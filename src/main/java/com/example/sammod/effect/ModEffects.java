package com.example.sammod.effect;

import com.example.sammod.SamMod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    //Create the register for custom mob effects
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SamMod.MOD_ID);


    //Define the wet effect
    public static final RegistryObject<MobEffect> WET_EFFECT = MOB_EFFECTS.register("wet",
            () -> new WetEffect(MobEffectCategory.NEUTRAL, 0x4040fe).

                    //Attribute modified, ID of modifier, amount the attribute is modified, operation to do with amount
            addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "wet"),
                    -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    //Here we are going to make the sticky effect
    public static final RegistryObject<MobEffect> STICKY_EFFECT = MOB_EFFECTS.register("sticky",
            () -> new StickyEffect(MobEffectCategory.NEUTRAL, 0xdfdfdf).
                    addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "sticky"),
                            -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "sticky"),
                            -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    //Called in our main (SamMod)
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
