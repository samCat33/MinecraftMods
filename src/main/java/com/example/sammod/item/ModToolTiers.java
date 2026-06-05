package com.example.sammod.item;

import com.example.sammod.block.ModBlocks;
import com.example.sammod.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {

    //Look at Tiers.java (not Tier, but using Tier class is still correct here)
    public static final Tier METEORITE = new ForgeTier(2500, 11, 6f, 16,
            ModTags.Blocks.NEEDS_METEORITE_TOOL, () -> Ingredient.of(ModItems.METEORITE_INGOT.get()),
            ModTags.Blocks.INCORRECT_FOR_METEORITE_TOOL);
}
