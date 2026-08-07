package com.example.sammod.entity.client;

import com.example.sammod.SamMod;
import com.example.sammod.entity.custom.TomahawkProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TomahawkProjectileRenderer extends EntityRenderer<TomahawkProjectileEntity> {
    private TomahawkProjectileModel model;

    public TomahawkProjectileRenderer(EntityRendererProvider.Context context){
        super(context);
        this.model= new TomahawkProjectileModel(context.bakeLayer(TomahawkProjectileModel.LAYER_LOCATION));
    }


    //This is what actually changes the visuals of the object when it is thrown
    //and when it hits the ground
    @Override
    public void render(TomahawkProjectileEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight){
        poseStack.pushPose();

        //If the axe is flying
        if(!entity.isGrounded()){

            //Set the yaw based on the direction the player is facing
            //We need to lerp between the yaw at the beginning of the tick (yRotO)
            //and the yaw at the end of the tick (getYRot) particularly when
            //the tomahawk leaves the player's hand. This is because the
            //player's refresh rate is usually much faster than the tick speed,
            //so we need the axe to not appear to be jittering when it leaves the player's
            //hand due to a tick speed that is slower than the refresh rate,
            //so we smoothen the transition using lerp
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));

            //Set the pitch
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRenderingRotation() * 5f));
            poseStack.translate(0, -1.0f, 0);
        }

        else{
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.groundedOffset.y));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.groundedOffset.x));
            poseStack.translate(0, -1.0f, 0);
        }

        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);

        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

    }

    //This is where the png for the tomahawk is used
    @Override
    public ResourceLocation getTextureLocation(TomahawkProjectileEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "textures/entity/tomahawk/tomahawk.png");
    }
}
