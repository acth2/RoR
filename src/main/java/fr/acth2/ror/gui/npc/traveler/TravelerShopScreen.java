package fr.acth2.ror.gui.npc.traveler;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class TravelerShopScreen  extends Screen {

    private final PlayerEntity player;

    public TravelerShopScreen(PlayerEntity player) {
        super(new StringTextComponent("Shop"));
        CoinsManager.loadCoins();
        this.player = player;
    }

    @Override
    protected void init() {
        int buttonWidth = 150;
        int buttonHeight = 20;
        int yStart = this.height / 4;

        addButton(new Button(this.width / 2 - buttonWidth / 2, yStart, buttonWidth, buttonHeight,
                new StringTextComponent("Buy Sword - 100 Coins"), button -> purchaseItem(100)));

        addButton(new Button(this.width / 2 - buttonWidth / 2, yStart + 30, buttonWidth, buttonHeight,
                new StringTextComponent("Buy Armor - 250 Coins"), button -> purchaseItem(250)));

        addButton(new Button(this.width / 2 - buttonWidth / 2, yStart + 60, buttonWidth, buttonHeight,
                new StringTextComponent("Buy Potion - 50 Coins"), button -> purchaseItem(50)));

        addButton(new Button(this.width / 2 - 50, this.height - 40, 100, 20,
                new StringTextComponent("Close Shop"), button -> onClose()));
    }

    private void purchaseItem(int cost) {
        int currentCoins = CoinsManager.getCoins();

        if (currentCoins >= cost) {
            CoinsManager.removeCoins(cost);
            player.sendMessage(new StringTextComponent("Purchased item for " + cost + " coins!"), player.getUUID());
        } else {
            player.sendMessage(new StringTextComponent("Not enough coins!"), player.getUUID());
            onClose();
        }
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(new TravelerSpeech(player, true));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        fill(matrixStack, 0, 0, this.width, this.height, 0xFF202020);

        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 15, 0xFFFFFF);
        drawCenteredString(matrixStack, this.font, "Coins: " + CoinsManager.getCoins(), this.width / 2, 35, 0xFFFFD700);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}