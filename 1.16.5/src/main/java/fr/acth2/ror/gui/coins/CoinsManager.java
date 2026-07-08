package fr.acth2.ror.gui.coins;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import net.minecraft.entity.player.PlayerEntity;
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

    public static void saveCoinsOnDeath(PlayerEntity player, int coins) {
        CompoundNBT persistentData = player.getPersistentData();
        persistentData.putInt("PlayerCoins", coins);
        System.out.println("Saved coins on death: " + coins);
    }

    public static int loadCoinsOnRespawn(PlayerEntity player) {
        CompoundNBT persistentData = player.getPersistentData();
        if (persistentData.contains("PlayerCoins")) {
            int coins = persistentData.getInt("PlayerCoins");
            System.out.println("Loaded coins on respawn: " + coins);
            return coins;
        }
        System.out.println("No coins found in persistent data");
        return 0;
    }

    public static void syncCoins(ServerPlayerEntity player) {
        ModNetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncCoinsPacket(getCoins(player))
        );
    }
}