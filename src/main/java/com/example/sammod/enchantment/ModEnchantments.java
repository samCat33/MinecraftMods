package com.example.sammod.enchantment;

import com.example.sammod.SamMod;
import com.example.sammod.enchantment.custom.LightningEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {

    //Creates a key for the in-game enchantment asset
    public static final ResourceKey<Enchantment> LIGHTNING =
            ResourceKey.create(Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "lightning"));

    //bootstrap() always means datagen
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        //var is like auto in C++
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);


        register(context, LIGHTNING, Enchantment.enchantment(Enchantment.definition(
                //Supported Items
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),

                //Primary Items (works with enchanting table)
                items.getOrThrow(ItemTags.BOW_ENCHANTABLE),

                //How common the enchantment is on a scale of (1-10)
                3,

                //Max level
                2,

                //Minimum required enchanting power
                Enchantment.dynamicCost(5, 8),

                //Maximum required enchanting power
                Enchantment.dynamicCost(25, 8),

                //Base cost for adding this enchantment using an anvil,
                //(this number gets scaled up if already enchanted)
                2,

                //The enchantment only applies if the player is holding it in their main hand
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.BOW_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightningEnchantmentEffect()));
    }

    //helper method used by bootstrap()
    private static void register(BootstrapContext<Enchantment> registry,
                                 ResourceKey<Enchantment> key, Enchantment.Builder builder){
        registry.register(key, builder.build(key.location()));
    }


}
