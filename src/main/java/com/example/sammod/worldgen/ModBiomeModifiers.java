package com.example.sammod.worldgen;

import com.example.sammod.SamMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_METEORITE_ORE
            = registerKey("add_meteorite_ore");
    public static final ResourceKey<BiomeModifier> ADD_BLUE_OPAL_ORE
            = registerKey("add_blue_opal_ore");

    public static void bootstrap(BootstrapContext<BiomeModifier> context){

        //Refers back to ModPlacedFeatures.java
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);

        //Refers to the biome registries
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_METEORITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
           biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
           HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.METEORITE_ORE_PLACED_KEY)),
           GenerationStep.Decoration.UNDERGROUND_ORES));

        // Individual Biomes
        // context.register(ADD_METEORITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
        //         HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
        //         HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.METEORITE_ORE_PLACED_KEY)),
        //         GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_BLUE_OPAL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BLUE_OPAL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
    }
}
