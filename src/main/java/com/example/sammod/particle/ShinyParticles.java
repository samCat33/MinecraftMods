package com.example.sammod.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ShinyParticles extends TextureSheetParticle {
    protected ShinyParticles(ClientLevel pLevel, double pX, double pY, double pZ,
                             SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ,
                pXSpeed, pYSpeed, pZSpeed);


        //friction is scaled 0-1
        this.friction = 0.8f;

        //How long the sprite lasts
        this.lifetime = 40;

        this.setSpriteFromAge(spriteSet);

        //This sets the colors
        //to be the same as the sprite texture
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        @Nullable
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
                                       double pXSpeed, double pYSpeed, double pZSpeed) {
            return new ShinyParticles(pLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
