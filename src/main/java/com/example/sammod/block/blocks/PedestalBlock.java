package com.example.sammod.block.blocks;

import com.example.sammod.block.entity.custom.PedestalBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {

    public static final VoxelShape SHAPE = Block.box(2, 0,2, 14, 13, 14);
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);


    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    //This method is necessary to ensure that the block
    //is visible
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    //Level is the world variable
    //When we destroy the pedestal, we want it to drop whatever it was holding
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos,
                            BlockState newBlockState, boolean movedByPiston) {

        if (state.getBlock() != newBlockState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof  PedestalBlockEntity pedestalBlockEntity) {
                pedestalBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newBlockState, movedByPiston);
    }

    //If the player interacts with the block
    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel,
                                              BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {

        //If the player hits the block entity (they will when interacting with the block)
        if (pLevel.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestalBlockEntity) {

            //Open the menu on the server side if the player is crouching
            //We open on the server side because the server is responsible for handling all the
            //menu logic in UI

            //The server will send a packet to the client telling it how to create a
            //client-side menu that the player sees
            if(pPlayer.isCrouching() && !pLevel.isClientSide()){
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(pedestalBlockEntity, Component.literal("Pedestal")), pPos);
                return ItemInteractionResult.SUCCESS;
            }

            //Return on the client side to prevent the code below from executing
            //if the menu is open
            if (pPlayer.isCrouching() && pLevel.isClientSide()){
                return ItemInteractionResult.SUCCESS;
            }

            //If the pedestal is empty and the player is holding something
            if (pedestalBlockEntity.INVENTORY.getStackInSlot(0).isEmpty() && !pStack.isEmpty()) {

                //Put one item from the stack that the player is holding inside the pedestal
                pedestalBlockEntity.INVENTORY.insertItem(0, pStack.copy(), false);
                pStack.shrink(1);

                //Play SoundEvents.ITEM_PICKUP at 2x pitch
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }

            //If the player is not holding anything
            else if(pStack.isEmpty()){

                //Remove one item from the pedestal (if the pedestal is also empty nothing happens)
                ItemStack stackOnPedestal = pedestalBlockEntity.INVENTORY.extractItem(0, 1, false);

                //Give the player the item that was on the pedestal
                pPlayer.setItemInHand(pHand, stackOnPedestal);

                //Set the slot in the pedestal to empty
                pedestalBlockEntity.clearContents();

                //Play SoundEvents.ITEM_PICKUP at 1x pitch
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
