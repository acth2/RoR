package fr.acth2.ror.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.gui.common.DiscordButton;
import fr.acth2.ror.gui.common.GithubButton;
import fr.acth2.ror.gui.common.LogoButton;
import fr.acth2.ror.gui.diary.DiaryEntry;
import fr.acth2.ror.gui.diary.DiaryManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.RequestCoinSyncPacket;
import fr.acth2.ror.network.skills.RequestLevelUpPacket;
import fr.acth2.ror.network.skills.RequestSyncPlayerStatsPacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.WorldParticleHandler;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class MainMenuGui extends Screen {

    private static PlayerEntity player;
    private int currentTab = 0;
    private final String[] tabLabels = { "SKILLS", "DIARY", "OTHER" };
    private PlayerStats playerStats;

    private DiscordButton discordButton;
    private GithubButton githubButton;
    private LogoButton logoButton;

    private final List<Button> diaryEntryButtons = new ArrayList<>();
    private DiaryEntry currentDiaryEntry = null;
    private int diaryPage = 0;
    private final int entriesPerPage = 5;
    private Button prevPageButton;
    private Button nextPageButton;

    private Button healthButton;
    private Button dexterityButton;
    private Button strengthButton;
    private Button particleToggleButton;

    public MainMenuGui(PlayerEntity player) {
        super(new StringTextComponent("Ruins of Realms"));
        this.player = player;
        DiaryManager.loadDiary();
    }

    @Override
    protected void init() {
        ModNetworkHandler.INSTANCE.sendToServer(new RequestSyncPlayerStatsPacket());
        this.buttons.clear();
        diaryEntryButtons.clear();
        diaryPage = 0;
        requestInitialCoinSync();

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

        int pbuttonWidth = 150;
        int pbuttonHeight = 20;
        particleToggleButton = new Button(
                this.width - pbuttonWidth - 10,
                this.height - pbuttonHeight - 10,
                pbuttonWidth,
                pbuttonHeight,
                new StringTextComponent(getParticleButtonText()),
                button -> {
                    WorldParticleHandler.toggleParticles();
                    particleToggleButton.setMessage(new StringTextComponent(getParticleButtonText()));
                }
        );

        discordButton.visible = (currentTab == 2);
        githubButton.visible = (currentTab == 2);
        logoButton.visible = (currentTab == 2);
        particleToggleButton.visible = (currentTab == 2);

        this.addButton(discordButton);
        this.addButton(githubButton);
        this.addButton(logoButton);

        if (currentTab == 0) {
            ModNetworkHandler.INSTANCE.sendToServer(new RequestSyncPlayerStatsPacket());
            int startX2 = 45;
            int startY = 45;
            int buttonWidth = 125;
            int buttonHeight = 20;
            int spacing = 10;

            healthButton = new Button(startX2 + buttonWidth + spacing, startY, buttonWidth, buttonHeight, new StringTextComponent("Level Health"), btn -> {
                updateButtonCosts();
                ModNetworkHandler.INSTANCE.sendToServer(new RequestLevelUpPacket("health"));
            });

            dexterityButton = new Button(startX2 + buttonWidth + spacing, startY + (buttonHeight + spacing), buttonWidth, buttonHeight, new StringTextComponent("Level Dexterity"), btn -> {
                updateButtonCosts();
                ModNetworkHandler.INSTANCE.sendToServer(new RequestLevelUpPacket("dexterity"));
            });

            strengthButton = new Button(startX2 + buttonWidth + spacing, startY + 2 * (buttonHeight + spacing), buttonWidth, buttonHeight, new StringTextComponent("Level Strength"), btn -> {
                ModNetworkHandler.INSTANCE.sendToServer(new RequestLevelUpPacket("strength"));
            });

            this.addButton(healthButton);
            this.addButton(dexterityButton);
            this.addButton(strengthButton);
            this.addButton(particleToggleButton);
        }

        if (currentTab == 1) {
            initDiaryEntries();
        }
    }

    public static int calculateDexterityFromModifiers(PlayerEntity player) {
        ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (maxDexAttribute != null) {
            AttributeModifier dexModifier = maxDexAttribute.getModifier(References.DEXTERITY_MODIFIER_UUID);
            if (dexModifier != null) {
                double modifierValue = dexModifier.getAmount();
                return (int) (modifierValue * References.DEXTERITY_REDUCER);
            }
        }
        return 0;
    }

    public static int calculateStrengthFromModifiers(PlayerEntity player) {
        ModifiableAttributeInstance maxStrAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (maxStrAttribute != null) {
            AttributeModifier strModifier = maxStrAttribute.getModifier(References.STRENGTH_MODIFIER_UUID);
            if (strModifier != null) {
                double modifierValue = strModifier.getAmount();
                return (int) (modifierValue * References.STRENGTH_REDUCER);
            }
        }
        return 0;
    }

    private String getParticleButtonText() {
        return WorldParticleHandler.areParticlesEnabled() ?
                "Disable World Particles" : "Enable World Particles";
    }

    private void updateButtonCosts() {
        if (playerStats != null) {
            healthButton.setMessage(new StringTextComponent(playerStats.getHealth() >= 100 ? "MAX" : "Health: " + playerStats.getHealth()  + " (Cost: " + playerStats.getLevelUpCost("health") + ")"));
            dexterityButton.setMessage(new StringTextComponent(playerStats.getDexterity() >= 25 ? "MAX" : "Dexterity: " + playerStats.getDexterity() + " (Cost: " + playerStats.getLevelUpCost("dexterity") + ")"));
            strengthButton.setMessage(new StringTextComponent(playerStats.getStrength() >= 30 ? "MAX" : "Strength: " + playerStats.getStrength() + " (Cost: " + playerStats.getLevelUpCost("strength") + ")"));


            if (playerStats.getHealth() >= 100) {
                healthButton.setFGColor(0xFF0000);
                healthButton.setAlpha(0.5f);
                healthButton.active = false;
            }

            if (playerStats.getDexterity() >= 25) {
                dexterityButton.setFGColor(0xFF0000);
                dexterityButton.setAlpha(0.5f);
                dexterityButton.active = false;
            }

            if (playerStats.getStrength() >= 30) {
                strengthButton.setFGColor(0xFF0000);
                strengthButton.setAlpha(0.5f);
                strengthButton.active = false;
            }
        }
    }

    public void updateStats(int level, int health, int dexterity, int strength) {
        this.playerStats = new PlayerStats(level, health, dexterity, strength);
        updateButtonCosts();
    }

    private void requestInitialCoinSync() {
        if (player.level.isClientSide) {
            ModNetworkHandler.INSTANCE.sendToServer(new RequestCoinSyncPacket());
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
            int y = startY + index * (buttonHeight + spacing);
            Button diaryButton = new Button(startX, y, buttonWidth, buttonHeight, new StringTextComponent(entry.getMonsterName()), btn -> {
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
        for (Button btn : diaryEntryButtons) {
            btn.active = false;
            btn.visible = false;
        }
        this.buttons.removeAll(diaryEntryButtons);

        if (prevPageButton != null) {
            prevPageButton.active = false;
            prevPageButton.visible = false;
            this.buttons.remove(prevPageButton);
        }
        if (nextPageButton != null) {
            nextPageButton.active = false;
            nextPageButton.visible = false;
            this.buttons.remove(nextPageButton);
        }
    }


    private void updateTabVisibility() {
        discordButton.visible = (currentTab == 2);
        githubButton.visible = (currentTab == 2);
        logoButton.visible = (currentTab == 2);
        particleToggleButton.visible = (currentTab == 2);

        healthButton.visible = (currentTab == 0);
        dexterityButton.visible = (currentTab == 0);
        strengthButton.visible = (currentTab == 0);

        removeDiaryWidgets();
        diaryEntryButtons.clear();

        if (currentTab == 1) {
            initDiaryEntries();
        } else {
            currentDiaryEntry = null;
        }

        if (currentTab == 2) {
            particleToggleButton.setMessage(new StringTextComponent(getParticleButtonText()));
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
                int statsX = 20;
                int statsY = 50;
                int statsSpacing = 20;

                if (playerStats != null) {
                    drawString(matrixStack, this.font, "Level: " + playerStats.getLevel(), statsX, statsY, 0xFFFFFF);
                    drawString(matrixStack, this.font, "Health: " + playerStats.getHealth(), statsX, statsY + statsSpacing, 0xFFFFFF);
                    drawString(matrixStack, this.font, "Dexterity: " + playerStats.getDexterity(), statsX, statsY + 2 * statsSpacing, 0xFFFFFF);
                    drawString(matrixStack, this.font, "Strength: " + playerStats.getStrength(), statsX, statsY + 3 * statsSpacing, 0xFFFFFF);
                }

                int coins = player.level.isClientSide ? CoinsManager.getClientCoins() : CoinsManager.getCoins((ServerPlayerEntity) player);
                int coinsLogoX = 27;
                int coinsLogoY = this.height - 14;

                int coinIconSize = 16;
                int coinX = coinsLogoX - coinIconSize - 3;
                int coinY = coinsLogoY - 2;

                this.minecraft.getTextureManager().bind(new ResourceLocation("ror", "textures/gui/coin.png"));
                blit(matrixStack, coinX, coinY - 5, 0, 0, coinIconSize, coinIconSize, coinIconSize, coinIconSize);

                drawString(matrixStack, this.font, coins + " COINS", coinX + 16, coinY, 0xFFFFFF);
                break;
            case 1:
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
                        if (renderEntity instanceof LivingEntity) {
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
