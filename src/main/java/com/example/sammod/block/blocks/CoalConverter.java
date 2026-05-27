package com.example.sammod.block.blocks;

import com.example.sammod.item.ModItems;
import com.example.sammod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

import java.util.List;

public class CoalConverter extends Block {

    public CoalConverter(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    )
    {
        if (!pLevel.isClientSide &&
                (pStack.is(Items.COBBLED_DEEPSLATE) || pStack.is(Items.STONE))){
            pPlayer.getItemInHand(pHand).shrink(1);
            pPlayer.getInventory().add(new ItemStack(Items.COAL));
            pLevel.playSound(pPlayer, pPos, SoundEvents.DEEPSLATE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof ItemEntity itemEntity){
            if (isValidItem(itemEntity.getItem())){
                itemEntity.setItem(new ItemStack(Items.COAL, itemEntity.getItem().getCount()));
            }
        }
    }

    private boolean isValidItem(ItemStack item) {
        return item.is(ModTags.Items.COAL_CONVERTER_ITEMS);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {

        pTooltipComponents.add(Component.translatable("tooltip.sammod.coal_converter"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
