package com.example.sammod.screen.custom;

import com.example.sammod.SamMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GrowthChamberScreen extends AbstractContainerScreen<GrowthChamberMenu> {
    private static final String GUI_PATH = "textures/gui/growth_chamber/growth_chamber_gui.png";
    private static final String ARROW_PATH = "textures/gui/arrow_progress.png";

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, GUI_PATH);

    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, ARROW_PATH);

    public GrowthChamberScreen(GrowthChamberMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        //Proper x and y coordinates
        //width and height are the width and height of the PNG itself
        //imageWidth and imageHeight are the width and height as they appear in-game
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pGuiGraphics, x, y);

    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y){
        if (menu.isCrafting()){
            //blit(texture, dest x, dest y, src u, src v, src w, src h, dest w, dest h)
            guiGraphics.blit(ARROW_TEXTURE, x + 83, y + 35, 0, 0, menu.getScaledArrowProgress(),16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
