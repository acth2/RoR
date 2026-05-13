package fr.acth2.ror.utils;

import fr.acth2.ror.nbt.PlayerDimensionData;
import net.minecraft.world.entity.player.Player;

public class DimensionAccessManager {

    public static boolean hasSkyriaAccess(Player player) {
        if (player == null || player.level.isClientSide) return false;

        PlayerDimensionData data = PlayerDimensionData.get(player.level);
        return data.hasSkyriaAccess(player.getUUID());
    }

    public static void grantSkyriaAccess(Player player) {
        if (player != null && !player.level.isClientSide) {
            PlayerDimensionData data = PlayerDimensionData.get(player.level);
            data.setSkyriaAccess(player.getUUID(), true);
        }
    }

    public static boolean checkSkyriaConditions(Player player) {
        return player != null && player.getY() >= 100.0;
    }
}