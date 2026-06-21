package com.example.sammod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CATNIP = new FoodProperties.Builder().nutrition(1).
            saturationModifier(0.1F)
            .effect(new MobEffectInstance(MobEffects.POISON, 200), 0.1F).build();

    public static final FoodProperties RICE = new FoodProperties.Builder().nutrition(1).
            saturationModifier(0.3F).build();

}
