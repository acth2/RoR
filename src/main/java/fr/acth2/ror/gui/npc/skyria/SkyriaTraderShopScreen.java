package fr.acth2.ror.gui.npc.skyria;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.traveler.PurchaseItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SkyriaTraderShopScreen extends Screen {

    private final PlayerEntity player;
    private final List<ShopItem> shopItems = new ArrayList<>();
    private final int itemSize = 32;

    public SkyriaTraderShopScreen(PlayerEntity player) {
        super(new StringTextComponent("Shop"));
        this.player = player;

        shopItems.add(new ShopItem(new ItemStack(Items.CHORUS_FRUIT), 100));
        shopItems.add(new ShopItem(new ItemStack(Items.COOKED_BEEF), 35));
        shopItems.add(new ShopItem(new ItemStack(ModItems.GLIDER.get()), 1000));
        shopItems.add(new ShopItem(new ItemStack(ModItems.ORONIUM_SWORD.get()), 300));
    }

    @Override
    public void init() {
        super.init();
        if (player instanceof ServerPlayerEntity) {
            CoinsManager.syncCoins((ServerPlayerEntity) player);
            triggerCoinSync();
        }
    }

    private void triggerCoinSync() {
        if (!player.level.isClientSide && player instanceof ServerPlayerEntity) {
            CoinsManager.addCoins((ServerPlayerEntity) player, 0);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        fill(matrixStack, 0, 0, this.width, this.height, 0xFF202020);

        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 15, 0xFFFFFF);
        int displayCoins = player.level.isClientSide ?
                CoinsManager.getClientCoins() :
                CoinsManager.getCoins((ServerPlayerEntity) player);

        drawCenteredString(matrixStack, this.font, "Coins: " + displayCoins,
                this.width / 2, 35, 0xFFFFD700);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        int totalWidth = shopItems.size() * (itemSize + 10) - 20;
        int startX = (this.width - totalWidth) / 2;
        int startY = this.height / 3;

        for (int i = 0; i < shopItems.size(); i++) {
            ShopItem shopItem = shopItems.get(i);
            int x = startX + i * (itemSize + 10);
            int y = startY;

            drawCenteredString(matrixStack, this.font, String.valueOf(shopItem.cost), x + 8, y - 12, 0xFFFFFF);
            itemRenderer.renderGuiItem(shopItem.stack, x, y);

            if (mouseX >= x && mouseX <= x + itemSize && mouseY >= y && mouseY <= y + itemSize) {
                renderTooltip(matrixStack, shopItem.stack, mouseX, mouseY);
            }
        }

        drawCenteredString(matrixStack, this.font, "Click an item to buy!", this.width / 2, startY + 60, 0xFFFFA500);
        drawCenteredString(matrixStack, this.font, "[ESC] to exit", this.width / 2, this.height - 20, 0xAAAAAA);
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int startX = (this.width - (shopItems.size() * (itemSize + 10))) / 2;
        int startY = this.height / 3;

        for (int i = 0; i < shopItems.size(); i++) {
            int x = startX + i * (itemSize + 10);
            int y = startY;

            if (mouseX >= x && mouseX <= x + itemSize && mouseY >= y && mouseY <= y + itemSize) {
                ShopItem shopItem = shopItems.get(i);

                ModNetworkHandler.INSTANCE.sendToServer(new PurchaseItemPacket(shopItem.stack, shopItem.cost));

                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class ShopItem {
        final ItemStack stack;
        final int cost;

        ShopItem(ItemStack stack, int cost) {
            this.stack = stack;
            this.cost = cost;
        }
    }
}
