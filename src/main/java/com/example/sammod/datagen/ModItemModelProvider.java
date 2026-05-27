package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


//Look at ItemModelProvider
public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SamMod.MOD_ID, existingFileHelper);
    }

    //The texture files must exist for these items or else an
    //InvocationTargetException will occur
    @Override
    protected void registerModels() {
        basicItem(ModItems.CATNIP.get());
        basicItem(ModItems.METEORITE_INGOT.get());
        basicItem(ModItems.RAW_METEORITE.get());
        basicItem(ModItems.MIDAS_TOUCH.get());

        buttonItem(ModBlocks.SUSIE_BUTTON, ModBlocks.SUSIE_TNT);
        fenceItem(ModBlocks.SUSIE_FENCE, ModBlocks.SUSIE_TNT);
        wallItem(ModBlocks.SUSIE_WALL, ModBlocks.SUSIE_TNT);

        simpleBlockItem(ModBlocks.SUSIE_DOOR);

        handheldItem(ModItems.METEORITE_AXE);
        handheldItem(ModItems.METEORITE_PICKAXE);
        handheldItem(ModItems.METEORITE_SHOVEL);
        handheldItem(ModItems.METEORITE_SWORD);
        handheldItem(ModItems.METEORITE_HOE);
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void buttonItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void fenceItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
