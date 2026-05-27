package com.example.sammod.component;

import com.example.sammod.SamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    //This creates the registry for data components
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, SamMod.MOD_ID);


    //Here we are registering a BlockPos data component
    public static final RegistryObject<DataComponentType<BlockPos>> COORDINATES = register("coordinates",
            //CODEC allows coordinates to turn into json files (serialization and deserialization)
            builder -> builder.persistent(BlockPos.CODEC));

    //This is the method for registering data components
    private static <T> RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    //This registers the data component registry
    public static void register(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }
}
