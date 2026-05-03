package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerStatsStorage implements Capability.IStorage<PlayerStats> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<PlayerStats> capability, PlayerStats instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<PlayerStats> capability, PlayerStats instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}