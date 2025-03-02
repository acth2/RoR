package fr.acth2.ror.gui.npc.traveler;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TravelerSpeech extends Screen {

    private PlayerEntity player;
    private boolean showAfterShopDialogue = false;
    private String npcText;
    private String[] playerResponses;

    public TravelerSpeech(PlayerEntity player) {
        super(new StringTextComponent("Ruins of Realms"));
        this.player = player;
        this.npcText = "Hello, traveler. Searching items?";
        this.playerResponses = new String[]{"Open shop", "No"};
    }

    @Override
    protected void init() {
        this.buttons.clear();
        int dialogueHeight = this.height / 3;
        int yStart = this.height - dialogueHeight + 30;
        int buttonHeight = 20;
        int buttonSpacing = 8;

        for (int i = 0; i < playerResponses.length; i++) {
            int yPos = yStart + (i * (buttonHeight + buttonSpacing));
            int index = i;
            this.addButton(new Button(this.width / 2 - 75, yPos, 150, buttonHeight,
                    new StringTextComponent(playerResponses[i]),
                    button -> selectResponse(index)
            ));
        }
    }

    private void selectResponse(int index) {
        if (showAfterShopDialogue) {
            this.onClose();
        } else {
            if (index == 0) {
                Minecraft.getInstance().setScreen(new TravelerShopScreen(player));
            } else {
                this.onClose();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int dialogueHeight = screenHeight / 3;

        fill(matrixStack, 0, screenHeight - dialogueHeight, screenWidth, screenHeight, 0xAA000000);
        drawCenteredString(matrixStack, this.font, npcText, this.width / 2, screenHeight - dialogueHeight + 10, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
