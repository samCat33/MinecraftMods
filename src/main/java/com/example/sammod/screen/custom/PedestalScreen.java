package com.example.sammod.screen.custom;

import com.example.sammod.SamMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

//This is responsible for rendering the background screen
public class PedestalScreen extends AbstractContainerScreen<PedestalMenu> {
    private static final ResourceLocation GUI_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "textures/gui/pedestal/pedestal_gui.png");

    public PedestalScreen(PedestalMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    //This will render the texture onto the screen
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

        //blit(texture, dest x, dest y, src u, src v, src w, src h, dest w, dest h)
        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0,0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        //We override the render method to render the tooltips
        //that show the names of items that we may
        //hover over in the slots on the pedestal's menu
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
