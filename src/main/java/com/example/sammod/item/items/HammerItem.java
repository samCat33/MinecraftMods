package com.example.sammod.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier pTier, Properties pProperties) {
        super(pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }

    //Mines a 3x3 area
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialPos, ServerPlayer player){
        List<BlockPos> positions = new ArrayList<>();

        //clip() runs a ray trace
        //ClipContext(start point, end point, block conditions, fluid conditions, entity)
        //Here we are doing a ray trace starting from the player's eyes, extending six blocks
        //outward in the direction the player is looking, and only checking full blocks and ignoring fluids
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));


        //Return the positions as empty if the traceResult does not find any blocks
        //matching the predefined conditions
        if (traceResult.getType() == HitResult.Type.MISS){
            return positions;
        }

        //If the traceResult is successful,
        //gather positions of blocks for mining in a 3x3 area according
        //to which direction the traceResult determines we are facing

        //Player is looking down or up (Mine a 3x3 grid in the xz plane)
        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP){
            for (int x = -range; x <= range; x++){
                for (int z = -range; z <= range; ++z){
                    positions.add(new BlockPos(initialPos.getX() + x, initialPos.getY(), initialPos.getZ() + z));
                }
            }
        }

        //Player is looking north or south (Mine a 3x3 grid in the xy plane)
        if (traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH){
            for (int x = -range; x <= range; x++){
                for (int y = -range; y <= range; ++y){
                    positions.add(new BlockPos(initialPos.getX() + x, initialPos.getY() + y, initialPos.getZ()));
                }
            }
        }

        //Player is looking east or west (Mine a 3x3 grid in the yz plane)
        if (traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST){
            for (int z = -range; z <= range; z++){
                for (int y = -range; y <= range; ++y){
                    positions.add(new BlockPos(initialPos.getX(), initialPos.getY() + y, initialPos.getZ() + z));
                }
            }
        }

        return positions;
    }
}
