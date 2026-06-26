package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.enchantment.ModEnchantments;
import com.example.sammod.trim.ModTrimMaterials;
import com.example.sammod.trim.ModTrimPatterns;
import com.example.sammod.worldgen.ModBiomeModifiers;
import com.example.sammod.worldgen.ModConfiguredFeatures;
import com.example.sammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackEntries extends DatapackBuiltinEntriesProvider {


    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

            //This is to register our custom trim materials, trim patterns, and enchantments
            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
            .add(Registries.TRIM_PATTERN, ModTrimPatterns::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)

            //This is to register the classes needed for custom worldgen
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);



    public ModDataPackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(SamMod.MOD_ID));
    }
}
