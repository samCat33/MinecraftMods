package com.example.sammod.particle;


import com.example.sammod.SamMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    //We need a new register for our custom particles
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SamMod.MOD_ID);

    //The name matches what is found under resources/assets/sammod/particles
    public static final RegistryObject<SimpleParticleType> SHINY_PARTICLES =
            PARTICLE_TYPES.register("shiny_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);

        //sendParticles is server-side
        //sendParticles(particle option, x-position, y-position, z-position, count, x-offset, y-offset, z-offset, speed)
        //the position coordinates are anchored on the bottom-right side of the block (I think)

        //This creates new particles according to the type of block that was converted
            /*((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, clickedBlock.defaultBlockState()),
                    context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.0, context.getClickedPos().getZ() + 0.5,
                    10, 0, 0, 0, 1);*/


        //addParticle(Particle, x-pos, y-pos, z-pos, x-speed, y-speed, z-speed)
        //This is another way to add particles, but one at a time
        //This is also a client-side function instead of a server-side function
    }

}
