package fr.acth2.mod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.mod.gui.common.DiscordButton;
import fr.acth2.mod.gui.common.GithubButton;
import fr.acth2.mod.gui.common.LogoButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class MainMenuGui extends Screen {

    private final PlayerEntity player;
    private int currentTab = 0;
    private final String[] tabLabels = { "SKILLS", "DIARY", "OTHER" };
    private DiscordButton discordButton;
    private GithubButton githubButton;
    private LogoButton logoButton;

    public MainMenuGui(PlayerEntity player) {
        super(new StringTextComponent("Ruins of Realms"));
        this.player = player;
    }

    @Override
    protected void init() {
        this.buttons.clear();

        int tabButtonWidth = 120;
        int tabButtonHeight = 20;
        int tabSpacing = 10;
        int totalWidth = tabLabels.length * tabButtonWidth + (tabLabels.length - 1) * tabSpacing;
        int startX = (this.width - totalWidth) / 2;
        int y = 20;

        for (int i = 0; i < tabLabels.length; i++) {
            int x = startX + i * (tabButtonWidth + tabSpacing);
            final int tabIndex = i;
            this.addButton(new Button(x, y, tabButtonWidth, tabButtonHeight, new StringTextComponent(tabLabels[i]), button -> {
                currentTab = tabIndex;
                updateTabVisibility();
            }));
        }

        int iconSize = 16;
        int logoX = (this.width - 300) / 2;
        int logoY = (this.height - 128) / 2;
        discordButton = new DiscordButton(10, this.height - iconSize - 10, iconSize, iconSize);
        discordButton.visible = (currentTab == 2);

        githubButton = new GithubButton(10, this.height - iconSize - 30, iconSize, iconSize);
        githubButton.visible = (currentTab == 2);

        logoButton = new LogoButton(logoX, logoY, 300, 128);
        logoButton.visible = (currentTab == 2);

        this.addButton(discordButton);
        this.addButton(githubButton);
        this.addButton(logoButton);
    }

    private void updateTabVisibility() {
        if (discordButton != null) {
            discordButton.visible = (currentTab == 2);
        }

        if (githubButton != null) {
            githubButton.visible = (currentTab == 2);
        }

        if (logoButton != null) {
            logoButton.visible = (currentTab == 2);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 5, 0xFFFFFF);

        int renderX = 50;
        int renderY = this.height / 2 + 50;
        int scale = 30;
        float offsetX = (float)(renderX - mouseX);
        float offsetY = (float)(renderY - 75 - mouseY);
        InventoryScreen.renderEntityInInventory(renderX, renderY, scale, offsetX, offsetY, this.player);

        int contentY = 80;
        switch (currentTab) {
            case 0:
                drawCenteredString(matrixStack, this.font, "Content for SKILLS", this.width / 2, contentY, 0xFFFFFF);
                break;
            case 1:
                drawCenteredString(matrixStack, this.font, "Content for DIARY", this.width / 2, contentY, 0xFFFFFF);
                break;
            case 2:
                String credit = "Mod made by AcTh2";
                int textX = 30;
                int textY = this.height - 20;
                drawString(matrixStack, this.font, credit, textX, textY, 0xFFFFFF);
                break;
            default:
                break;
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
