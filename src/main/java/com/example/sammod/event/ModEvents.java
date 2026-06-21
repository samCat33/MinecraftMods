package com.example.sammod.event;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import com.example.sammod.item.items.HammerItem;
import com.example.sammod.item.items.MidasTouchItem;
import com.example.sammod.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

//MOD events implement IModBusEvent and are usually for registering things before the game begins
//FORGE events happen while the game is running and include things such as using tools, as is the
//case in this example

@Mod.EventBusSubscriber(modid = SamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer){
            BlockPos initialBlockPos = event.getPos();

            //Important if statement
            if (HARVESTED_BLOCKS.contains(initialBlockPos)){
                return;
            }

            //If the position is the block we are standing on, or the block
            //is not one to be mined by a pickaxe, do not destroy
            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)){
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))){
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);

                //This line actually recursively calls BlockEvent.BreakEvent again
                //This is why we add the pos to the harvested blocks hashset
                //and why we have the important if statement noted above
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    //This uses the event of a player right-clicking a block
    @SubscribeEvent
    public static void rightClickOnSusieTNT(PlayerInteractEvent.RightClickBlock event){
        BlockPos pos = event.getPos();
        BlockState blockState = event.getLevel().getBlockState(pos);
        Player player = event.getEntity();

        //If the block that was right-clicked was a SUSIE_TNT,
        //the player was holding catnip
        //and we are not on the client side
        if (blockState.getBlock() == ModBlocks.SUSIE_TNT.get()
                && player.getMainHandItem().getItem() == ModItems.CATNIP.get()
        && !event.getLevel().isClientSide()){

            //Tell the player they are meanie and make them blind lol
            player.sendSystemMessage(Component.literal("Why are you blowing up Susie? MEANIE >:("));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 1));
        }
    }

    //This event changes the base damage of an arrow shot from the super bow
    @SubscribeEvent
    public static void onArrowShoot(EntityJoinLevelEvent event){
        //If the object is an unloaded arrow
        //and we are not on the client side
        if (event.getEntity() instanceof AbstractArrow arrow &&
                !event.getLevel().isClientSide()){


            //If the arrow was shot by a player
            if (arrow.getOwner() instanceof Player player){
                //If the player shot the arrow using a Super Bow in their main hand
                //or their offhand
                if (player.getMainHandItem().getItem() == ModItems.SUPER_BOW.get()
                        || player.getOffhandItem().getItem() == ModItems.SUPER_BOW.get()){

                    //change the base damage of the arrow
                    //default is 2.0 (1 heart)
                    arrow.setBaseDamage(5.0);
                }
            }
        }
    }

    //Adds the brewing recipe for our custom potion
    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event){
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.HONEY_BLOCK, ModPotions.STICKY_POTION.getHolder().get());
    }
}
