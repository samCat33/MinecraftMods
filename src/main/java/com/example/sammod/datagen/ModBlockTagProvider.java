package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import com.example.sammod.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

//Look at BlockTagsProvider
public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output,lookupProvider, SamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider){
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.METEORITE_BLOCK.get())
                .add(ModBlocks.METEORITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.METEORITE_ORE.get())
                .add(ModBlocks.RAW_METEORITE_BLOCK.get())
                .add(ModBlocks.SUSIE_BLOCK.get())
                .add(ModBlocks.SUSIE_TNT.get())
                .add(ModBlocks.SUSIE_BUTTON.get())
                .add(ModBlocks.SUSIE_SLAB.get())
                .add(ModBlocks.SUSIE_STAIRS.get())
                .add(ModBlocks.SUSIE_WALL.get())
                .add(ModBlocks.SUSIE_FENCE.get())
                .add(ModBlocks.SUSIE_FENCE_GATE.get())
                .add(ModBlocks.SUSIE_DOOR.get())
                .add(ModBlocks.SUSIE_PRESSURE_PLATE.get())
                .add(ModBlocks.SUSIE_TRAPDOOR.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.METEORITE_BLOCK.get())
                .add(ModBlocks.RAW_METEORITE_BLOCK.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.METEORITE_ORE.get())
                .add(ModBlocks.METEORITE_DEEPSLATE_ORE.get());

        tag(BlockTags.FENCES).add(ModBlocks.SUSIE_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.SUSIE_FENCE_GATE.get());
        tag(BlockTags.WALLS).add(ModBlocks.SUSIE_WALL.get());

        tag(ModTags.Blocks.NEEDS_METEORITE_TOOL)

                //These top four add statements aren't really necessary,
                //but they are still there in case I decide to change
                //the NEEDS_DIAMOND_TOOL tag
                .add(ModBlocks.METEORITE_BLOCK.get())
                .add(ModBlocks.METEORITE_ORE.get())
                .add(ModBlocks.METEORITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.RAW_METEORITE_BLOCK.get())
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_METEORITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(ModTags.Blocks.NEEDS_METEORITE_TOOL);

    }
}
