package com.example.sammod.block;

import com.example.sammod.SamMod;
import com.example.sammod.block.blocks.*;
import com.example.sammod.item.ModItems;
import com.example.sammod.sound.MySillySounds;
import com.example.sammod.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SamMod.MOD_ID);

    //Deepslate diamond ore has a hardness of 4.5f
    //Deepslate blocks have a blast resistance of 3

    //IMPORTANT: The registerBlock method defined below is necessary for all blocks
    //that we also want to be able to hold as an item because they must
    //specifically be registered as a BlockItem

    public static final RegistryObject<Block> CAT_NOTE_BLOCK = registerBlock("cat_note_block",
            () -> new CatNoteBlock(BlockBehaviour.Properties.of().instabreak().strength(1.0F)));

    public static final RegistryObject<Block> METEORITE_ORE = registerBlock("meteorite_ore",
            () -> new DropExperienceBlock(UniformInt.of(7, 10), BlockBehaviour.Properties.of()
                    .strength(5f, 9).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> METEORITE_DEEPSLATE_ORE = registerBlock("meteorite_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(8, 12), BlockBehaviour.Properties.of()
                    .strength(5.5f, 9).requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> RAW_METEORITE_BLOCK = registerBlock("raw_meteorite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f, 9).requiresCorrectToolForDrops().sound(MySillySounds.METEORITE_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> METEORITE_BLOCK = registerBlock("meteorite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(MySillySounds.METEORITE_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> SUSIE_TNT = registerBlock("susie_tnt",
            () -> new SusieTNT(BlockBehaviour.Properties.of().instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<StairBlock> SUSIE_STAIRS = registerBlock("susie_stairs",
    () -> new StairBlock(ModBlocks.SUSIE_TNT.get().defaultBlockState(), BlockBehaviour.Properties.of()
            .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));
    public static final RegistryObject<SlabBlock> SUSIE_SLAB = registerBlock("susie_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<PressurePlateBlock> SUSIE_PRESSURE_PLATE = registerBlock("susie_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));
    public static final RegistryObject<ButtonBlock> SUSIE_BUTTON = registerBlock("susie_button",
            () -> new ButtonBlock(BlockSetType.IRON, 100, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).noCollission().sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<FenceBlock> SUSIE_FENCE = registerBlock("susie_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));
    public static final RegistryObject<FenceGateBlock> SUSIE_FENCE_GATE = registerBlock("susie_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));
    public static final RegistryObject<WallBlock> SUSIE_WALL = registerBlock("susie_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<DoorBlock> SUSIE_DOOR = registerBlock("susie_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).noCollission().sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));
    public static final RegistryObject<TrapDoorBlock> SUSIE_TRAPDOOR = registerBlock("susie_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of()
                    .instabreak().strength(1.0F).sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> SUSIE_BLOCK = registerBlock("susie_block",
            () -> new Block(BlockBehaviour.Properties.of().instabreak().strength(1.0F)
                    .sound(MySillySounds.SUSIE_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> CUSTOM_LAMP = registerBlock("custom_lamp",
            () -> new LampBlock(BlockBehaviour.Properties.of().strength(2f)
                    .lightLevel(state -> state.getValue(LampBlock.LIGHT_LEVEL))));

    public static final RegistryObject<Block> COAL_CONVERTER = registerBlock("coal_converter",
            () -> new CoalConverter(BlockBehaviour.Properties.of().strength(2f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RICE_CROP = BLOCKS.register("rice_crop",
            () -> new RiceCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

    public static final RegistryObject<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush",

            //We are only able to do a full copy of a sweet berry bush
            //because we have not changed the AGE property of SweetBerryBush
            () -> new BlueberryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final RegistryObject<Block> BLUE_OPAL_ORE = registerBlock("blue_opal_ore",
            () -> new DropExperienceBlock(UniformInt.of(8, 12), BlockBehaviour.Properties.of()
                    .strength(8f, 12).requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<RotatedPillarBlock> REDWOOD_LOG = registerBlock("redwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> REDWOOD_WOOD = registerBlock("redwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_REDWOOD_LOG = registerBlock("stripped_redwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_REDWOOD_WOOD = registerBlock("stripped_redwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));


    //These redwood planks use an anonymous class to override certain properties
    public static final RegistryObject<Block> REDWOOD_PLANKS = registerBlock("redwood_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });

    //Like the redwood planks, these redwood leaves use an anonymous class to override
    //flammability and also the fire spread speed
    public static final RegistryObject<Block> REDWOOD_LEAVES = registerBlock("redwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final RegistryObject<Block> REDWOOD_SAPLING = registerBlock("redwood_sapling",
            () -> new SaplingBlock(ModTreeGrowers.REDDWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

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
