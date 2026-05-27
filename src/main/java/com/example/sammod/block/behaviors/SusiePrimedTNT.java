package com.example.sammod.block.behaviors;

import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SusiePrimedTNT extends PrimedTnt {
    public SusiePrimedTNT(Level level, double x, double y, double z,
                          Player player){
        super(level, x, y, z, player);
    }

    @Override
    protected void explode(){
        this.level().explode(this, this.getX(), this.getY(), this.getZ(),
                25.0F, Level.ExplosionInteraction.TNT);
    }
}
