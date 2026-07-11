package com.example.sammod.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

import java.util.function.Supplier;

//This class is what allows us to plant our redwood sapling
//on a different block. That block is defined in SamMod.redwoodSaplingBlock.

public class ModSaplingBlock extends SaplingBlock {

    private final Supplier<Block> block;

    public ModSaplingBlock(TreeGrower treeGrower, Properties properties,
                           Supplier<Block> block) {
        super(treeGrower, properties);

        //We define the supplier to get our given block
        this.block = block;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos){
        //We use the supplier to get the block
        return state.is(block.get());
    }

    //This prevents the tree from growing on regular dirt
    //by making the plant type unrecognizable in PlantType.java

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos){

        return PlantType.get("custom");
    }
}
