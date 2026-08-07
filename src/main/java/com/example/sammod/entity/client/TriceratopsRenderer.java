package com.example.sammod.entity.client;

import com.example.sammod.SamMod;
import com.example.sammod.entity.TriceratopsVariant;
import com.example.sammod.entity.custom.TriceratopsEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TriceratopsRenderer extends MobRenderer<TriceratopsEntity, TriceratopsModel<TriceratopsEntity>> {

    //This creates a map that maps trike variants to resource locations
    //When a variant is selected, the variant corresponds to a texture file
    private static final Map<TriceratopsVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TriceratopsVariant.class),
                    map -> {
                        map.put(TriceratopsVariant.GRAY,
                                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "textures/entity/triceratops/triceratops_gray.png"));
                        map.put(TriceratopsVariant.GREEN,
                                ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "textures/entity/triceratops/triceratops_green.png"));
                    });
/*

*/
    public TriceratopsRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TriceratopsModel<>(pContext.bakeLayer(TriceratopsModel.LAYER_LOCATION)), 0.6f);
    }

    //This is where the textures are derived from, depending on which variant is selected
    @Override
    public ResourceLocation getTextureLocation(TriceratopsEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
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
