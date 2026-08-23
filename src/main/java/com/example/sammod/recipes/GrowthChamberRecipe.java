package com.example.sammod.recipes;

import com.example.sammod.screen.custom.GrowthChamberScreen;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

//This recipe class defines how to read in the JSON file
public record GrowthChamberRecipe(Ingredient input1, Ingredient input2, ItemStack output)
implements Recipe<GrowthChamberRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input1);
        list.add(input2);
        return list;
    }

    @Override
    public boolean matches(GrowthChamberRecipeInput pInput, Level pLevel) {

        //Do not run this on the client side
        if (pLevel.isClientSide) {
            return false;
        }

        //Return whether the input items of the recipe are the same as
        //what is currently inside the Growth Chamber's slots
        return  input1.test(pInput.getItem(0)) &&
                input2.test(pInput.getItem(1));

    }


    //This actually crafts the recipe
    @Override
    public ItemStack assemble(GrowthChamberRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output.copy();
    }


    //The RecipeSerializer and the RecipeType are registered
    //in the ModRecipes class, and in the same way
    //that all the other registries are registered
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GROWTH_CHAMBER_TYPE.get();
    }

    //Codecs basically define how you read in a JSON file
    public static class Serializer implements RecipeSerializer<GrowthChamberRecipe> {
        public static final MapCodec<GrowthChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                //Map the input ingredients to fields inside the JSON file
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(GrowthChamberRecipe::input1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(GrowthChamberRecipe::input2),

                //Map the output item to a field inside the JSON file
                        ItemStack.CODEC.fieldOf("result").forGetter(GrowthChamberRecipe::output))
                //Create a new instance of the GrowthChamberRecipe after defining how to read
                //from a JSON file
                .apply(inst, GrowthChamberRecipe::new));

        //StreamCodec allows us to share between client and server
        public static final StreamCodec<RegistryFriendlyByteBuf, GrowthChamberRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, GrowthChamberRecipe::input1,
                        Ingredient.CONTENTS_STREAM_CODEC, GrowthChamberRecipe::input2,
                        ItemStack.STREAM_CODEC, GrowthChamberRecipe::output,
                        GrowthChamberRecipe::new);

        @Override
        public MapCodec<GrowthChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GrowthChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
