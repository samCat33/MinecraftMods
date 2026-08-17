package com.example.sammod.villager;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.sound.MySillySounds;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {

    //Points of Interest Registry
    //These are for the blocks the villagers need for their professions
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, SamMod.MOD_ID);

    //Villager Professions Registry
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, SamMod.MOD_ID);

    //This sets the vet station block as a PoiType, meaning that it is a workstation block for a villager
    //PoiType(BlockStates, how many villagers can use that block, how far away the villager
    //      has to be from the block in order to use it)
    public static final RegistryObject<PoiType> VET_POI = POI_TYPES.register("vet_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.VET_STATION.get().getStateDefinition().getPossibleStates()),
                    1, 1));


    //VillagerProfession(String name,
    //      Predicate heldJobSite,
    //      Predicate acquirableJobSites,
    //      ImmutableSet requestedItems,
    //      ImmutableSet secondaryPoi,
    //      SoundEvent workSound)

    public static final RegistryObject<VillagerProfession> VETERINARIAN =
            VILLAGER_PROFESSIONS.register("veterinarian",
                    () -> new VillagerProfession("veterinarian",
                            holder -> holder.value() == VET_POI.get(),
                            holder -> holder.value() == VET_POI.get(),
                            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BONE_BLOCK_PLACE));

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
