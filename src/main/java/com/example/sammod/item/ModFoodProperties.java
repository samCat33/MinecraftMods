package com.example.sammod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    //fast() allows the player to eat them faster
    public static final FoodProperties CATNIP = new FoodProperties.Builder().nutrition(1).
            saturationModifier(0.1F).fast()
            .effect(new MobEffectInstance(MobEffects.POISON, 200), 0.1F).build();

    public static final FoodProperties UNCOOKED_RICE = new FoodProperties.Builder().nutrition(1).
            saturationModifier(0.3F).build();

    public static final FoodProperties COOKED_RICE = new FoodProperties.Builder().nutrition(4).
            saturationModifier(0.5F).build();

    public static final FoodProperties BLUEBERRIES = new FoodProperties.Builder().nutrition(1).
            saturationModifier(0.1F).fast().build();

}
