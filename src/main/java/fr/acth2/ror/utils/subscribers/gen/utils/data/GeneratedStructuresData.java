package fr.acth2.ror.utils.subscribers.gen.utils.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashSet;
import java.util.Set;

public class GeneratedStructuresData extends WorldSavedData {
    private static final String NAME = "ror_generated_structures";
    private final Set<Long> generatedChunks = new HashSet<>();

    public GeneratedStructuresData() {
        super(NAME);
    }

    public GeneratedStructuresData(String name) {
        super(name);
    }

    public boolean isChunkGenerated(long chunkPos) {
        return generatedChunks.contains(chunkPos);
    }

    public void markChunkGenerated(long chunkPos) {
        generatedChunks.add(chunkPos);
        setDirty();
    }

    @Override
    public void load(CompoundNBT nbt) {
        generatedChunks.clear();
        long[] chunksArray = nbt.getLongArray("generatedChunks");
        for (long pos : chunksArray) {
            generatedChunks.add(pos);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        long[] chunksArray = generatedChunks.stream().mapToLong(Long::longValue).toArray();
        nbt.putLongArray("generatedChunks", chunksArray);
        return nbt;
    }

    public static GeneratedStructuresData get(ServerWorld world) {
        return world.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(GeneratedStructuresData::new, NAME);
    }
}