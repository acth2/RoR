package fr.acth2.ror.gui.coins;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.PacketDistributor;

public class CoinsManager {
    private static final String COINS_TAG = "PlayerCoins";

    public static void setCoins(ServerPlayerEntity player, int coins) {
        CompoundNBT data = player.getPersistentData();
        int newAmount = Math.max(coins, 0);
        data.putInt(COINS_TAG, newAmount);
        syncCoins(player);
    }

    public static void addCoins(ServerPlayerEntity player, int coins) {
        setCoins(player, getCoins(player) + coins);
    }

    public static void removeCoins(ServerPlayerEntity player, int coins) {
        setCoins(player, getCoins(player) - coins);
    }

    public static int getCoins(ServerPlayerEntity player) {
        CompoundNBT data = player.getPersistentData();
        if (!data.contains(COINS_TAG)) {
            data.putInt(COINS_TAG, 0);
        }
        return data.getInt(COINS_TAG);
    }
    private static int clientCoins = 0;

    public static void setClientCoins(int coins) {
        clientCoins = coins;
    }

    public static int getClientCoins() {
        return clientCoins;
    }

    public static void syncCoins(ServerPlayerEntity player) {
        ModNetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncCoinsPacket(getCoins(player))
        );
    }
}