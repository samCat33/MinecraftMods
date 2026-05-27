package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.block.blocks.LampBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

        stairsBlock(ModBlocks.SUSIE_STAIRS.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        slabBlock(ModBlocks.SUSIE_SLAB.get(), blockTexture(ModBlocks.SUSIE_TNT.get()), blockTexture(ModBlocks.SUSIE_TNT.get()));


        buttonBlock(ModBlocks.SUSIE_BUTTON.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        pressurePlateBlock(ModBlocks.SUSIE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));

        fenceBlock(ModBlocks.SUSIE_FENCE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        fenceGateBlock(ModBlocks.SUSIE_FENCE_GATE.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));
        wallBlock(ModBlocks.SUSIE_WALL.get(), blockTexture(ModBlocks.SUSIE_TNT.get()));

        doorBlockWithRenderType(ModBlocks.SUSIE_DOOR.get(), modLoc("block/susie_door_bottom"), modLoc("block/susie_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.SUSIE_TRAPDOOR.get(), modLoc("block/susie_trapdoor"), true, "cutout");

        blockItem(ModBlocks.SUSIE_STAIRS);
        blockItem(ModBlocks.SUSIE_SLAB);
        blockItem(ModBlocks.SUSIE_PRESSURE_PLATE);
        blockItem(ModBlocks.SUSIE_FENCE_GATE);
        blockItem(ModBlocks.SUSIE_TRAPDOOR, "_bottom");


        customLamp();
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
