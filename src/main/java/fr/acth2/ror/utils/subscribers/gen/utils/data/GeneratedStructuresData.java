package fr.acth2.ror.utils.subscribers.gen.utils.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeneratedStructuresData extends WorldSavedData {
    private static final String NAME = "ror_generated_structures";
    private final Set<Long> generatedChunks = new HashSet<>();
    private final Map<String, Set<BlockPos>> generatedStructures = new HashMap<>();

    public GeneratedStructuresData() {
        super(NAME);
    }

    public GeneratedStructuresData(String name) {
        super(name);
    }

    public boolean isChunkGenerated(long chunkPos) {
        return generatedChunks.contains(chunkPos);
    }

    public void markChunkGenerated(long chunkPos, String structureId, BlockPos pos) {
        generatedChunks.add(chunkPos);
        generatedStructures.computeIfAbsent(structureId, k -> new HashSet<>()).add(pos);
        setDirty();
    }

    public Set<BlockPos> getStructures(String structureId) {
        return generatedStructures.getOrDefault(structureId, new HashSet<>());
    }

    @Override
    public void load(CompoundNBT nbt) {
        generatedChunks.clear();
        generatedStructures.clear();

        long[] chunksArray = nbt.getLongArray("generatedChunks");
        for (long pos : chunksArray) {
            generatedChunks.add(pos);
        }

        CompoundNBT structuresNBT = nbt.getCompound("generatedStructures");
        for (String key : structuresNBT.getAllKeys()) {
            long[] positions = structuresNBT.getLongArray(key);
            Set<BlockPos> posSet = new HashSet<>();
            for (int i = 0; i < positions.length; i += 3) {
                posSet.add(new BlockPos(
                        (int)(positions[i] >> 38),
                        (int)(positions[i] << 26 >> 52),
                        (int)(positions[i] << 38 >> 38)
                ));
            }
            generatedStructures.put(key, posSet);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        long[] chunksArray = generatedChunks.stream().mapToLong(Long::longValue).toArray();
        nbt.putLongArray("generatedChunks", chunksArray);

        CompoundNBT structuresNBT = new CompoundNBT();
        for (Map.Entry<String, Set<BlockPos>> entry : generatedStructures.entrySet()) {
            long[] positions = new long[entry.getValue().size() * 3];
            int i = 0;
            for (BlockPos pos : entry.getValue()) {
                positions[i++] = ((long)pos.getX() & 0x3FFFFFF) << 38 | ((long)pos.getY() & 0xFFF) << 26 | (long)pos.getZ() & 0x3FFFFFF;
            }
            structuresNBT.putLongArray(entry.getKey(), positions);
        }
        nbt.put("generatedStructures", structuresNBT);

        return nbt;
    }

    public static GeneratedStructuresData get(ServerWorld world) {
        return world.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(GeneratedStructuresData::new, NAME);
    }
}