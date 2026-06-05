package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import com.example.sammod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

//Look at ItemTagsProvider
public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.COAL_CONVERTER_ITEMS)
                .add(Items.STONE)
                .add(Items.COBBLED_DEEPSLATE);

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.METEORITE_HELMET.get())
                .add(ModItems.METEORITE_CHESTPLATE.get())
                .add(ModItems.METEORITE_LEGGINGS.get())
                .add(ModItems.METEORITE_BOOTS.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.METEORITE_INGOT.get());
        
        tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.BIG_S_SMITHING_TEMPLATE.get());

    }
}
