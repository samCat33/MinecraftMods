package com.example.sammod.enchantment;

import com.example.sammod.SamMod;
import com.example.sammod.enchantment.custom.LightningEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantmentEffects {

    //Create the deferred register
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>>
             ENTITY_ENCHANTMENT_EFFECTS = DeferredRegister.create
            (Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, SamMod.MOD_ID);


    //Create a codec for a new enchantment
    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>>
    LIGHTNING_STRIKER = ENTITY_ENCHANTMENT_EFFECTS.register("lightning",
            () -> LightningEnchantmentEffect.CODEC);


    //Used in our main class to register the ENTITY_ENCHANTMENT_EFFECTS register
    public static void register(IEventBus eventBus){
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
