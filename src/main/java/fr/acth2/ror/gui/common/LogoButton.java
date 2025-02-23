package fr.acth2.ror.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class LogoButton extends Button {

    private static final ResourceLocation LOGO_ICON = new ResourceLocation("ror", "textures/gui/logo.png");
    public LogoButton(int x, int y, int width, int height) {
        super(x, y, width, height, new StringTextComponent(""), button -> {
            // make a sound (need to learn fl studio ahhh)
        });
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bind(LOGO_ICON);
        blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
    }
}