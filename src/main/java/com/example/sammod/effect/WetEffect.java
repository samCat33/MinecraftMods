package com.example.sammod.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

public class WetEffect extends MobEffect {

    public WetEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier){
        Level level = Minecraft.getInstance().level;

        //We are going to return true if it is raining and the player is outside
        if (level != null && !level.isClientSide()){

            if (entity instanceof Player player &&
            (level.isRaining() || level.isThundering())){

                BlockPos playerPos = player.blockPosition();

                if (level.canSeeSky(playerPos)){
                    return true;
                }
            }
        }

        return super.applyEffectTick(entity, amplifier);
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier){
        return true;
    }
}
