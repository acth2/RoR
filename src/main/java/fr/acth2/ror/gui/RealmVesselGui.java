package fr.acth2.ror.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.realmvessel.DimensionSyncPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RealmVesselGui extends Screen {

    private static PlayerEntity player;
    private ItemStack realmVesselItem;
    private float zoomLevel = 1.0f;
    private final float minZoom = 0.5f;
    private final float maxZoom = 3.0f;
    private final float zoomStep = 0.1f;
    private int panX = 0;
    private int panY = 0;
    private boolean isDragging = false;
    private int lastMouseX;
    private int lastMouseY;

    private static final ResourceLocation OVERWORLD = new ResourceLocation("minecraft", "overworld");
    private static final ResourceLocation SKYRIA = new ResourceLocation("ror", "skyria");

    private static final ResourceLocation OVERWORLD_IMAGE = new ResourceLocation("ror", "textures/gui/overworld.png");
    private static final ResourceLocation SKYRIA_IMAGE = new ResourceLocation("ror", "textures/gui/skyria.png");
    private static final ResourceLocation SKYRIA_UNLOCKED_IMAGE = new ResourceLocation("ror", "textures/gui/skyria2.png");

    private static final Map<UUID, Boolean> playerSkyriaAccess = new HashMap<>();

    private int smallImageSize = 80;

    private String errorMessage = "";
    private long errorDisplayTime = 0;
    private static final long ERROR_DISPLAY_DURATION = 2000;
    private float currentImageFadeAlpha = 1.0f;
    private Hand hand;

    public RealmVesselGui(PlayerEntity player, ItemStack realmVesselItem, Hand hand) {
        super(new StringTextComponent("Realm Vessel"));
        RealmVesselGui.player = player;
        this.realmVesselItem = realmVesselItem;
        this.hand = hand;
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

        drawString(matrixStack, this.font, "Mouse wheel to zoom | Click and drag to move | Click to select dimension", 10, 25, 0xCCCCCC);

        if (player != null) {
            String debugInfo = String.format("Y: %.1f | Skyria Access: %s", player.getY(), hasSkyriaAccess(player));
            drawString(matrixStack, this.font, debugInfo, 10, 40, 0xFFFF00);
        }

        if (!errorMessage.isEmpty() && System.currentTimeMillis() - errorDisplayTime < ERROR_DISPLAY_DURATION) {
            int errorY = scaledHeight - 30;
            drawString(matrixStack, this.font, errorMessage, 10, errorY, 0xFF0000);
        } else if (!errorMessage.isEmpty()) {
            errorMessage = "";
        }

        int centerX = imageX + (imageWidth / 2);
        int centerY = imageY + (imageHeight / 2);

        float imageFadeAlpha;
        if (zoomLevel <= 1.0f) {
            imageFadeAlpha = 0.0f;
        } else if (zoomLevel >= maxZoom) {
            imageFadeAlpha = 1.0f;
        } else {
            imageFadeAlpha = (zoomLevel - 1.0f) / (maxZoom - 1.0f);
        }

        currentImageFadeAlpha = imageFadeAlpha;

        boolean overworldHovered = false;
        boolean skyriaHovered = false;

        if (imageFadeAlpha > 0.01f) {
            smallImageSize = 80;

            int overworldX = centerX - (smallImageSize / 2);
            int overworldY = centerY - (smallImageSize / 2);
            int skyriaX = centerX - (smallImageSize / 2);
            int skyriaY = imageY + 100;

            overworldHovered = isMouseOverImage(mouseX, mouseY, overworldX, overworldY, smallImageSize, smallImageSize);
            skyriaHovered = isMouseOverImage(mouseX, mouseY, skyriaX, skyriaY, smallImageSize, smallImageSize);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            RenderSystem.color4f(1.0f, 1.0f, 1.0f, imageFadeAlpha);

            this.minecraft.getTextureManager().bind(OVERWORLD_IMAGE);
            blit(matrixStack, overworldX, overworldY, smallImageSize, smallImageSize, 0, 0, 256, 256, 256, 256);

            boolean hasSkyriaAccess = hasSkyriaAccess(player);
            ResourceLocation skyriaTexture = hasSkyriaAccess ? SKYRIA_UNLOCKED_IMAGE : SKYRIA_IMAGE;
            this.minecraft.getTextureManager().bind(skyriaTexture);
            blit(matrixStack, skyriaX, skyriaY, smallImageSize, smallImageSize, 0, 0, 256, 256, 256, 256);

            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
        }

        if (overworldHovered && currentImageFadeAlpha > 0.7f) {
            drawImageTooltip(matrixStack, mouseX, mouseY, "Overworld", "click to select", OVERWORLD, true);
        }
        if (skyriaHovered && currentImageFadeAlpha > 0.7f) {
            boolean hasSkyriaAccess = hasSkyriaAccess(player);
            String description = hasSkyriaAccess ? "click to select" : "To select Skyria, you will need to advent high in the sky";
            drawImageTooltip(matrixStack, mouseX, mouseY, "Skyria", description, SKYRIA, hasSkyriaAccess);
        }

        float separatorFadeAlpha;
        if (zoomLevel <= minZoom) {
            separatorFadeAlpha = 0.0f;
        } else if (zoomLevel >= 1.0f) {
            separatorFadeAlpha = 1.0f;
        } else {
            separatorFadeAlpha = (zoomLevel - minZoom) / (1.0f - minZoom);
        }

        if (separatorFadeAlpha > 0.01f) {
            int textX = imageX + (imageWidth / 2);
            int textY = imageY + (imageHeight / 4);
            int separatorAlpha = (int)(separatorFadeAlpha * 255) << 24;
            int separatorColor = 0xFFFFFF | separatorAlpha;
            drawCenteredString(matrixStack, this.font, "", textX, textY, separatorColor);
        }

        drawCenteredString(matrixStack, this.font, this.title.getString(), centerX, imageY + 15, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private String obfuscateText(String text, float fadeAlpha) {
        float readability = fadeAlpha;

        if (readability > 0.7f) {
            return text;
        }

        if (readability < 0.1f) {
            StringBuilder fullObfuscated = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                fullObfuscated.append('#');
            }
            return fullObfuscated.toString();
        }

        float obfuscationProgress = (0.7f - readability) / 0.6f;
        int charsToObfuscate = (int)(text.length() * obfuscationProgress);

        StringBuilder obfuscated = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char originalChar = text.charAt(i);

            if (Character.isLetterOrDigit(originalChar)) {
                if (i >= text.length() - charsToObfuscate) {
                    obfuscated.append('#');
                } else {
                    obfuscated.append(originalChar);
                }
            } else {
                obfuscated.append(originalChar);
            }
        }

        return obfuscated.toString();
    }

    private void showErrorMessage(String message) {
        errorMessage = message;
        errorDisplayTime = System.currentTimeMillis();
    }

    private boolean isMouseOverImage(int mouseX, int mouseY, int imageX, int imageY, int width, int height) {
        return mouseX >= imageX && mouseX <= imageX + width &&
                mouseY >= imageY && mouseY <= imageY + height;
    }

    private void drawImageTooltip(MatrixStack matrixStack, int mouseX, int mouseY, String realmName, String realmDescription, ResourceLocation dimension, boolean hasAccess) {
        java.util.List<ITextComponent> tooltip = new java.util.ArrayList<>();

        String readableDescription;

        if (hasAccess) {
            readableDescription = obfuscateText("Click to select " + realmName + " for the Realm Vessel", currentImageFadeAlpha);
            tooltip.add(new StringTextComponent(readableDescription).withStyle(TextFormatting.GRAY));
        } else {
            readableDescription = obfuscateText("To select " + realmName + ", " + realmDescription, currentImageFadeAlpha);
            tooltip.add(new StringTextComponent(readableDescription).withStyle(TextFormatting.GRAY));
        }

        if (realmVesselItem != null && realmVesselItem.hasTag()) {
            CompoundNBT nbt = realmVesselItem.getTag();
            if (nbt.contains("SelectedDimension")) {
                String selectedDim = nbt.getString("SelectedDimension");
                if (selectedDim.equals(dimension.toString())) {
                    tooltip.add(new StringTextComponent(""));
                    String currentRealmText = obfuscateText("Currently selected for this Realm Vessel", currentImageFadeAlpha);
                    tooltip.add(new StringTextComponent(currentRealmText).withStyle(TextFormatting.AQUA));
                }
            }
        }

        renderComponentTooltip(matrixStack, tooltip, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
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

            int centerX = imageX + (imageWidth / 2);
            int centerY = imageY + (imageHeight / 2);

            int overworldX = centerX - (smallImageSize / 2);
            int overworldY = centerY - (smallImageSize / 2);
            int skyriaX = centerX - (smallImageSize / 2);
            int skyriaY = imageY + 100;

            boolean overworldClicked = isMouseOverImage((int)mouseX, (int)mouseY, overworldX, overworldY, smallImageSize, smallImageSize);
            boolean skyriaClicked = isMouseOverImage((int)mouseX, (int)mouseY, skyriaX, skyriaY, smallImageSize, smallImageSize);

            float imageFadeAlpha;
            if (zoomLevel <= 1.0f) {
                imageFadeAlpha = 0.0f;
            } else if (zoomLevel >= maxZoom) {
                imageFadeAlpha = 1.0f;
            } else {
                imageFadeAlpha = (zoomLevel - 1.0f) / (maxZoom - 1.0f);
            }

            if (imageFadeAlpha > 0.1f) {
                if (overworldClicked) {
                    if (player != null) {
                        setSelectedDimension(OVERWORLD);
                        player.sendMessage(new StringTextComponent("Selected Overworld for the Realm Vessel"), player.getUUID());
                    }
                    this.minecraft.setScreen(null);
                    return true;
                }

                if (skyriaClicked) {
                    boolean canAccess = hasSkyriaAccess(player) || checkSkyriaConditions(player);
                    if (canAccess) {
                        if (!hasSkyriaAccess(player)) {
                            grantSkyriaAccess(player);
                        }
                        setSelectedDimension(SKYRIA);
                        player.sendMessage(new StringTextComponent("Selected Skyria for the Realm Vessel"), player.getUUID());
                        this.minecraft.setScreen(null);
                        return true;
                    } else {
                        showErrorMessage("You need to be higher to access Skyria!");
                        return true;
                    }
                }
            }

            this.isDragging = true;
            this.lastMouseX = (int)mouseX;
            this.lastMouseY = (int)mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void setSelectedDimension(ResourceLocation dimension) {
        if (realmVesselItem != null && player != null && hand != null) {
            String dimensionName = getDimensionName(dimension.toString());
            TextFormatting color = dimensionName.equals("Skyria") ? TextFormatting.AQUA : TextFormatting.GREEN;
            realmVesselItem.setHoverName(new StringTextComponent("Realm Vessel: " + dimensionName).withStyle(color));

            CompoundNBT nbt = realmVesselItem.getOrCreateTag();
            nbt.putString("SelectedDimension", dimension.toString());
            realmVesselItem.setTag(nbt);

            System.out.println("Client updated Realm Vessel to: " + dimensionName);
            ModNetworkHandler.INSTANCE.sendToServer(new DimensionSyncPacket(dimension.toString(), hand));

            this.minecraft.setScreen(null);
        }
    }

    private String getDimensionName(String dimensionId) {
        if (dimensionId.equals("minecraft:overworld")) {
            return "Overworld";
        } else if (dimensionId.equals("ror:skyria")) {
            return "Skyria";
        } else {
            return "Unknown";
        }
    }

    private boolean hasSkyriaAccess(PlayerEntity player) {
        if (player == null) return false;
        return playerSkyriaAccess.getOrDefault(player.getUUID(), false);
    }

    private boolean checkSkyriaConditions(PlayerEntity player) {
        return player != null && player.getY() >= 100.0;
    }

    private void grantSkyriaAccess(PlayerEntity player) {
        if (player != null) {
            playerSkyriaAccess.put(player.getUUID(), true);
        }
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