package com.example.sammod.block.blocks;

import com.example.sammod.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SusieTNT extends Block {

    private static final Map<BlockPos, Integer> fuseCounts = new HashMap<>();

    public SusieTNT(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                                    Player player, InteractionHand hand,
                                                    BlockHitResult hit) {


        //If we use the client side, any changes to the world will be out of
        //sync since the server is responsible for storing information about
        //the world
        if (!level.isClientSide && stack.is(ModItems.CATNIP.get())) {
            fuseCounts.put(pos, 60);
            level.scheduleTick(pos, this, 1);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    ExplosionDamageCalculator customCalculator = new ExplosionDamageCalculator() {
        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            return 200F;
        }
    };

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos,
                     RandomSource random) {

        int fuse = fuseCounts.getOrDefault(pos, 0);

        if (fuse <= 0) {
            fuseCounts.remove(pos);
            level.explode(
                    null,                                                    // source entity
                    level.damageSources().explosion(null, null),            // damage source
                    customCalculator,                                                    // damage calculator (null = default)
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    25.0F,                                                   // power
                    false,                                                   // create fire
                    Level.ExplosionInteraction.TNT,
                    ParticleTypes.EXPLOSION,                                 // small particles
                    ParticleTypes.EXPLOSION_EMITTER,                         // large particles
                    BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ENDER_DRAGON_AMBIENT) // your custom sound
            );

            level.removeBlock(pos, false);
        } else {
            level.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    pos.getX() + 0.5, pos.getY() + 1.0,
                    pos.getZ() + 0.5, 30, 0.1,
                    0.1, 0.1, 0.5);
            level.sendParticles(ParticleTypes.SMALL_FLAME, pos.getX() + 0.5,
                    pos.getY() + 1.0, pos.getZ() + 0.5,
                    100, 0.05, 0.05, 0.05, 1);

            if (fuse % 5 == 0) {
                level.playSound(null, pos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 1.0F, (float) (Math.random() + 0.3));
            }

            fuseCounts.put(pos, fuse - 1);
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> TooltipComponents, TooltipFlag TooltipFlag){

        if (Screen.hasShiftDown()){
            TooltipComponents.add(Component.translatable("tooltip.sammod.susie_tnt.tooltip_shift"));
        }
        else{
            TooltipComponents.add(Component.translatable("tooltip.sammod.susie_tnt.tooltip_no_shift"));
        }

        super.appendHoverText(stack, context, TooltipComponents, TooltipFlag);
    }

}
