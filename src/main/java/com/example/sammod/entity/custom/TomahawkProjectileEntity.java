package com.example.sammod.entity.custom;

import com.example.sammod.entity.ModEntities;
import com.example.sammod.item.ModItems;
import com.example.sammod.item.items.TomahawkItem;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.fml.common.Mod;

public class TomahawkProjectileEntity extends AbstractArrow {
    private float rotation;
    public Vec2 groundedOffset;

    public TomahawkProjectileEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //This constructor sets the owner to the shooter parameter
    public TomahawkProjectileEntity(LivingEntity shooter, Level level) {
        super(ModEntities.TOMAHAWK.get(), shooter, level, new ItemStack(ModItems.TOMAHAWK_ITEM.get()), null);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.TOMAHAWK_ITEM.get());
    }

    //This will only rotate the visuals, and not the entity itself
    //which is okay for a tomahawk.
    public float getRenderingRotation() {
        rotation += 0.5f;

        if (rotation >= 360) {
            rotation = 0;
        }

        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    //When the tomahawk hits an entity
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        //Damage the entity by three hearts (6 HP)
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 6);

        //Broadcast the event to the server
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    //When the tomahawk hits a block
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        //Get the player who throw the tomahawk
        Entity owner = this.getOwner();
        float yRot = 0.0f;

        //Get the y-rotation of the player
        if (owner instanceof Player){
            yRot = owner.getYRot();
        }

        if (result.getDirection() == Direction.NORTH ||
        result.getDirection() == Direction.SOUTH ||
        result.getDirection() == Direction.EAST ||
        result.getDirection() == Direction.WEST){
            groundedOffset = new Vec2(245, -yRot);
        }

        if (result.getDirection() == Direction.DOWN) {
            groundedOffset = new Vec2(245, -yRot);
        }


        if (result.getDirection() == Direction.UP) {
            groundedOffset = new Vec2(245, -yRot);
        }
    }
}
