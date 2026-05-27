package com.example.sammod.block;

import com.example.sammod.SamMod;
import com.example.sammod.block.blocks.CatNoteBlock;
import com.example.sammod.block.blocks.CoalConverter;
import com.example.sammod.block.blocks.LampBlock;
import com.example.sammod.block.blocks.SusieTNT;
import com.example.sammod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SamMod.MOD_ID);

    public static final RegistryObject<Block> SUSIE_TNT = registerBlock("susie_tnt",
            () -> new SusieTNT(BlockBehaviour.Properties.of().instabreak().strength(1.0F)));

    public static final RegistryObject<Block> CAT_NOTE_BLOCK = registerBlock("cat_note_block",
            () -> new CatNoteBlock(BlockBehaviour.Properties.of().instabreak().strength(1.0F)));

    public static final RegistryObject<Block> METEORITE_ORE = registerBlock("meteorite_ore",
            () -> new DropExperienceBlock(UniformInt.of(7, 10), BlockBehaviour.Properties.of()
                    .strength(6f, 9).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> METEORITE_DEEPSLATE_ORE = registerBlock("meteorite_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(8, 12), BlockBehaviour.Properties.of()
                    .strength(6.5f, 9).requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> RAW_METEORITE_BLOCK = registerBlock("raw_meteorite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(6f, 9).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> METEORITE_BLOCK = registerBlock("meteorite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops()));

    public static final RegistryObject<StairBlock> SUSIE_STAIRS = registerBlock("susie_stairs",
    () -> new StairBlock(ModBlocks.SUSIE_TNT.get().defaultBlockState(), BlockBehaviour.Properties.of()
            .instabreak().strength(1.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<SlabBlock> SUSIE_SLAB = registerBlock("susie_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<PressurePlateBlock> SUSIE_PRESSURE_PLATE = registerBlock("susie_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<ButtonBlock> SUSIE_BUTTON = registerBlock("susie_button",
            () -> new ButtonBlock(BlockSetType.IRON, 100, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<FenceBlock> SUSIE_FENCE = registerBlock("susie_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<FenceGateBlock> SUSIE_FENCE_GATE = registerBlock("susie_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<WallBlock> SUSIE_WALL = registerBlock("susie_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<DoorBlock> SUSIE_DOOR = registerBlock("susie_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops().noCollission()));
    public static final RegistryObject<TrapDoorBlock> SUSIE_TRAPDOOR = registerBlock("susie_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SUSIE_BLOCK = registerBlock("susie_block",
            () -> new Block(BlockBehaviour.Properties.of().instabreak().strength(1.0F)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CUSTOM_LAMP = registerBlock("custom_lamp",
            () -> new LampBlock(BlockBehaviour.Properties.of().strength(2f)
                    .lightLevel(state -> state.getValue(LampBlock.LIGHT_LEVEL))));

    public static final RegistryObject<Block> COAL_CONVERTER = registerBlock("coal_converter",
            () -> new CoalConverter(BlockBehaviour.Properties.of().strength(2f)
                    .requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
