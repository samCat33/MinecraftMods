package com.example.sammod.item.items;

import com.example.sammod.component.ModDataComponentTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class MidasTouchItem extends Item {

    public MidasTouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        //Make sure the code is not running for client only and
        //make sure that the block that it was used on was not another gold block
        if (!level.isClientSide && clickedBlock != Blocks.GOLD_BLOCK) {
            level.setBlockAndUpdate(context.getClickedPos(), Blocks.GOLD_BLOCK.defaultBlockState());

            //hurtAndBreak(damage, level, player, item (use lambda function to determine when it breaks
            context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) context.getPlayer()),
                    item -> {;
                        assert context.getPlayer() != null;
                        context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND);
                    });

            level.playSound(null, context.getClickedPos(), SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.BLOCKS, 1.0F, 2.0F);

            context.getItemInHand().set(ModDataComponentTypes.COORDINATES.get(), context.getClickedPos());

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity
            (ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand){

        Level level = pPlayer.level();

        if (!level.isClientSide) {
            pInteractionTarget.hurt(level.damageSources().generic(), 110F);
            pStack.hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) pPlayer), item -> {
                pPlayer.onEquippedItemBroken(item, EquipmentSlot.MAINHAND);
            });

            level.setBlockAndUpdate(pInteractionTarget.blockPosition(), Blocks.GOLD_BLOCK.defaultBlockState());
        }

        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (pStack.get(ModDataComponentTypes.COORDINATES.get()) != null){
            pTooltipComponents.add(Component.literal("Last block turned to gold at " + pStack.get(ModDataComponentTypes.COORDINATES.get())));
        }
    }

}
