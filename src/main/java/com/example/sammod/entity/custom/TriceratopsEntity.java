package com.example.sammod.entity.custom;

import com.example.sammod.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TriceratopsEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int idleAnimationTimeInTicks = 40;

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
            this.idleAnimationTimeout = idleAnimationTimeInTicks;
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

}
