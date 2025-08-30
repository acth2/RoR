package fr.acth2.ror.utils.subscribers.gen.skyria.structures;

import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.Structure;
import fr.acth2.ror.utils.subscribers.gen.utils.data.GeneratedStructuresData;
import fr.acth2.ror.utils.subscribers.gen.utils.parser.StructureParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
public class SkyriaStructureGeneratorSubscriber {
    private static final List<Structure> list_structures = new ArrayList<>();
    private static boolean initialized = false;
    private static List allList = Arrays.asList(
            "minecraft:ocean",
            "minecraft:plains",
            "minecraft:desert",
            "minecraft:mountains",
            "minecraft:forest",
            "minecraft:taiga",
            "minecraft:swamp",
            "minecraft:river",
            "minecraft:nether_wastes",
            "minecraft:the_end",
            "minecraft:frozen_ocean",
            "minecraft:frozen_river",
            "minecraft:snowy_tundra",
            "minecraft:snowy_mountains",
            "minecraft:mushroom_fields",
            "minecraft:mushroom_field_shore",
            "minecraft:beach",
            "minecraft:desert_hills",
            "minecraft:wooded_hills",
            "minecraft:taiga_hills",
            "minecraft:mountain_edge",
            "minecraft:jungle",
            "minecraft:jungle_hills",
            "minecraft:jungle_edge",
            "minecraft:deep_ocean",
            "minecraft:stone_shore",
            "minecraft:snowy_beach",
            "minecraft:birch_forest",
            "minecraft:birch_forest_hills",
            "minecraft:dark_forest",
            "minecraft:snowy_taiga",
            "minecraft:snowy_taiga_hills",
            "minecraft:giant_tree_taiga",
            "minecraft:giant_tree_taiga_hills",
            "minecraft:wooded_mountains",
            "minecraft:savanna",
            "minecraft:savanna_plateau",
            "minecraft:badlands",
            "minecraft:wooded_badlands_plateau",
            "minecraft:badlands_plateau",
            "minecraft:small_end_islands",
            "minecraft:end_midlands",
            "minecraft:end_highlands",
            "minecraft:end_barrens",
            "minecraft:warm_ocean",
            "minecraft:lukewarm_ocean",
            "minecraft:cold_ocean",
            "minecraft:deep_warm_ocean",
            "minecraft:deep_lukewarm_ocean",
            "minecraft:deep_cold_ocean",
            "minecraft:deep_frozen_ocean",
            "minecraft:the_void",
            "minecraft:sunflower_plains",
            "minecraft:desert_lakes",
            "minecraft:gravelly_mountains",
            "minecraft:flower_forest",
            "minecraft:taiga_mountains",
            "minecraft:swamp_hills",
            "minecraft:ice_spikes",
            "minecraft:modified_jungle",
            "minecraft:modified_jungle_edge",
            "minecraft:tall_birch_forest",
            "minecraft:tall_birch_hills",
            "minecraft:dark_forest_hills",
            "minecraft:snowy_taiga_mountains",
            "minecraft:giant_spruce_taiga",
            "minecraft:giant_spruce_taiga_hills",
            "minecraft:modified_gravelly_mountains",
            "minecraft:shattered_savanna",
            "minecraft:shattered_savanna_plateau",
            "minecraft:eroded_badlands",
            "minecraft:modified_wooded_badlands_plateau",
            "minecraft:modified_badlands_plateau",
            "minecraft:bamboo_jungle",
            "minecraft:bamboo_jungle_hills"
    );

    public static void initStructures() {
        if (initialized) return;
        initialized = true;

        list_structures.add(new Structure(
                new ResourceLocation(References.MODID + ":skyria_tower"),
                60, 80,
                allList,
                350
        ));
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();
            if (world.dimension().location().toString().equals("ror:skyria")) {
                if (list_structures.isEmpty()) {
                    initStructures();
                }
             }
        }
    }

    @SubscribeEvent
    public static void onChunkGenerate(ChunkEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        ServerWorld world = (ServerWorld) event.getWorld();

        if (!world.dimension().location().toString().equals("ror:skyria")) {
            return;
        }

        IChunk chunk = event.getChunk();
        if (chunk.getStatus() != ChunkStatus.FULL) return;

        ChunkPos chunkPos = chunk.getPos();
        long chunkLongPos = chunkPos.toLong();

        GeneratedStructuresData data = GeneratedStructuresData.get(world);
        if (data.isChunkGenerated(chunkLongPos)) {
            return;
        }

        if (!initialized) initStructures();

        long worldSeed = world.getSeed();
        Random random = new Random(worldSeed);
        long chunkSpecificSeed = (long) chunkPos.x * 341873128712L + (long) chunkPos.z * 132897987541L;
        random.setSeed(worldSeed ^ chunkSpecificSeed);
        generateStructure(random, chunkPos.x, chunkPos.z, world, data, chunkLongPos);
    }

    public static void generateStructure(Random random, int chunkX, int chunkZ, ServerWorld world, GeneratedStructuresData data, long chunkLongPos) {
        if (list_structures.isEmpty()) {
            return;
        }

        for (Structure structure : list_structures) {
            int rarityResult = random.nextInt(structure.getRarity());

            if (rarityResult == 0) {
                BlockPos suitablePos = findSuitableSkyriaPosition(world, chunkX, chunkZ, structure.getMinY());

                if (suitablePos == null) continue;


                if (structure.generate(world, world.getStructureManager(), random, suitablePos)) {
                    data.markChunkGenerated(chunkLongPos, structure.getStructureLocation().getPath(), suitablePos);
                }
                break;
            }
        }
    }

    private static BlockPos findSuitableSkyriaPosition(ServerWorld world, int chunkX, int chunkZ, int minY) {

        for (int attempt = 0; attempt < 5; attempt++) {
            int xInChunk = 4 + attempt * 3;
            int zInChunk = 4 + attempt * 3;

            if (xInChunk >= 16) xInChunk = 15;
            if (zInChunk >= 16) zInChunk = 15;

            int worldX = chunkX * 16 + xInChunk;
            int worldZ = chunkZ * 16 + zInChunk;

            for (int y = world.getMaxBuildHeight(); y >= minY; y--) {
                BlockPos currentPos = new BlockPos(worldX, y, worldZ);
                BlockPos belowPos = currentPos.below();
                BlockPos abovePos = currentPos.above();

                boolean isCloudBelow = isCloudBlock(world, belowPos);
                boolean isAirAtPos = world.isEmptyBlock(currentPos);
                boolean isAirAbove = world.isEmptyBlock(abovePos);

                if (isCloudBelow && isAirAtPos && isAirAbove) {
                    return currentPos;
                }
            }
        }
        return null;
    }

    private static boolean isCloudBlock(ServerWorld world, BlockPos pos) {
        if (!world.isLoaded(pos)) {
            return false;
        }

        try {
            String blockName = world.getBlockState(pos).getBlock().getRegistryName().toString();
            return blockName.equals("ror:cloud_piece");
        } catch (Exception e) {
            return false;
        }
    }
}