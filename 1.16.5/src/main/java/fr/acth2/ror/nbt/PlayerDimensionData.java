package fr.acth2.ror.nbt;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDimensionData extends WorldSavedData {
    private static final String NAME = "ror_player_dimension_data";
    private final Map<UUID, Boolean> playerSkyriaAccess = new HashMap<>();

    public PlayerDimensionData() {
        super(NAME);
    }

    public PlayerDimensionData(String name) {
        super(name);
    }

    public boolean hasSkyriaAccess(UUID playerId) {
        return playerSkyriaAccess.getOrDefault(playerId, false);
    }

    public void setSkyriaAccess(UUID playerId, boolean hasAccess) {
        playerSkyriaAccess.put(playerId, hasAccess);
        setDirty();
    }

    @Override
    public void load(CompoundNBT nbt) {
        playerSkyriaAccess.clear();
        if (nbt.contains("playerAccess")) {
            ListNBT list = nbt.getList("playerAccess", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundNBT playerData = list.getCompound(i);
                UUID playerId = playerData.getUUID("playerId");
                boolean hasAccess = playerData.getBoolean("hasSkyriaAccess");
                playerSkyriaAccess.put(playerId, hasAccess);
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        for (Map.Entry<UUID, Boolean> entry : playerSkyriaAccess.entrySet()) {
            CompoundNBT playerData = new CompoundNBT();
            playerData.putUUID("playerId", entry.getKey());
            playerData.putBoolean("hasSkyriaAccess", entry.getValue());
            list.add(playerData);
        }
        nbt.put("playerAccess", list);
        return nbt;
    }

    public static PlayerDimensionData get(World world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(PlayerDimensionData::new, NAME);
    }
}