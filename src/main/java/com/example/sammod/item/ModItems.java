package com.example.sammod.item;

import com.example.sammod.SamMod;
import com.example.sammod.item.items.FuelItem;
import com.example.sammod.item.items.MidasTouchItem;
import net.minecraft.network.chat.Component;
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
                    .attributes(SwordItem.createAttributes(ModToolTiers.METEORITE, 3, -2.4F))));

    public static final RegistryObject<Item> METEORITE_PICKAXE = ITEMS.register("meteorite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.METEORITE, 1, -2.8F))));

    public static final RegistryObject<Item> METEORITE_AXE = ITEMS.register("meteorite_axe",
            () -> new AxeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.METEORITE, 6, -3.2F))));

    public static final RegistryObject<Item> METEORITE_SHOVEL = ITEMS.register("meteorite_shovel",
            () -> new ShovelItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.METEORITE, 1.5F, -3.0F))));

    public static final RegistryObject<Item> METEORITE_HOE = ITEMS.register("meteorite_hoe",
            () -> new HoeItem(ModToolTiers.METEORITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.METEORITE, 0, -3.0F))));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
