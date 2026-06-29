package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.block.blocks.BlueberryBushBlock;
import com.example.sammod.block.blocks.LampBlock;
import com.example.sammod.block.blocks.RiceCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

//Look at BlockStateProvider
public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SamMod.MOD_ID, exFileHelper);
    }


    //The texture files must exist for these items or else an
    //InvocationTargetException will occur
    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CAT_NOTE_BLOCK);
        blockWithItem(ModBlocks.SUSIE_TNT);
        blockWithItem(ModBlocks.SUSIE_BLOCK);
        blockWithItem(ModBlocks.METEORITE_BLOCK);
        blockWithItem(ModBlocks.METEORITE_ORE);
        blockWithItem(ModBlocks.METEORITE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.RAW_METEORITE_BLOCK);
        blockWithItem(ModBlocks.COAL_CONVERTER);

        blockWithItem(ModBlocks.BLUE_OPAL_ORE);

        stairsBlock(ModBlocks.SUSIE_STAIRS.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        slabBlock(ModBlocks.SUSIE_SLAB.get(), blockTexture(ModBlocks.SUSIE_TNT.get()), blockTexture(ModBlocks.SUSIE_TNT.get()));


        buttonBlock(ModBlocks.SUSIE_BUTTON.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        pressurePlateBlock(ModBlocks.SUSIE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));

        fenceBlock(ModBlocks.SUSIE_FENCE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        fenceGateBlock(ModBlocks.SUSIE_FENCE_GATE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        wallBlock(ModBlocks.SUSIE_WALL.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));

        doorBlockWithRenderType(ModBlocks.SUSIE_DOOR.get(), modLoc("block/susie_door_bottom"),
                modLoc("block/susie_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.SUSIE_TRAPDOOR.get(), modLoc("block/susie_trapdoor"), true, "cutout");

        blockItem(ModBlocks.SUSIE_STAIRS);
        blockItem(ModBlocks.SUSIE_SLAB);
        blockItem(ModBlocks.SUSIE_PRESSURE_PLATE);
        blockItem(ModBlocks.SUSIE_FENCE_GATE);
        blockItem(ModBlocks.SUSIE_TRAPDOOR, "_bottom");

        customLamp();

        //This makes the rice crop
        //The textures must be named rice_crop_stageX, where X is the age property from 0 up to MAX_AGE
        makeCrop((CropBlock) ModBlocks.RICE_CROP.get(), "rice_crop_stage", "rice_crop_stage");
        makeBush((SweetBerryBushBlock) ModBlocks.BLUEBERRY_BUSH.get(), "blueberry_bush_stage", "blueberry_bush_stage");

        logBlock(ModBlocks.REDWOOD_LOG.get());
        axisBlock(ModBlocks.REDWOOD_WOOD.get(), blockTexture(ModBlocks.REDWOOD_LOG.get()),
                blockTexture(ModBlocks.REDWOOD_LOG.get()));
        logBlock(ModBlocks.STRIPPED_REDWOOD_LOG.get());
        axisBlock(ModBlocks.STRIPPED_REDWOOD_WOOD.get(),
                blockTexture(ModBlocks.STRIPPED_REDWOOD_LOG.get()), blockTexture(ModBlocks.STRIPPED_REDWOOD_LOG.get()));

        blockItem(ModBlocks.REDWOOD_LOG);
        blockItem(ModBlocks.REDWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_REDWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_REDWOOD_WOOD);

        blockWithItem(ModBlocks.REDWOOD_PLANKS);

        leavesBlock(ModBlocks.REDWOOD_LEAVES);
        saplingBlock(ModBlocks.REDWOOD_SAPLING);

    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        //simpleBlock(block, model)
        simpleBlock(blockRegistryObject.get(),

                //model argument is models().cross(name, ResourceLocation cross)
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get())
                        .getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));

    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject){
        //simpleBlockWithItem(block, model)
        simpleBlockWithItem(blockRegistryObject.get(),
                //model argument is models().singleTexture(name, parent, textureKey, texture)
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get())
                        .getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, String modelName, String textureName){
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(BlueberryBushBlock.AGE),
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "block/" + textureName +
                        state.getValue(BlueberryBushBlock.AGE))).renderType("cutout"));

        return models;
    }

    //This calls the "states" helper method below to make the block states
    public void makeCrop(CropBlock block, String modelName, String textureName){

        //A one-line java function is being defined here
        //that takes in a block state and returns a configured model
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        //We build the block states using the defined function
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block,
                                     String modelName, String textureName){
        ConfiguredModel[] models = new ConfiguredModel[1];

        //This creates a configured model with different textures
        //depending on the age property of the crop

        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RiceCropBlock) block)
                .getAgeProperty()), ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                "block/" + textureName + state.getValue(((RiceCropBlock) block).getAgeProperty())))
                .renderType("cutout"));

        return models;
    }

    private void customLamp(){
        //Variant blockstates
        getVariantBuilder(ModBlocks.CUSTOM_LAMP.get()).forAllStates(state -> {
            //Off blockstate
            if (state.getValue(LampBlock.LIGHT_LEVEL) <= 0) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("custom_lamp_off",
                        ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "block/" + "custom_lamp_off")))};
            }

            //Bright blockstate
            else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("custom_lamp_on",
                        ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "block/" + "custom_lamp_on")))};
            }
        });

        //Block item appearance
        simpleBlockItem(ModBlocks.CUSTOM_LAMP.get(), models().cubeAll("custom_lamp_on",
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "block/" + "custom_lamp_on")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("sammod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("sammod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}
