package com.example.sammod.block.blocks;

import com.example.sammod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

//This class is for making our custom logs and planks for our custom tree
public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
    public ModFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }


    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos,
                               Direction direction){
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos,
                               Direction direction){
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
                                  Direction direction){
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                     ToolAction toolAction, boolean simulate){
        //If we are modifying the block with an axe
        if (context.getItemInHand().getItem() instanceof AxeItem){

            //These two if statements are for the redwood tree

            //Turn log textures on sides of redwood tree block
            // into stripped log textures with the axis of rotation saved
            if (state.is(ModBlocks.REDWOOD_LOG.get())){
                return  ModBlocks.STRIPPED_REDWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            //Turn wood textures on top and bottom of redwood tree block
            // into stripped wood textures with the axis of rotation saved
            else if (state.is(ModBlocks.REDWOOD_WOOD.get())){
                return ModBlocks.STRIPPED_REDWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            //If we are not simulating the action (client side simulates the action
            //to see if the action is valid before actually doing it on the server side,
            //and we want to make sure the action is actually happening
            if (!simulate){
                //Play the log stripping sound
                Level level = context.getLevel();
                BlockPos blockPos = context.getClickedPos();
                Player player = context.getPlayer();

                level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

}
