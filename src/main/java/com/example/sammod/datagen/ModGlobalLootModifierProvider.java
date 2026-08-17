package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.item.ModItems;
import com.example.sammod.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, SamMod.MOD_ID, registries);
    }

    //  External Libraries/Gradle: net.minecraft:client:extra:1.21/ client-extra.jar
    //  /data/minecraft/loot_table/

    @Override
    protected void start(HolderLookup.Provider registries) {
        this.add("rice_seeds_from_short_grass",
                //The array inside LootItemCondition
                //defines the set of conditions necessary
                //for a given vanilla entity to drop our
                //modified loot
                new AddItemModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                        LootItemRandomChanceCondition.randomChance(0.25f).build() }, /*End of conditions*/ ModItems.RICE_SEEDS.get()));

        this.add("rice_seeds_from_tall_grass",
                new AddItemModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                        LootItemRandomChanceCondition.randomChance(0.25f).build() }, /*End of conditions*/ ModItems.RICE_SEEDS.get()));

        this.add("raw_meteorite_from_shipwrecks",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/shipwreck_treasure")).build()
                }, ModItems.RAW_METEORITE.get(), 2));

        //This should make creepers drop catnip 50% of the time, but I've been told
        //that it doesn't seem like 50% for some reason
        this.add("catnip_from_creeper", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/creeper"))
                        .and(LootItemRandomChanceCondition.randomChance(0.5f)).build() },
                ModItems.CATNIP.get()));

    }
}
