package fr.acth2.ror.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class RealmVesselGui extends Screen {

    private static PlayerEntity player;
    private float zoomLevel = 1.0f;
    private final float minZoom = 0.5f;
    private final float maxZoom = 3.0f;
    private final float zoomStep = 0.1f;
    private int panX = 0;
    private int panY = 0;
    private boolean isDragging = false;
    private int lastMouseX;
    private int lastMouseY;

    public RealmVesselGui(PlayerEntity player) {
        super(new StringTextComponent("Realm Vessel"));
        RealmVesselGui.player = player;
    }

    @Override
    protected void init() {
        zoomLevel = 1.0f;
        panX = 0;
        panY = 0;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        int scaledWidth = this.width;
        int scaledHeight = this.height;

        int originalWidth = 636;
        int originalHeight = 602;

        float baseScale = 0.7f;
        float effectiveScale = baseScale * zoomLevel;
        int maxWidth = (int)(scaledWidth * effectiveScale);
        int maxHeight = (int)(scaledHeight * effectiveScale);

        float aspectRatio = (float)originalWidth / originalHeight;
        int imageWidth, imageHeight;

        if (maxWidth / aspectRatio <= maxHeight) {
            imageWidth = maxWidth;
            imageHeight = (int)(maxWidth / aspectRatio);
        } else {
            imageHeight = maxHeight;
            imageWidth = (int)(maxHeight * aspectRatio);
        }

        int imageX = (scaledWidth - imageWidth) / 2 + panX;
        int imageY = (scaledHeight - imageHeight) / 2 + panY;

        this.minecraft.getTextureManager().bind(new ResourceLocation("ror", "textures/gui/rvgui.png"));
        blit(matrixStack, imageX, imageY, imageWidth, imageHeight, 0, 0, originalWidth, originalHeight, originalWidth, originalHeight);

        String zoomText = String.format("Zoom: %.1fx", zoomLevel);
        drawString(matrixStack, this.font, zoomText, 10, 10, 0xFFFFFF);
        drawString(matrixStack, this.font, "Mouse wheel to zoom | Click and drag to move", 10, 25, 0xCCCCCC);

        int textX = imageX + (imageWidth / 2);
        int textY = imageY + (imageHeight / 4);

        drawCenteredString(matrixStack, this.font, "-------------------", textX, textY, 0xFFFFFF);
        drawCenteredString(matrixStack, this.font, this.title.getString(), textX, imageY + 15, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            zoomLevel = Math.min(zoomLevel + zoomStep, maxZoom);
        } else if (delta < 0) {
            zoomLevel = Math.max(zoomLevel - zoomStep, minZoom);
        }

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDragging = true;
            this.lastMouseX = (int)mouseX;
            this.lastMouseY = (int)mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.isDragging && button == 0) {
            int deltaX = (int)mouseX - this.lastMouseX;
            int deltaY = (int)mouseY - this.lastMouseY;

            this.panX += deltaX;
            this.panY += deltaY;

            this.lastMouseX = (int)mouseX;
            this.lastMouseY = (int)mouseY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}