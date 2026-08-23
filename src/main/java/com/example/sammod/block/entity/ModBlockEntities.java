package com.example.sammod.block.entity;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.block.blocks.PedestalBlock;
import com.example.sammod.block.entity.custom.GrowthChamberBlockEntity;
import com.example.sammod.block.entity.custom.PedestalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//This is where the deferred register for block entities is
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SamMod.MOD_ID);

    //This is where we register our pedestal block entity
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITY_TYPES.register("pedestal_be", () -> BlockEntityType.Builder.of(
                    PedestalBlockEntity::new, ModBlocks.PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<GrowthChamberBlockEntity>> GROWTH_CHAMBER_BE =
            BLOCK_ENTITY_TYPES.register("growth_chamber_be", () -> BlockEntityType.Builder.of(
                    GrowthChamberBlockEntity::new, ModBlocks.GROWTH_CHAMBER.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITY_TYPES.register(bus);
    }

}
