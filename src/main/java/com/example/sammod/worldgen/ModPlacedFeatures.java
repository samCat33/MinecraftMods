package com.example.sammod.worldgen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> METEORITE_ORE_PLACED_KEY =
            registerKey("meteorite_ore_placed");
    public static final ResourceKey<PlacedFeature> BLUE_OPAL_ORE_PLACED_KEY =
            registerKey("blue_opal_ore_placed");

    public static final ResourceKey<PlacedFeature> REDWOOD_PLACED_KEY =
            registerKey("redwood_placed");

    public static final ResourceKey<PlacedFeature> MEGA_REDWOOD_PLACED_KEY =
            registerKey("mega_redwood_placed");

    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PLACED_KEY =
            registerKey("blueberry_bush_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context){

        //This refers back to ModConfiguredFeatures.java
        //We use var because the actual data type has a long name
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        //Register the ore placement keys
        //This uses the configuration keys in the ModConfiguredFeatures class
        register(context, METEORITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.METEORITE_ORE_KEY),

                //The first parameter is how many veins per chunk
                //The second parameter defines the lower and upper y-bounds of ore vein generation
                ModOrePlacement.commonOrePlacement(25, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(-12))));

        register(context, BLUE_OPAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLUE_OPAL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(25, HeightRangePlacement.uniform(VerticalAnchor.absolute(30),
                        VerticalAnchor.absolute(70))));

        register(context, BLUEBERRY_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLUEBERRY_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

        //Check out VegetationPlacements.java to look at how vanilla trees are placed
        //Check out PlacementUtils.java to see how the chance parameter works

        //PlacementUtils.countExtra(int a, float b, int c)
        //a = How many trees by default
        //b = Probability of adding c more trees (Make sure 1/b returns a number that
        //      can be represented as an integer [e.g. b = 0.2 -> 1/0.2 = 5]
        //c = How many more trees are added if probability b is met

        //After PlacementUtils.countExtra(), the second argument
        //for VegetationPlacements.treePlacement is the block restrictions
        //we want to apply to the tree (the tree can only grow on blocks
        //that the sapling can be placed on)
        register(context, REDWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.REDWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1f, 2),
                        ModBlocks.REDWOOD_SAPLING.get()));

        register(context, MEGA_REDWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MEGA_REDWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(40, 0.1f, 2),
                        ModBlocks.REDWOOD_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
