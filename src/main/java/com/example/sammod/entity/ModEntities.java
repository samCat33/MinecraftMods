package com.example.sammod.entity;

import com.example.sammod.SamMod;
import com.example.sammod.entity.custom.ChairEntity;
import com.example.sammod.entity.custom.TomahawkProjectileEntity;
import com.example.sammod.entity.custom.TriceratopsEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SamMod.MOD_ID);

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS =
            //Ths sized() method creates the bounding box for the triceratops
            ENTITY_TYPES.register("triceratops", () -> EntityType.Builder.of(TriceratopsEntity::new,
                    MobCategory.CREATURE).sized(1.5f, 1.5f).build("triceratops"));


    public static final RegistryObject<EntityType<TomahawkProjectileEntity>> TOMAHAWK =
            ENTITY_TYPES.register("tomahawk", () -> EntityType.Builder.<TomahawkProjectileEntity>of(TomahawkProjectileEntity::new,
            MobCategory.MISC).sized(0.5f, 1.05f).build("tomahawk"));

    public static final RegistryObject<EntityType<ChairEntity>> CHAIR_ENTITY =
            ENTITY_TYPES.register("chair_entity", () -> EntityType.Builder.of(ChairEntity::new,
                    MobCategory.MISC).sized(0.5f, 0.5f).build("chair_entity"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
