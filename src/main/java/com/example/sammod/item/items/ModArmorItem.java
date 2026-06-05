package com.example.sammod.item.items;

import com.example.sammod.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

//This class only needs to be applied to one custom armor item in the set
//This class also only works with non-vanilla armor
public class ModArmorItem extends ArmorItem{


    //Maps an armor material to an immutable list of effects
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.METEORITE_ARMOR_MATERIAL,
                            List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1, false, false),
                                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, false, false)))
                    .build();

    //Constructor that is like ArmorItem constructor
    public ModArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex){
        //If the player has a full suit of armor (evaluate on server side)
        if (!level.isClientSide() && hasFullSuitOfArmorOn(player)){

            //Evaluate armor effects
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player){

        //Here we loop through all our custom armor materials
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : EFFECT_MAP.entrySet()){
            Holder<ArmorMaterial> armorMaterial = entry.getKey();
            List<MobEffectInstance> effect = entry.getValue();

            //Check if the player has the associated armorMaterial equipped
            if (hasPlayerCorrectArmorOn(armorMaterial, player)){

                //Add the corresponding armor material's effects to the player
                //if they have that armor material equipped
                addEffectToPlayer(player, effect);
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> effects){

        //Make sure the player does not already have all the effects in the list of effects
        boolean hasPlayerEffect = effects.stream().allMatch(theEffect -> player.hasEffect(theEffect.getEffect()));

        //If the player does not already have all the effects
        if (!hasPlayerEffect){

            //Add each effect from the list
            for (MobEffectInstance theEffect : effects){
                player.addEffect(new MobEffectInstance(theEffect.getEffect(),
                        theEffect.getDuration(), theEffect.getAmplifier(), theEffect.isAmbient(),
                        theEffect.isVisible()));
            }
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> armorMaterial, Player player){
        for (ItemStack armorStack : player.getArmorSlots()){

            //We want to make sure we are actually wearing armor
            //because some equippable items are not actually an
            //ArmorItem, such as the carved pumpkin
            if (!(armorStack.getItem() instanceof ArmorItem)){
                return false;
            }
        }

        //Get the armor that the player is wearing in each slot
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        //Return whether all the armor is meteorite armor
        return boots.getMaterial() == armorMaterial
                && leggings.getMaterial() == armorMaterial
                && chestplate.getMaterial() == armorMaterial
                && helmet.getMaterial() == armorMaterial;
    }

    private boolean hasFullSuitOfArmorOn(Player player){

        //Check if the player is wearing a full suit of armor, regardless of the type
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }
}


