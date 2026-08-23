package com.example.sammod.block.blocks;

import com.example.sammod.entity.ModEntities;
import com.example.sammod.entity.custom.ChairEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ChairBlock extends HorizontalDirectionalBlock {

    //Every block entity has a MapCodec like this
    public static final MapCodec<ChairBlock> CODEC = simpleCodec(ChairBlock::new);

    //This defines the hitbox for the chair (both for collision and selection)
    //Block.box(x1, y1, z1, x2, y2, z2) measures according to a 16x16x16 grid for a block
    public static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(3, 0, 3, 13, 8, 13),
                    Block.box(3, 0, 11, 13, 18, 13)
            ),
            Direction.SOUTH, Shapes.or(
                    Block.box(3, 0, 3, 13, 8, 13),
                    Block.box(3, 0, 3, 13, 18, 5)
            ),
            Direction.EAST, Shapes.or(
                    Block.box(3, 0, 3, 13, 8, 13),
                    Block.box(3, 0, 3, 5, 18, 13)
            ),
            Direction.WEST, Shapes.or(
                    Block.box(3, 0, 3, 13, 8, 13),
                    Block.box(11, 0, 3, 13, 18, 13)
            )


    );

    public ChairBlock(Properties properties){
        super(properties);

        //Set the default block state to face north
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult){

        //Only run on server side
        if(!level.isClientSide()){

            //Get the ChairEntities that are within the collision boxes of this chair
            Entity entity = null;
            List<ChairEntity> entities = level.getEntities(ModEntities.CHAIR_ENTITY.get(), new AABB(pos),
                    chair -> true);

            //If there are no chair entities, spawn a new one and set the current entity
            //to be the new entity
            if (entities.isEmpty()){
                entity = ModEntities.CHAIR_ENTITY.get().spawn((ServerLevel) level, pos, MobSpawnType.TRIGGERED);
            }
            //Otherwise set the entity to be the first entity
            else{
                entity = entities.get(0);
            }

            //Make the player sit on the chair entity
            player.startRiding(entity);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
                                  CollisionContext context){

        //When getting the shape, get the value of which direction the chair is facing,
        //or default to the shape it would have when facing North
        return SHAPES.getOrDefault(state.getValue(FACING), SHAPES.get(Direction.NORTH));
    }



    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec(){
        return CODEC;
    }


    //This sets the FACING block state value to make the chair face towards
    //the player when they set it down context.getHorizontalDirection().getOpposite()
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    //This adds the FACING block state to the chair block
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
