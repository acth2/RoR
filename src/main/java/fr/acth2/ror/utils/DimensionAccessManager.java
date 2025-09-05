package fr.acth2.ror.utils;

import fr.acth2.ror.nbt.PlayerDimensionData;
import net.minecraft.entity.player.PlayerEntity;

public class DimensionAccessManager {

    public static boolean hasSkyriaAccess(PlayerEntity player) {
        if (player == null || player.level.isClientSide) return false;

        PlayerDimensionData data = PlayerDimensionData.get(player.level);
        return data.hasSkyriaAccess(player.getUUID());
    }

    public static void grantSkyriaAccess(PlayerEntity player) {
        if (player != null && !player.level.isClientSide) {
            PlayerDimensionData data = PlayerDimensionData.get(player.level);
            data.setSkyriaAccess(player.getUUID(), true);
        }
    }

    public static boolean checkSkyriaConditions(PlayerEntity player) {
        return player != null && player.getY() >= 100.0;
    }
}