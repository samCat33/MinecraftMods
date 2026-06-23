package com.example.sammod.block.blocks;

import com.example.sammod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BlueberryBushBlock extends SweetBerryBushBlock {


    public BlueberryBushBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState){
        return new ItemStack(ModItems.BLUEBERRIES.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos,
                                                Player pPlayer, BlockHitResult pHitResult){
        int i = pState.getValue(AGE);
        boolean flag = i == 3;
        if (i > 1){
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(ModItems.BLUEBERRIES.get(), j + (flag ? 1 : 0)));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            BlockState blockstate = pState.setValue(AGE, Integer.valueOf(1));
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        else{
            return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
        }
    }

    //We make this method empty so that the player does not take damage when touching the bush
    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

    }

}
