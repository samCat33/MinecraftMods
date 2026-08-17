package com.example.sammod.loot;

import com.example.sammod.SamMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//The AddItemModifier class relies on this class in order to function
public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LMS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SamMod.MOD_ID);

    public static final RegistryObject<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM =
            LMS.register("add_item", () -> AddItemModifier.CODEC);

    public static void register(IEventBus eventBus){
        LMS.register(eventBus);
    }
}
