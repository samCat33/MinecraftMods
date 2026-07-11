package com.example.sammod.entity.client;

import com.example.sammod.SamMod;
import com.example.sammod.entity.custom.TriceratopsEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TriceratopsRenderer extends MobRenderer<TriceratopsEntity, TriceratopsModel<TriceratopsEntity>> {

    public TriceratopsRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TriceratopsModel<>(pContext.bakeLayer(TriceratopsModel.LAYER_LOCATION)), 0.6f);
    }

    //This is where the textures are derived from
    @Override
    public ResourceLocation getTextureLocation(TriceratopsEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "textures/entity/triceratops/triceratops_gray.png");
    }

    //This is where we define the size of the entity
    @Override
    public void render(TriceratopsEntity entity, float entityView, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight){

        //Scale the dino according to whether or not it is a baby
        if (entity.isBaby()){
            poseStack.scale(0.5f,0.5f,0.5f);
        }
        else{
            poseStack.scale(1f, 1f, 1f);
        }

        super.render(entity, entityView, partialTicks, poseStack, buffer, packedLight);

    }
}
