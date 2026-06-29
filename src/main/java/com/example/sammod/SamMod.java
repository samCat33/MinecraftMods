package com.example.sammod;

import com.example.sammod.block.ModBlocks;
import com.example.sammod.component.ModDataComponentTypes;
import com.example.sammod.effect.ModEffects;
import com.example.sammod.enchantment.ModEnchantmentEffects;
import com.example.sammod.item.ModCreativeModeTabs;
import com.example.sammod.item.ModItems;
import com.example.sammod.potion.ModPotions;
import com.example.sammod.sound.MySillySounds;
import com.example.sammod.util.ModItemProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SamMod.MOD_ID)
public class SamMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "sammod";

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public SamMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //Register creative mode tabs, items, blocks, and data component types
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        MySillySounds.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.UNCOOKED_RICE_BAG.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.RICE_SEEDS.get(), 0.1f);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.CATNIP);
            event.accept(ModItems.RAW_METEORITE);
            event.accept(ModItems.METEORITE_INGOT);

            event.accept(ModItems.BLUE_OPAL_SHARD);
            event.accept(ModItems.BLUE_OPAL_INGOT);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.RICE_SEEDS.get());
            event.accept(ModItems.UNCOOKED_RICE_BAG.get());
            event.accept(ModItems.COOKED_RICE_BAG.get());
            event.accept(ModItems.BLUEBERRIES.get());
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.MIDAS_TOUCH);
            event.accept(ModItems.METEORITE_SWORD.get());
            event.accept(ModItems.METEORITE_AXE.get());
            event.accept(ModItems.METEORITE_HAMMER.get());

            event.accept(ModItems.METEORITE_HELMET.get());
            event.accept(ModItems.METEORITE_CHESTPLATE.get());
            event.accept(ModItems.METEORITE_LEGGINGS.get());
            event.accept(ModItems.METEORITE_BOOTS.get());

            event.accept(ModItems.METEORITE_HORSE_ARMOR.get());
            event.accept(ModItems.SUPER_BOW.get());
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.METEORITE_PICKAXE.get());
            event.accept(ModItems.METEORITE_SHOVEL.get());
            event.accept(ModItems.METEORITE_HOE.get());
        }


        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.SUSIE_TNT);
            event.accept(ModBlocks.METEORITE_ORE);
            event.accept(ModBlocks.METEORITE_DEEPSLATE_ORE);
            event.accept(ModBlocks.METEORITE_BLOCK);
            event.accept(ModBlocks.RAW_METEORITE_BLOCK);
            event.accept(ModBlocks.CAT_NOTE_BLOCK);
            event.accept(ModBlocks.CUSTOM_LAMP);
            event.accept(ModBlocks.COAL_CONVERTER);
            event.accept(ModBlocks.SUSIE_BLOCK.get());
            event.accept(ModBlocks.SUSIE_BUTTON.get());
            event.accept(ModBlocks.SUSIE_DOOR.get());
            event.accept(ModBlocks.SUSIE_FENCE.get());
            event.accept(ModBlocks.SUSIE_FENCE_GATE.get());
            event.accept(ModBlocks.SUSIE_PRESSURE_PLATE.get());
            event.accept(ModBlocks.SUSIE_SLAB.get());
            event.accept(ModBlocks.SUSIE_STAIRS.get());
            event.accept(ModBlocks.SUSIE_TRAPDOOR.get());
            event.accept(ModBlocks.SUSIE_WALL.get());

            event.accept(ModBlocks.BLUE_OPAL_ORE.get());

            event.accept(ModBlocks.REDWOOD_LOG.get());
            event.accept(ModBlocks.REDWOOD_WOOD.get());
            event.accept(ModBlocks.STRIPPED_REDWOOD_LOG.get());
            event.accept(ModBlocks.STRIPPED_REDWOOD_WOOD.get());
            event.accept(ModBlocks.REDWOOD_PLANKS.get());
            event.accept(ModBlocks.REDWOOD_LEAVES.get());
            event.accept(ModBlocks.REDWOOD_SAPLING.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            //CustomItemProperties is on the client side
            ModItemProperties.addCustomItemProperties();
        }
    }
}
