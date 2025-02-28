package fr.acth2.ror.gui.coins;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CoinsManager {

    private static int playerCoins = 0;
    private static final File COINS_FILE = new File("config/coins.dat");

    public static void loadCoins() {
        if (!COINS_FILE.exists()) {
            playerCoins = 0;
            saveCoins();
            return;
        }
        try (FileInputStream fis = new FileInputStream(COINS_FILE)) {
            CompoundNBT root = CompressedStreamTools.readCompressed(fis);
            playerCoins = root.getInt("Coins");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCoins() {
        CompoundNBT root = new CompoundNBT();
        root.putInt("Coins", playerCoins);

        try (FileOutputStream fos = new FileOutputStream(COINS_FILE)) {
            CompressedStreamTools.writeCompressed(root, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCoins(int coins) {
        playerCoins = coins;
        saveCoins();
    }

    public static void addCoins(int coins) {
        playerCoins += coins;
        saveCoins();
    }

    public static void removeCoins(int coins) {
        if (playerCoins - coins < 0) {
            playerCoins = 0;
        } else {
            playerCoins -= coins;
        }
        saveCoins();
    }

    public static int getCoins() {
        return playerCoins;
    }

    public static boolean hasLeastCoins(int howMany) {
        return playerCoins >= howMany;
    }
}