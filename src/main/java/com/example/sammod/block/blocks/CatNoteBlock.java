package com.example.sammod.block.blocks;

import com.example.sammod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CatNoteBlock extends Block {
    private float pitch;

    public CatNoteBlock(Properties properties) {
        super(properties);
        pitch = 0.5f;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 1.0F, pitch);


        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity){
        if (entity instanceof Player) {
            level.playSound(entity, pos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 1.0F, pitch);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        if (!pLevel.isClientSide && pStack.is(ModItems.CATNIP.get())){
            pitch += 0.1F;

            if (pitch > 2.0F){
                pitch = 0.5f;
            }
        }
        pLevel.playSound(pPlayer, pPos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 1.0F, pitch);

        return ItemInteractionResult.SUCCESS;
    }

    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        //Make sure the block is not simply changing states and is actually being removed or replaced
        if (!pState.is(pNewState.getBlock())){
            pLevel.playSound(null, pPos, SoundEvents.CAT_DEATH, SoundSource.BLOCKS, 1.0F, 0.5F);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);

    }

}
