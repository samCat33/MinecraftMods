package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;


//Look at ItemModelProvider
public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static{
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }


    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SamMod.MOD_ID, existingFileHelper);
    }

    //The texture files must exist for these items or else an
    //InvocationTargetException will occur
    @Override
    protected void registerModels() {
        basicItem(ModItems.CATNIP.get());
        basicItem(ModItems.METEORITE_INGOT.get());
        basicItem(ModItems.RAW_METEORITE.get());
        basicItem(ModItems.BLUE_OPAL_SHARD.get());
        basicItem(ModItems.BLUE_OPAL_INGOT.get());
        basicItem(ModItems.METEORITE_HORSE_ARMOR.get());
        basicItem(ModItems.BIG_S_SMITHING_TEMPLATE.get());
        basicItem(ModItems.DUTTY_MUSIC_DISC.get());
        basicItem(ModItems.SECOND_SONG_MUSIC_DISC.get());

        basicItem(ModItems.UNCOOKED_RICE_BAG.get());
        basicItem(ModItems.COOKED_RICE_BAG.get());
        basicItem(ModItems.RICE_SEEDS.get());
        basicItem(ModItems.BLUEBERRIES.get());

        buttonItem(ModBlocks.SUSIE_BUTTON, ModBlocks.SUSIE_TNT);
        fenceItem(ModBlocks.SUSIE_FENCE, ModBlocks.SUSIE_TNT);
        wallItem(ModBlocks.SUSIE_WALL, ModBlocks.SUSIE_TNT);

        simpleBlockItem(ModBlocks.SUSIE_DOOR);

        handheldItem(ModItems.METEORITE_AXE);
        handheldItem(ModItems.METEORITE_PICKAXE);
        handheldItem(ModItems.METEORITE_SHOVEL);
        handheldItem(ModItems.METEORITE_SWORD);
        handheldItem(ModItems.METEORITE_HOE);
        handheldItem(ModItems.METEORITE_HAMMER);

        trimmedArmorItem(ModItems.METEORITE_HELMET);
        trimmedArmorItem(ModItems.METEORITE_CHESTPLATE);
        trimmedArmorItem(ModItems.METEORITE_LEGGINGS);
        trimmedArmorItem(ModItems.METEORITE_BOOTS);
    }

    //Documentation according to El_Redstoniano, who made this

    //This is similar to trimmedArmorItem method in ItemModelGenerators class, which is private
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject){
        final String MOD_ID = SamMod.MOD_ID;

        if (itemRegistryObject.get() instanceof ArmorItem armorItem){
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()){
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); //minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exists, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                //Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                //Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                        mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void buttonItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void fenceItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
