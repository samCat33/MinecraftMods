package com.example.sammod.datagen;

import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

//Look at BlockLootSubProvider
public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.METEORITE_BLOCK.get());
        dropSelf(ModBlocks.RAW_METEORITE_BLOCK.get());
        dropSelf(ModBlocks.SUSIE_TNT.get());
        dropSelf(ModBlocks.CAT_NOTE_BLOCK.get());

        dropSelf(ModBlocks.CUSTOM_LAMP.get());
        dropSelf(ModBlocks.COAL_CONVERTER.get());

        dropSelf(ModBlocks.SUSIE_BLOCK.get());
        dropSelf(ModBlocks.SUSIE_BUTTON.get());
        dropSelf(ModBlocks.SUSIE_FENCE.get());

        dropSelf(ModBlocks.SUSIE_FENCE_GATE.get());
        dropSelf(ModBlocks.SUSIE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SUSIE_STAIRS.get());

        dropSelf(ModBlocks.SUSIE_TRAPDOOR.get());
        dropSelf(ModBlocks.SUSIE_WALL.get());

        this.add(ModBlocks.SUSIE_DOOR.get(),
                createDoorTable(ModBlocks.SUSIE_DOOR.get()));

        this.add(ModBlocks.SUSIE_SLAB.get(),
                createSlabItemTable(ModBlocks.SUSIE_SLAB.get()));

        this.add(ModBlocks.METEORITE_ORE.get(),
                createMultipleOreDrops(ModBlocks.METEORITE_ORE.get(),
                        ModItems.RAW_METEORITE.get(), 2, 5));

        this.add(ModBlocks.METEORITE_DEEPSLATE_ORE.get(),
                createMultipleOreDrops(ModBlocks.METEORITE_DEEPSLATE_ORE.get(),
                        ModItems.RAW_METEORITE.get(), 3, 6));


    }

    protected LootTable.Builder createMultipleOreDrops (Block pBlock, Item pItem, int min, int max) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock,
                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                        pBlock,
                        LootItem.lootTableItem(pItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
