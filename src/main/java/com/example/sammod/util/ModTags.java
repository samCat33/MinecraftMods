package com.example.sammod.util;

import com.example.sammod.SamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEEDS_METEORITE_TOOL = createTag("needs_meteorite_tool");
        public static final TagKey<Block> INCORRECT_FOR_METEORITE_TOOL = createTag("incorrect_for_meteorite_tool");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
        }

    }

    public static class Items{

        public static final TagKey<Item> COAL_CONVERTER_ITEMS = createTag("coal_converter_items");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
        }
    }
}
