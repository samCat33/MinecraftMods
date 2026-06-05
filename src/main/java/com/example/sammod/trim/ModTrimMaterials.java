package com.example.sammod.trim;

import com.example.sammod.SamMod;
import com.example.sammod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class ModTrimMaterials {

    //This is the trim material
    public static final ResourceKey<TrimMaterial> METEORITE =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "meteorite_ingot"));

    //This registers the trim material using the method below using
    //the context, trim material, the item associated with the trim material, the color of the text indicating the armor,
    //and the color of the trim (see ModItemModelProvider for floats associated with trim colors)
    public static void bootstrap(BootstrapContext<TrimMaterial> context) {

        //We have one register method for each custom trim
        register(context, METEORITE, ModItems.METEORITE_INGOT.get(), Style.EMPTY.withColor(TextColor.parseColor("#7f03fc").getOrThrow()), 1.0F);
    }


    //This method is called by the bootstrap method
    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                                 Style style, float itemModelIndex){
        //Create trim material with name, item, and color for trim
        TrimMaterial trimMaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,

            //Change the color of the text of the armor
            Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimMaterial);
    }

}
