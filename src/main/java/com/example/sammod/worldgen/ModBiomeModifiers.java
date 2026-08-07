package com.example.sammod.worldgen;

import com.example.sammod.SamMod;
import com.example.sammod.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_METEORITE_ORE
            = registerKey("add_meteorite_ore");
    public static final ResourceKey<BiomeModifier> ADD_BLUE_OPAL_ORE
            = registerKey("add_blue_opal_ore");

    public static final ResourceKey<BiomeModifier> ADD_REDWOOD_TREE =
            registerKey("add_redwood_tree");

    public static final ResourceKey<BiomeModifier> ADD_MEGA_REDWOOD_TREE =
            registerKey("add_mega_redwood_tree");

    public static final ResourceKey<BiomeModifier> ADD_BLUEBERRY_BUSH =
            registerKey("add_blueberry_bush");

    public static final ResourceKey<BiomeModifier> SPAWN_TRICERATOPS =
            registerKey("spawn_triceratops");

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

        context.register(ADD_REDWOOD_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                //This first parameter is a set of the biomes the redwood tree can grow in
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.REDWOOD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MEGA_REDWOOD_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                //This first parameter is a set of the biomes the mega redwood tree can grow in
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.MEGA_REDWOOD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_BLUEBERRY_BUSH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(

                //This first parameter is a set of the biomes the mega redwood tree can grow in
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.FLOWER_FOREST),
                        biomes.getOrThrow(Biomes.STONY_PEAKS)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BLUEBERRY_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        //This defines the biomes that the triceratops can spawn in
        context.register(SPAWN_TRICERATOPS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BAMBOO_JUNGLE),
                        biomes.getOrThrow(Biomes.JUNGLE), biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.SAVANNA)),

                //Higher weights make this mob spawn more frequently, but at the cost of other mobs
                //spawning less frequently.
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.TRICERATOPS.get(), 25, 3, 5))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
    }
}
