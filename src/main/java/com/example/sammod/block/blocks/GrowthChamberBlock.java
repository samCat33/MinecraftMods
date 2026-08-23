package com.example.sammod.block.blocks;

import com.example.sammod.block.entity.ModBlockEntities;
import com.example.sammod.block.entity.custom.GrowthChamberBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GrowthChamberBlock extends BaseEntityBlock {
    public static final MapCodec<GrowthChamberBlock> CODEC = simpleCodec(GrowthChamberBlock::new);

    public GrowthChamberBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    //This will create the corresponding block entity for the block
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GrowthChamberBlockEntity(pPos, pState);
    }

    //This will allow the block corresponding to the block entity to be visible
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    //All the contents from the inventory of the block entity
    //will be dropped when the block is broken
    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof GrowthChamberBlockEntity growthChamberBlockEntity){
                growthChamberBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            //If the player right-clicks on the entity corresponding to the block
            //open up a new menu with the name "Growth Chamber"
            if (entity instanceof GrowthChamberBlockEntity growthChamberBlockEntity){
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(growthChamberBlockEntity, Component.literal("Growth Chamber")), pPos);
            }
            else{
                throw new IllegalStateException("The Container provider is missing");
            }
        }

        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    //This will allow the block entity to keep track of in-game ticks
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {

        //Items can only be crafted on the server-side
        //so we will not keep track of ticks on the client-side
        if (pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.GROWTH_CHAMBER_BE.get(),
                (level, blockPos, blockState, growthChamberBlockEntity)
                        -> growthChamberBlockEntity.tick(level, blockPos, blockState));
    }
}
