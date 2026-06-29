package com.example.sammod.worldgen.tree;

import com.example.sammod.SamMod;
import com.example.sammod.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {

    //Parameters of Tree Grower:
    //  Resource Location,
    //  Large Tree Key,
    //  Small Tree Key,
    //  Secondary Features Key
    public static final TreeGrower REDDWOOD = new TreeGrower(SamMod.MOD_ID + ":redwood",
            Optional.of(ModConfiguredFeatures.MEGA_REDWOOD_KEY), Optional.of(ModConfiguredFeatures.REDWOOD_KEY),
            Optional.empty());
}
