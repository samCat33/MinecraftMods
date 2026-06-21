package com.example.sammod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LightningEnchantmentEffect() implements EnchantmentEntityEffect {

    //MapCodec is used to define how to set up NBT data
    //and the unit function is used to declare the effect as
    //an empty object in a custom enchantment JSON file
    public static final MapCodec<LightningEnchantmentEffect> CODEC = MapCodec.unit(LightningEnchantmentEffect::new);


    //This is the method that is called when the enchantment effect is used
    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        if (pEnchantmentLevel == 1){
            EntityType.LIGHTNING_BOLT.spawn(pLevel, pEntity.getOnPos(), MobSpawnType.TRIGGERED);
        }

        else if (pEnchantmentLevel == 2){
            EntityType.LIGHTNING_BOLT.spawn(pLevel, pEntity.getOnPos(), MobSpawnType.TRIGGERED);
            EntityType.LIGHTNING_BOLT.spawn(pLevel, pEntity.getOnPos(), MobSpawnType.TRIGGERED);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
