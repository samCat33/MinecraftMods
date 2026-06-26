package com.example.sammod.datagen;

import com.example.sammod.SamMod;
import com.example.sammod.block.ModBlocks;
import com.example.sammod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//Look at RecipeProvider
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }


    //Look at RecipeOutput
    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> METEORITE_SMELTABLES =
                List.of(ModItems.RAW_METEORITE.get(), ModBlocks.METEORITE_ORE.get(),
                ModBlocks.METEORITE_DEEPSLATE_ORE.get());

        List <ItemLike> RICE =
                List.of(ModItems.UNCOOKED_RICE_BAG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.METEORITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.METEORITE_INGOT.get())
                .unlockedBy(getHasName(ModItems.METEORITE_INGOT.get()), has(ModItems.METEORITE_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_METEORITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.RAW_METEORITE.get())
                .unlockedBy(getHasName(ModItems.RAW_METEORITE.get()), has(ModItems.RAW_METEORITE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MIDAS_TOUCH.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.GOLD_BLOCK)
                .define('B', Items.NETHERITE_BLOCK)
                .unlockedBy(getHasName(Items.NETHERITE_BLOCK), has(Items.NETHERITE_BLOCK)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CATNIP.get())
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.WHEAT_SEEDS)
                .define('B', Items.SUGAR_CANE)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUSIE_BLOCK.get())
                .pattern("BAA")
                .pattern("ABA")
                .pattern("AAB")
                .define('A', ModItems.CATNIP.get())
                .define('B', Items.COD)
                .unlockedBy(getHasName(ModItems.CATNIP.get()), has(ModItems.CATNIP.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUSIE_TNT.get())
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', ModBlocks.SUSIE_BLOCK.get())
                .define('B', Items.TNT)
                .unlockedBy(getHasName(Items.TNT), has(Items.TNT)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COAL_CONVERTER.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Blocks.DEEPSLATE_COAL_ORE)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.METEORITE_PICKAXE.get())
                .pattern("AAA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.METEORITE_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.METEORITE_AXE.get())
                .pattern("AA ")
                .pattern("AB ")
                .pattern(" B ")
                .define('A', ModItems.METEORITE_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.METEORITE_SWORD.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', ModItems.METEORITE_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.METEORITE_SHOVEL.get())
                .pattern(" A ")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.METEORITE_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.METEORITE_HOE.get())
                .pattern("AA ")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', ModItems.METEORITE_INGOT.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.DEEPSLATE_COAL_ORE), has(Items.DEEPSLATE_COAL_ORE)).save(pRecipeOutput);


        //.save(pRecipeOutput, SamMod.MOD_ID + ":string") is for when two recipes have the same output to avoid errors

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.METEORITE_INGOT.get(), 9)
                .requires(ModBlocks.METEORITE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.METEORITE_BLOCK.get()), has(ModBlocks.METEORITE_BLOCK.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_METEORITE.get(), 9)
                .requires(ModBlocks.RAW_METEORITE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RAW_METEORITE_BLOCK.get()), has(ModBlocks.RAW_METEORITE_BLOCK.get())).save(pRecipeOutput);


        //These are all the furnace recipes
        oreSmelting(pRecipeOutput, METEORITE_SMELTABLES, RecipeCategory.MISC, ModItems.METEORITE_INGOT.get(), 5, 400, "meteorite");
        oreBlasting(pRecipeOutput, METEORITE_SMELTABLES, RecipeCategory.MISC, ModItems.METEORITE_INGOT.get(), 5, 80, "meteorite");
        foodSmoking(pRecipeOutput, RICE, RecipeCategory.FOOD, ModItems.COOKED_RICE_BAG.get(), 1, 100, "rice");
        foodCooking(pRecipeOutput, RICE, RecipeCategory.FOOD, ModItems.COOKED_RICE_BAG.get(), 1, 200, "rice");

        //All the susie block variant recipes
        stairBuilder(ModBlocks.SUSIE_STAIRS.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUSIE_SLAB.get(), ModBlocks.SUSIE_BLOCK.get());

        fenceBuilder(ModBlocks.SUSIE_FENCE.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        pressurePlate(pRecipeOutput, ModBlocks.SUSIE_PRESSURE_PLATE.get(), ModBlocks.SUSIE_BLOCK.get());

        fenceGateBuilder(ModBlocks.SUSIE_FENCE_GATE.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        wall(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUSIE_WALL.get(), ModBlocks.SUSIE_BLOCK.get());

        doorBuilder(ModBlocks.SUSIE_DOOR.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        trapdoorBuilder(ModBlocks.SUSIE_TRAPDOOR.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        buttonBuilder(ModBlocks.SUSIE_BUTTON.get(), Ingredient.of(ModBlocks.SUSIE_BLOCK.get())).group("susie")
                .unlockedBy(getHasName(ModBlocks.SUSIE_BLOCK.get()), has(ModBlocks.SUSIE_BLOCK.get())).save(pRecipeOutput);

        trimSmithing(pRecipeOutput, ModItems.BIG_S_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "big_s"));

    }

    protected static void oreSmelting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void foodSmoking(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ){
        oreCooking(pRecipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smoking");
    }

    protected static void foodCooking(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ){
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    private static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput pRecipeOutput,
            RecipeSerializer<T> pSerializer,
            AbstractCookingRecipe.Factory<T> pRecipeFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pSuffix
    ) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, SamMod.MOD_ID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }
}
