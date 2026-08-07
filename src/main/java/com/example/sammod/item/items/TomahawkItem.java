package com.example.sammod.item.items;

import com.example.sammod.entity.custom.TomahawkProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class TomahawkItem extends Item {

    public TomahawkItem(Properties pProperties) {
        super(pProperties);
    }

    //This method gets called when the player right-clicks.
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand){
        ItemStack itemStack = player.getItemInHand(usedHand);

        //nextFloat() returns between 0.0(inclusive) and 1.0(exclusive)
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.6F + 0.8F));

        if(!level.isClientSide) {
            TomahawkProjectileEntity tomahawkProjectile = new TomahawkProjectileEntity(player, level);
            //The last three parameters are z rotation, velocity, and an inaccuracy parameter
            tomahawkProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
            level.addFreshEntity(tomahawkProjectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        //This reduces the number of tomahawks the player has
        //if they are not in creative mode, because the player
        //has instabuild in creative mode
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
