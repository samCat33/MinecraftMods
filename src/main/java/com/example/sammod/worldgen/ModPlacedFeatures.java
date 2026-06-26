package com.example.sammod.worldgen;

import com.example.sammod.SamMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> METEORITE_ORE_PLACED_KEY =
            registerKey("meteorite_ore_placed");
    public static final ResourceKey<PlacedFeature> BLUE_OPAL_ORE_PLACED_KEY =
            registerKey("blue_opal_ore_placed");


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
