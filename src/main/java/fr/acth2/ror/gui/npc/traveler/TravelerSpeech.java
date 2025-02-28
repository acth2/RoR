package fr.acth2.ror.gui.npc.traveler;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.gui.npc.common.DialogueEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;

public class TravelerSpeech extends Screen {

    private final PlayerEntity player;
    private DialogueEntry currentDialogue;
    public TravelerSpeech(PlayerEntity player) {
        super(new StringTextComponent("Ruins of Realms"));
        this.player = player;
        CoinsManager.loadCoins();
        defineDialogs();
    }

    private void defineDialogs() {
        DialogueEntry response2 = new DialogueEntry(
                "Glad to hear that!",
                Arrays.asList("Goodbye"),
                Arrays.asList()
        );

        DialogueEntry response1 = new DialogueEntry(
                "That's unfortunate...",
                Arrays.asList("Goodbye"),
                Arrays.asList()
        );

        currentDialogue = new DialogueEntry(
                "Hello, traveler. How are you?",
                Arrays.asList("I'm good.", "Not great..."),
                Arrays.asList(response2, response1)
        );
    }

    @Override
    protected void init() {
        this.buttons.clear();
        int yStart = this.height - (this.height / 3) + 30;
        int buttonHeight = 20;
        int buttonSpacing = 8;

        for (int i = 0; i < currentDialogue.getPlayerResponses().size(); i++) {
            int yPos = yStart + (i * (buttonHeight + buttonSpacing));
            int index = i;

            this.addButton(new Button(this.width / 2 - 75, yPos, 150, buttonHeight,
                    new StringTextComponent(currentDialogue.getPlayerResponses().get(i)),
                    button -> selectResponse(index)
            ));
        }
    }

    private void selectResponse(int index) {
        DialogueEntry next = currentDialogue.getNextEntry(index);
        if (next != null) {
            currentDialogue = next;
            this.init();
        } else {
            this.onClose();
        }
    }

    public static int getCurrentPlayerCoins() {
        return CoinsManager.getCoins();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int dialogueHeight = screenHeight / 3;

        fill(matrixStack, 0, screenHeight - dialogueHeight, screenWidth, screenHeight, 0xAA000000);
        drawCenteredString(matrixStack, this.font, currentDialogue.getNpcText(), this.width / 2, screenHeight - dialogueHeight + 10, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
