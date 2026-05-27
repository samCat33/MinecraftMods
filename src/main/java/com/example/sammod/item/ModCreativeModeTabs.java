package com.example.sammod.item;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SamMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SAMMODITEMS =
            CREATIVE_MODE_TABS.register("moditems",
                    () ->CreativeModeTab.builder().icon(() ->
                            new ItemStack(ModItems.CATNIP.get()))
                            .title(Component.translatable("creativetab.sammod.samitems"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(ModItems.CATNIP.get());
                                output.accept(ModItems.RAW_METEORITE.get());
                                output.accept(ModItems.METEORITE_INGOT.get());
                                output.accept(ModItems.MIDAS_TOUCH.get());
                                output.accept(ModItems.METEORITE_SWORD.get());
                                output.accept(ModItems.METEORITE_PICKAXE.get());
                                output.accept(ModItems.METEORITE_AXE.get());
                                output.accept(ModItems.METEORITE_SHOVEL.get());
                                output.accept(ModItems.METEORITE_HOE.get());

                            }).build());

    public static final RegistryObject<CreativeModeTab> SAMMODBLOCKS =
            CREATIVE_MODE_TABS.register("modblocks",
                    () ->CreativeModeTab.builder().icon(() ->
                            new ItemStack(ModBlocks.SUSIE_TNT.get()))
                            .withTabsBefore((SAMMODITEMS.getId()))
                            .title(Component.translatable("creativetab.sammod.samblocks"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(ModBlocks.METEORITE_BLOCK.get());
                                output.accept(ModBlocks.RAW_METEORITE_BLOCK.get());
                                output.accept(ModBlocks.METEORITE_ORE.get());
                                output.accept(ModBlocks.METEORITE_DEEPSLATE_ORE.get());
                                output.accept(ModBlocks.SUSIE_TNT.get());
                                output.accept(ModBlocks.CAT_NOTE_BLOCK.get());
                                output.accept(ModBlocks.SUSIE_BLOCK.get());
                                output.accept(ModBlocks.SUSIE_STAIRS.get());
                                output.accept(ModBlocks.SUSIE_BUTTON.get());

                                output.accept(ModBlocks.SUSIE_WALL.get());
                                output.accept(ModBlocks.SUSIE_FENCE.get());

                                output.accept(ModBlocks.SUSIE_FENCE_GATE.get());
                                output.accept(ModBlocks.SUSIE_TRAPDOOR.get());
                                output.accept(ModBlocks.SUSIE_DOOR.get());

                                output.accept(ModBlocks.SUSIE_SLAB.get());
                                output.accept(ModBlocks.SUSIE_PRESSURE_PLATE.get());

                                output.accept(ModBlocks.CUSTOM_LAMP.get());
                                output.accept(ModBlocks.COAL_CONVERTER.get());
                            }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
