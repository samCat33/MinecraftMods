package com.example.sammod.item.items;

import com.example.sammod.component.ModDataComponentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WaterStaffItem extends Item {
    public WaterStaffItem(Properties pProperties) {
        super(pProperties);
    }


    //When the player right clicks, the block they interacted
    //with (if they hit a block) turns into water
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        //Make sure the code is not running for client only and
        //make sure that the block that it was used on was not another water block
        if (!level.isClientSide && clickedBlock != Blocks.WATER){
            level.setBlockAndUpdate(context.getClickedPos(), Blocks.WATER.defaultBlockState());

            //hurtAndBreak(damage, level, player, item (use lambda function to determine when it breaks))
            context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) context.getPlayer()),
                    item -> {;
                        assert context.getPlayer() != null;
                        context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND);
                    });

            level.playSound(null, context.getClickedPos(),
                    SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);


            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }


    //If the player uses this on an entity water will spawn above the entity in a 3x3 grid
    @Override
    public InteractionResult interactLivingEntity
            (ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand){

        Level level = pPlayer.level();

        if (!level.isClientSide) {

            pStack.hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) pPlayer), item -> {
                pPlayer.onEquippedItemBroken(item, EquipmentSlot.MAINHAND);
            });


            for (int i = -1; i < 2; i += 1){
                for (int j = -1; j < 2; j += 1){
                    level.setBlockAndUpdate(pInteractionTarget.blockPosition().offset(i,3,j), Blocks.WATER.defaultBlockState());
                }
            }

        }

        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }
}
