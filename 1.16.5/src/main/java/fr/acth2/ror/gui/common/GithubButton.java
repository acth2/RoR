package fr.acth2.ror.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

import java.net.URI;

public class GithubButton extends Button {

    private static final ResourceLocation DISCORD_ICON = new ResourceLocation("ror", "textures/gui/github.png");
    private static final String GITHUB_URL = "https://github.com/acth2";

    public GithubButton(int x, int y, int width, int height) {
        super(x, y, width, height, new StringTextComponent(""), button -> {
            try {
                Util.getPlatform().openUri(new URI(GITHUB_URL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bind(DISCORD_ICON);
        blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
    }
}