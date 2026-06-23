package com.example.sammod.block.blocks;

import com.example.sammod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RiceCropBlock extends CropBlock {

    //MAX_AGE defines how many stages the rice crop has for growing
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    public RiceCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId(){
        return ModItems.RICE_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty(){
        return AGE;
    }

    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(AGE);
    }

}
