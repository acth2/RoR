package fr.acth2.mod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.mod.gui.common.DiscordButton;
import fr.acth2.mod.gui.common.GithubButton;
import fr.acth2.mod.gui.common.LogoButton;
import fr.acth2.mod.gui.diary.DiaryEntry;
import fr.acth2.mod.gui.diary.DiaryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenuGui extends Screen {

    private final PlayerEntity player;
    private int currentTab = 0;
    private final String[] tabLabels = { "SKILLS", "DIARY", "OTHER" };

    private DiscordButton discordButton;
    private GithubButton githubButton;
    private LogoButton logoButton;

    private final List<Button> diaryEntryButtons = new ArrayList<>();
    private DiaryEntry currentDiaryEntry = null;
    private int diaryPage = 0;
    private final int entriesPerPage = 5;
    private Button prevPageButton;
    private Button nextPageButton;

    public MainMenuGui(PlayerEntity player) {
        super(new StringTextComponent("Ruins of Realms"));
        this.player = player;
        DiaryManager.loadDiary();
    }

    @Override
    protected void init() {
        this.buttons.clear();
        diaryEntryButtons.clear();
        diaryPage = 0;

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
        githubButton = new GithubButton(10, this.height - iconSize - 30, iconSize, iconSize);
        logoButton = new LogoButton(logoX, logoY, 300, 128);

        discordButton.visible = (currentTab == 2);
        githubButton.visible = (currentTab == 2);
        logoButton.visible = (currentTab == 2);

        this.addButton(discordButton);
        this.addButton(githubButton);
        this.addButton(logoButton);

        if (currentTab == 1) {
            initDiaryEntries();
        }
    }

    private void initDiaryEntries() {
        removeDiaryWidgets();
        diaryEntryButtons.clear();

        List<DiaryEntry> entries = DiaryManager.getEntries();
        int totalPages = (int) Math.ceil((double) entries.size() / entriesPerPage);
        if (diaryPage >= totalPages) {
            diaryPage = totalPages - 1;
        }
        if (diaryPage < 0) {
            diaryPage = 0;
        }
        int startIndex = diaryPage * entriesPerPage;
        int endIndex = Math.min(startIndex + entriesPerPage, entries.size());

        int startX = 20;
        int startY = 45;
        int buttonWidth = 140;
        int buttonHeight = 20;
        int spacing = 5;
        int index = 0;
        for (int i = startIndex; i < endIndex; i++) {
            DiaryEntry entry = entries.get(i);
            int x = startX;
            int y = startY + index * (buttonHeight + spacing);
            Button diaryButton = new Button(x, y, buttonWidth, buttonHeight, new StringTextComponent(entry.getMonsterName()), btn -> {
                currentDiaryEntry = entry;
            });
            this.addButton(diaryButton);
            diaryEntryButtons.add(diaryButton);
            index++;
        }
        if (totalPages > 1) {
            int navY = startY + entriesPerPage * (buttonHeight + spacing) + 5;
            prevPageButton = new Button(startX, navY, 40, 20, new StringTextComponent("<"), btn -> {
                diaryPage--;
                initDiaryEntries();
            });
            nextPageButton = new Button(startX + buttonWidth - 40, navY, 40, 20, new StringTextComponent(">"), btn -> {
                diaryPage++;
                initDiaryEntries();
            });
            prevPageButton.visible = diaryPage > 0;
            nextPageButton.visible = diaryPage < totalPages - 1;
            this.addButton(prevPageButton);
            this.addButton(nextPageButton);
        }
    }

    private void removeDiaryWidgets() {
        Iterator<net.minecraft.client.gui.widget.Widget> iterator = this.buttons.iterator();
        while (iterator.hasNext()) {
            net.minecraft.client.gui.widget.Widget widget = iterator.next();
            if (diaryEntryButtons.contains(widget) || widget == prevPageButton || widget == nextPageButton) {
                iterator.remove();
            }
        }
    }

    private void updateTabVisibility() {
        discordButton.visible = (currentTab == 2);
        githubButton.visible = (currentTab == 2);
        logoButton.visible = (currentTab == 2);

        removeDiaryWidgets();
        diaryEntryButtons.clear();

        if (currentTab == 1) {
            initDiaryEntries();
        } else {
            currentDiaryEntry = null;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 5, 0xFFFFFF);

        if (currentTab != 1) {
            int renderX = 50;
            int renderY = this.height / 2 + 50;
            int scale = 30;
            float offsetX = (float) (renderX - mouseX);
            float offsetY = (float) (renderY - 75 - mouseY);
            InventoryScreen.renderEntityInInventory(renderX, renderY, scale, offsetX, offsetY, this.player);
        }

        int contentY = 80;
        switch (currentTab) {
            case 0:
                drawCenteredString(matrixStack, this.font, "Content for SKILLS", this.width / 2, contentY, 0xFFFFFF);
                break;
            case 1:
                drawCenteredString(matrixStack, this.font, "Diary of Modded Kills", this.width / 2, contentY - 20, 0xFFFFFF);
                if (currentDiaryEntry != null) {
                    int popupWidth = 200;
                    int popupHeight = 150;
                    int popupX = this.width - popupWidth - 20;
                    int popupY = 50;
                    fill(matrixStack, popupX, popupY, popupX + popupWidth, popupY + popupHeight, 0xAA000000);
                    drawString(matrixStack, this.font, currentDiaryEntry.getMonsterName(), popupX + 10, popupY + 10, 0xFFFFFF);

                    List<IReorderingProcessor> lines = this.font.split(new StringTextComponent(currentDiaryEntry.getDescription()), popupWidth - 20);
                    int lineY = popupY + 30;
                    for (IReorderingProcessor line : lines) {
                        this.font.draw(matrixStack, line, popupX + 10, lineY, 0xCCCCCC);
                        lineY += this.font.lineHeight;
                    }

                    if (currentDiaryEntry.getMonsterType() != null) {
                        Entity renderEntity = currentDiaryEntry.getMonsterType().create(Minecraft.getInstance().level);
                        if (renderEntity != null && renderEntity instanceof LivingEntity) {
                            int modelX = popupX + popupWidth / 2;
                            int modelY = popupY + popupHeight - 20;
                            int modelScale = 30;
                            float modelOffsetX = 0;
                            float modelOffsetY = 0;
                            InventoryScreen.renderEntityInInventory(modelX, modelY, modelScale, modelOffsetX, modelOffsetY, (LivingEntity) renderEntity);
                        }
                    }
                }
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
