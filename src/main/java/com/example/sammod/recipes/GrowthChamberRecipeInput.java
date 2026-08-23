package com.example.sammod.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record GrowthChamberRecipeInput(ItemStack animal, ItemStack animalFood) implements RecipeInput {

    @Override
    public ItemStack getItem(int pIndex) {
        return switch(pIndex) {
            case 0 -> animal;
            case 1 -> animalFood;
            default -> ItemStack.EMPTY;
        };
    }

    //This is for the number of inputs
    @Override
    public int size() {
        return 2;
    }
}
