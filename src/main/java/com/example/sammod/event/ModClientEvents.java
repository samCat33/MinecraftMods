package com.example.sammod.event;

import com.example.sammod.SamMod;
import com.example.sammod.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    //This creates a zoom out effect that we use for our SUPER_BOW
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event){

        //If the player is using a Super Bow
        if (event.getPlayer().isUsingItem() &&
        event.getPlayer().getUseItem().getItem() == ModItems.SUPER_BOW.get()) {
            float fovModifier = 1f;

            //Get the ticks that the player has been using the bow for and divide
            //it by 20, the number of ticks per second, to get seconds
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float) ticksUsingItem / 20f;

            //If the player has been using the bow for more than one second
            //set deltaTicks to 1f to prevent the fov modifier from further
            //decreasing
            if (deltaTicks > 1f){
                deltaTicks = 1f;
            }

            else{
                deltaTicks *= deltaTicks;
            }

            //The fov goes down the longer we have been holding the bow
            //down for
            fovModifier *= 1f - deltaTicks * 0.3f;
            event.setNewFovModifier(fovModifier);
        }

        //Otherwise if the player is using a Super Spyglass
    }
}
