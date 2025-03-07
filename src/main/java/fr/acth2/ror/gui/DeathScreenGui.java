package fr.acth2.ror.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.RevivePlayerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeathScreenGui extends Screen {

    public DeathScreenGui() {
        super(new StringTextComponent("You Died"));
    }

    @Override
    protected void init() {
        // Add a "Revive" button
        this.addButton(new Button(
                this.width / 2 - 50, this.height / 2 - 10, 100, 20,
                new StringTextComponent("Revive"),
                button -> {
                    ModNetworkHandler.INSTANCE.sendToServer(new RevivePlayerPacket());
                    this.minecraft.setScreen(null);
                }
        ));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, "You Died", this.width / 2, this.height / 2 - 30, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}