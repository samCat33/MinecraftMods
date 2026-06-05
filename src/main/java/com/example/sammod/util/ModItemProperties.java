package com.example.sammod.util;

import com.example.sammod.SamMod;
import com.example.sammod.component.ModDataComponentTypes;
import com.example.sammod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


//Look at ItemProperties.java for vanilla Item Properties
public class ModItemProperties {
    public static void addCustomItemProperties() {
        //Register an Item Property for the Midas Touch Item, call it the "used" property and set it to 0 or 1 according to whether
        //it has a Coordinates data component, because if it does, that means it has been used.
        ItemProperties.register(ModItems.MIDAS_TOUCH.get(), ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.get(ModDataComponentTypes.COORDINATES.get()) != null ? 1f : 0f);

        makeCustomBow(ModItems.SUPER_BOW.get());
    }

    //Same as the ItemProperties bow methods except we replace the first register argument with the custom item
    //This adds the listed properites below
    private static void makeCustomBow(Item item){

        //Float property of how much we have pulled it
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (p_340951_, p_340952_, p_340953_, p_340954_) -> {

            //If we are not pulling the bow (living entity parameter)
            if (p_340953_ == null) {
                return 0.0F;
            }
            //If the item we are pulling is not the bow, return 0, otherwise return how long we have been pulling for
            else {
                return p_340953_.getUseItem() != p_340951_ ? 0.0F : (float)(p_340951_.getUseDuration(p_340953_) - p_340953_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        //Boolean property of whether we are pulling the bow
        ItemProperties.register(
                item,
                ResourceLocation.withDefaultNamespace("pulling"),
                (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                    return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
                });
    }
}

