package com.example.sammod.worldgen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> METEORITE_ORE_KEY =
            registerKey("meteorite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_OPAL_ORE_KEY =
            registerKey("blue_opal_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_KEY =
            registerKey("redwood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_REDWOOD_KEY =
            registerKey("mega_redwood");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        //These are all the different vanilla blocks that will be replaced by ores
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        //This is a list of ore configurations for meteorite that replaces
        //stone and deepslate
        List<OreConfiguration.TargetBlockState> meteoriteOres =
                List.of(OreConfiguration.target(stoneReplaceables,
                                ModBlocks.METEORITE_ORE.get().defaultBlockState()),
                        OreConfiguration.target(deepslateReplaceables,
                                ModBlocks.METEORITE_DEEPSLATE_ORE.get().defaultBlockState()));


        //Register the meteorite ore configurations with the list defined above
        register(context, METEORITE_ORE_KEY, Feature.ORE, new OreConfiguration(meteoriteOres, 3));

        //Register the blue opal ore configurations with the endstoneReplaceables RuleTest
        register(context, BLUE_OPAL_ORE_KEY, Feature.ORE, new OreConfiguration(endstoneReplaceables,
                ModBlocks.BLUE_OPAL_ORE.get().defaultBlockState(), 5));

        //For the redwood tree
        //Look at TreeFeatures.java for how vanilla trees are created!
        register(context, REDWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                //How the logs are placed
                BlockStateProvider.simple(ModBlocks.REDWOOD_LOG.get()),
                new ForkingTrunkPlacer(4, 4, 3),

                //How the leaves are placed
                BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 3),

                new TwoLayersFeatureSize(1, 0, 2))
            .build());

        //For the mega redwood tree
        register(context, MEGA_REDWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                //How the logs are placed
                BlockStateProvider.simple(ModBlocks.REDWOOD_LOG.get()),

                //Forking Trunk Placer: Base height is
                //first parameter + rand % second parameter + rand % third parameter
                new MegaJungleTrunkPlacer(32, 20, 15),

                //How the leaves are placed
                BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES.get()),

                //Parameters for BlobFoliagePlacer: (radius, offset relative to top of trunk, height)
                //UniformInt.of(a,b) generates a random value between a inclusive and b inclusive
                new MegaJungleFoliagePlacer(UniformInt.of(2,4), ConstantInt.of(0), 9),

                //First parameter: Height threshold from the ground where check switches
                //  from lower to upper zone
                //Second parameter: Air radius required for lower part of tree
                //Third parameter: Air radius required for upper part of tree
                new TwoLayersFeatureSize(3, 2, 3))
                .build());

    }

    //This is where we actually register Configured Features
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name));
    }

    //Putting an inherited class inside this generic restricts
    //the data type to objects that inherit from that class

    //This adds the json file
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature, FC configuration){
                context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
