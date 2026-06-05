package com.example.sammod.item;

import com.example.sammod.SamMod;
import com.example.sammod.item.items.FuelItem;
import com.example.sammod.item.items.HammerItem;
import com.example.sammod.item.items.MidasTouchItem;
import com.example.sammod.item.items.ModArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SamMod.MOD_ID);

    public static final RegistryObject<Item> CATNIP = ITEMS.register("catnip",
            () -> new FuelItem(new Item.Properties().food(ModFoodProperties.CATNIP), 40));

    public static final RegistryObject<Item> RAW_METEORITE = ITEMS.register("raw_meteorite",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> METEORITE_INGOT = ITEMS.register("meteorite_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MIDAS_TOUCH = ITEMS.register("midas_touch",
            () -> new MidasTouchItem(new Item.Properties().durability(32768)){

                    @Override
                    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> TooltipComponents, TooltipFlag TooltipFlag){
                        TooltipComponents.add(Component.translatable("tooltip.midas_touch1"));
                        TooltipComponents.add(Component.translatable("tooltip.midas_touch2"));
                    }

            });

    public static final RegistryObject<Item> METEORITE_SWORD = ITEMS.register("meteorite_sword",
            () -> new SwordItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.METEORITE, 7, -2.4F))));

    public static final RegistryObject<Item> METEORITE_PICKAXE = ITEMS.register("meteorite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.METEORITE, 5, -2.8F))));

    public static final RegistryObject<Item> METEORITE_AXE = ITEMS.register("meteorite_axe",
            () -> new AxeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.METEORITE, 10, -3.2F))));

    public static final RegistryObject<Item> METEORITE_SHOVEL = ITEMS.register("meteorite_shovel",
            () -> new ShovelItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.METEORITE, 5.5F, -3.0F))));

    public static final RegistryObject<Item> METEORITE_HOE = ITEMS.register("meteorite_hoe",
            () -> new HoeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.METEORITE, 0, -3.0F))));

    public static final RegistryObject<Item> METEORITE_HAMMER = ITEMS.register("meteorite_hammer",
            () -> new HammerItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.METEORITE, 13F, -3.8F))));


    //Look at Items.java and then for vanilla armor sets to compare durability factors
    public static final RegistryObject<Item> METEORITE_HELMET = ITEMS.register("meteorite_helmet",
            () -> new ModArmorItem(ModArmorMaterials.METEORITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(42))));

    public static final RegistryObject<Item> METEORITE_CHESTPLATE = ITEMS.register("meteorite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.METEORITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(42))));

    public static final RegistryObject<Item> METEORITE_LEGGINGS = ITEMS.register("meteorite_leggings",
            () -> new ArmorItem(ModArmorMaterials.METEORITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(42))));

    public static final RegistryObject<Item> METEORITE_BOOTS = ITEMS.register("meteorite_boots",
            () -> new ArmorItem(ModArmorMaterials.METEORITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(42))));


    public static final RegistryObject<Item> METEORITE_HORSE_ARMOR = ITEMS.register("meteorite_horse_armor",
            () -> new AnimalArmorItem(ModArmorMaterials.METEORITE_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN,
                    false, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BIG_S_SMITHING_TEMPLATE = ITEMS.register("big_s_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "big_s")));

    public static final RegistryObject<Item> SUPER_BOW = ITEMS.register("super_bow",
            () -> new BowItem(new Item.Properties().durability(768)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
