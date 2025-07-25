package fr.acth2.ror.utils.subscribers.gen.overworld.structures;

import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.Structure;
import fr.acth2.ror.utils.subscribers.gen.utils.data.GeneratedStructuresData;
import fr.acth2.ror.utils.subscribers.gen.utils.parser.StructureParser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID)
public class StructureGeneratorSubscriber {
    private static final List<Structure> list_structures = new ArrayList<>();
    private static boolean initialized = false;

    public static void initStructures() {
        if (initialized) return;
        initialized = true;

        //Infranium
        list_structures.add(new Structure(
                new ResourceLocation(References.MODID + ":sealed_treasure_t1"),
                60, 80,
                Arrays.asList("minecraft:plains", "minecraft:desert"),
                225
        ));

        //Gefranium
        list_structures.add(new Structure(
                new ResourceLocation(References.MODID + ":sealed_treasure_t2"),
                60, 80,
                Arrays.asList("minecraft:plains", "minecraft:desert"),
                275
        ));

        //Oronium
        list_structures.add(new Structure(
                new ResourceLocation(References.MODID + ":sealed_treasure_t3"),
                60, 80,
                Arrays.asList("minecraft:plains", "minecraft:desert"),
                375
        ));
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            if (list_structures.isEmpty()) {
                initStructures();
            }
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        if (event.getWorld() instanceof ServerWorld) {
            StructureParser.CACHE.clear();
            System.out.println("Cleared structure cache");
        }
    }

    @SubscribeEvent
    public static void onChunkGenerate(ChunkEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        ServerWorld world = (ServerWorld) event.getWorld();
        IChunk chunk = event.getChunk();
        if (chunk.getStatus() != ChunkStatus.FULL) return;

        ChunkPos chunkPos = chunk.getPos();
        long chunkLongPos = chunkPos.toLong();

        GeneratedStructuresData data = GeneratedStructuresData.get(world);
        if (data.isChunkGenerated(chunkLongPos)) {
            return;
        }

        if (!initialized) initStructures();

        Random random = new Random(world.getSeed());
        random.setSeed(random.nextLong() ^ chunkLongPos);

        if (generateStructure(random, chunkPos.x, chunkPos.z, world)) {
            data.markChunkGenerated(chunkLongPos);
        }
    }

    public static boolean generateStructure(Random random, int chunkX, int chunkZ, ServerWorld world) {
        if (list_structures.isEmpty()) return false;

        boolean generatedAny = false;
        for (Structure structure : list_structures) {
            if (random.nextInt(structure.getRarity()) == 0) {
                BlockPos pos = new BlockPos(
                        chunkX * 16 + 8,
                        world.getSeaLevel(),
                        chunkZ * 16 + 8
                );

                if (structure.generate(world, world.getStructureManager(), random, pos)) {
                    System.out.println("Generated structure at " + pos);
                    generatedAny = true;
                }
                break;
            }
        }
        return generatedAny;
    }
}