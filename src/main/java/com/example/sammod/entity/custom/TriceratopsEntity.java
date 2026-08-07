package com.example.sammod.entity.custom;

import com.example.sammod.entity.ModEntities;
import com.example.sammod.entity.TriceratopsVariant;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.unsafe.UnsafeFieldAccess;
import org.jetbrains.annotations.Nullable;

public class TriceratopsEntity extends Animal {

    //This will save the entity variant ID of the triceratops
    //(defined in TriceratopsVariant enum)

    //The EntityDataAccessor automatically synchronizes server and client
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private final int IDLE_ANIMATION_TICK_LENGTH = 40;

    //Adds the bossbar for the triceratops
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.literal("Triceratops"),
            BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_20);

    public TriceratopsEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //This method defines the behavior of the triceratops
    @Override
    protected void registerGoals(){
        //The first parameter is the priority of the goal.
        //Lower numbers mean higher priority, meaning the mob will execute
        //this behavior over other behaviors that have lower priorities


        //The biggest priority for the entity is to swim if it is on water
        //so it does not drown
        this.goalSelector.addGoal(0, new FloatGoal(this));

        //The next highest priorities, in order, are panicking when attacked
        //breeding, and following player who holds a specific item.
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(Items.SUGAR_CANE), false));

        //The trike will follow its parent if it is young
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        //The trike will try to avoid water when it can
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));

        //The trike will look around randomly but will prioritize looking at the player
        //when the player is near
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    //Defining these three basic attributes is necessary in order
    //for the mob to even spawn
    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 25D);
    }

    //This is the food that you can feed it
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SUGAR_CANE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TRICERATOPS.get().create(pLevel);
    }


    //This will loop through the idle animation
    private void setupAnimationStates(){
        if (this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = IDLE_ANIMATION_TICK_LENGTH;
            this.idleAnimationState.start(this.tickCount);
        }
        else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick(){
        super.tick();

        //The animations will only run on the client side
        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    //This builds the synched data for the EntityDataAccessor variant
    //This method is always necessary whenever you have an EntityDataAccessor
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder){
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                if (this.getHealth() < this.getAttributeBaseValue(Attributes.MAX_HEALTH)) {
                    this.heal(2f);
                }
                else{
                    this.setInLove(pPlayer);
                }
                this.usePlayerItem(pPlayer, pHand, itemstack);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                if (this.getHealth() < this.getAttributeBaseValue(Attributes.MAX_HEALTH)) {
                    this.heal(2f);
                }
                this.usePlayerItem(pPlayer, pHand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }


    //This is a helper method for the method below
    private int getTypeVariant(){
        return this.entityData.get(VARIANT);
    }

    //This gets the triceratops variant
    public TriceratopsVariant getVariant(){
        //This uses a bitwise operator, and I am not sure why
        //but this is what Vanilla uses
        return TriceratopsVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(TriceratopsVariant variant){
        this.entityData.set(VARIANT, variant.getId() & 255);
    }


    //The following two methods save the entity variant data
    //so that they are saved after the player leaves the game
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag){
        super.addAdditionalSaveData(compoundTag);

        this.entityData.set(VARIANT, compoundTag.getInt("Variant"));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag){
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(VARIANT, compoundTag.getInt("Variant"));
    }

    //This method will run when the entity is about to spawn,
    //choosing a random value among the list of TriceratopsVariant values
    //and then setting the trike variant to be the random value selected
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData){
        TriceratopsVariant variant = Util.getRandom(TriceratopsVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);

    }

    //This method is similar to the one above but will run when a baby trike
    //spawns from being bred
    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal animal, @Nullable AgeableMob baby){
        TriceratopsVariant variant = Util.getRandom(TriceratopsVariant.values(), this.random);
        ((TriceratopsEntity) baby).setVariant(variant);
        super.finalizeSpawnChildFromBreeding(level, animal, baby);
    }

    /* TRICERATOPS SOUNDS */

    @Nullable
    @Override
    protected SoundEvent getAmbientSound(){
        return SoundEvents.ELDER_GUARDIAN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource){
        return SoundEvents.ELDER_GUARDIAN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound(){
        return SoundEvents.ELDER_GUARDIAN_DEATH;
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        super.playStepSound(pPos, pState);
    }

    @Override
    public void heal(float pHealAmount) {
        super.heal(pHealAmount);
    }


    /*Bossbar Logic*/
    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer){
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer){
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }


    //This updates the triceratops' bossbar depending on its health
    @Override
    public void aiStep(){
        super.aiStep();
        //Both health and maxHealth are floats, so there is no integer division
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }



}
