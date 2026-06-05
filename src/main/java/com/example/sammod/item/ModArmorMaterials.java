package com.example.sammod.item;

import com.example.sammod.SamMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> METEORITE_ARMOR_MATERIAL = register("meteorite", Util.make(new EnumMap<>(ArmorItem.Type.class),

            //These are each of the entries in the EnumMap
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 5);
                attribute.put(ArmorItem.Type.LEGGINGS, 7);
                attribute.put(ArmorItem.Type.CHESTPLATE, 10);
                attribute.put(ArmorItem.Type.HELMET, 5);
                attribute.put(ArmorItem.Type.BODY, 11);

                //The rest of the parameters are enchantability, toughness, knockbackResistance
                //and the ingredient item for repairing
            }), 15, 4f, 0.1f, () -> ModItems.METEORITE_INGOT.get());

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchatnability, float toughness, float knockbackResistance,
                                                  Supplier<Item> ingredientItem){
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name);
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_LEATHER;

        //This is the repair ingredient
        Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()){
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchatnability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }
}
