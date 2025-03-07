package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerStatsCapability implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(PlayerStats.class)
    public static Capability<PlayerStats> PLAYER_STATS_CAPABILITY = null;

    private final PlayerStats instance = new PlayerStats(1, 20, 10, 1);
    private final LazyOptional<PlayerStats> lazyInstance = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_STATS_CAPABILITY.orEmpty(cap, lazyInstance);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        instance.deserializeNBT(nbt);
    }
}