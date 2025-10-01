package fr.acth2.ror.events.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class WorldEventData extends WorldSavedData {
    private static final String NAME = "ror_events";
    private final Map<String, Boolean> activeEvents = new HashMap<>();

    public WorldEventData() {
        super(NAME);
    }

    public static WorldEventData get(ServerWorld world) {
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.computeIfAbsent(WorldEventData::new, NAME);
    }

    public void setEventActive(String event, boolean active) {
        activeEvents.put(event, active);
        setDirty();
    }

    public boolean isEventActive(String event) {
        return activeEvents.getOrDefault(event, false);
    }

    @Override
    public void load(CompoundNBT nbt) {
        activeEvents.clear();
        CompoundNBT events = nbt.getCompound("ActiveEvents");
        for (String key : events.getAllKeys()) {
            activeEvents.put(key, events.getBoolean(key));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT events = new CompoundNBT();
        activeEvents.forEach(events::putBoolean);
        nbt.put("ActiveEvents", events);
        return nbt;
    }
}