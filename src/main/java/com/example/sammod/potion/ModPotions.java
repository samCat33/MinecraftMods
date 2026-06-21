package com.example.sammod.potion;

import com.example.sammod.SamMod;
import com.example.sammod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    //Add the DeferredRegister for potions (<Potion> type)
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, SamMod.MOD_ID);


    //Add the sticky potion
    public static final RegistryObject<Potion> STICKY_POTION = POTIONS.register(SamMod.MOD_ID + ".sticky_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.STICKY_EFFECT.getHolder().get(), 3600, 0)));


    //Method to register the POTIONS DeferredRegister
    public static void register(IEventBus bus) {
        POTIONS.register(bus);
    }
}
