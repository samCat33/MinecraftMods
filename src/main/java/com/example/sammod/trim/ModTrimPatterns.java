package com.example.sammod.trim;

import com.example.sammod.SamMod;
import com.example.sammod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTrimPatterns {

    //This is the trim pattern
    public static final ResourceKey<TrimPattern> BIG_S = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "big_s"));


    //This method calls the method below it with the given context
    public static void bootstrap(BootstrapContext<TrimPattern> context){
        register(context, ModItems.BIG_S_SMITHING_TEMPLATE.get(), BIG_S);
    }

    //The trim pattern register method is similar to the register method for the trim material
    private static void register(BootstrapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> key){
        TrimPattern trimPattern = new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }

}
