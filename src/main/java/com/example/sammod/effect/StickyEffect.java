package com.example.sammod.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StickyEffect extends MobEffect {
    public StickyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier){


        //We are going to return true if it is raining and the player is outside
        if (entity.horizontalCollision){
            Vec3 initVec = entity.getDeltaMovement();
            Vec3 climbVec = new Vec3(initVec.x, 0.20 * (amplifier + 1), initVec.z);
            entity.setDeltaMovement(climbVec.scale(1.01));
            return true;
        }

        return super.applyEffectTick(entity, amplifier);
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier){
        return true;
    }
}
