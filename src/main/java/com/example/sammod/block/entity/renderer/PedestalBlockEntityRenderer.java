package com.example.sammod.block.entity.renderer;

import com.example.sammod.block.entity.custom.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

//This is for rendering the item on the PedestalBlockEntity when something is put inside it
public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity>{
    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(PedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = pBlockEntity.INVENTORY.getStackInSlot(0);

        //The poseStack will have a new pose that displays the item
        pPoseStack.pushPose();

        //Move the rendered item so that it is centered an above the pedestal
        pPoseStack.translate(0.5f, 1.15f, 0.5f);


        //Scale the item to be half as large
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        //Rotate the item being rendered around the y-axis in + direction
        //according to the PedestalBlockEntity.getRenderingRotation() method,
        //which updates each tick
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRenderingRotation()));

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource,  pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    //This will copy the world's light level at the position of the renderer
    //onto the item being rendered
    private int getLightLevel(Level level, BlockPos pos){
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
