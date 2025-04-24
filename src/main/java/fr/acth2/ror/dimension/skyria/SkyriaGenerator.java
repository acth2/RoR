package fr.acth2.ror.dimension.skyria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.acth2.ror.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class SkyriaGenerator extends ChunkGenerator {
    private static final Random RANDOM = new Random();
    private final BiomeProvider biomeProvider;
    private final DimensionSettings settings;
    private final long seed;
    private final SimplexNoiseGenerator islandNoise;
    private final SimplexNoiseGenerator detailNoise;

    public static final Codec<SkyriaGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter(SkyriaGenerator::getBiomeProvider),
                    DimensionSettings.CODEC.fieldOf("settings").forGetter(generator -> () -> generator.settings),
                    Codec.LONG.fieldOf("seed").forGetter(generator -> generator.seed)
            ).apply(instance, (biomeProvider, settingsSupplier, seed) ->
                    new SkyriaGenerator(biomeProvider, settingsSupplier.get(), seed)
            )
    );


    public SkyriaGenerator(BiomeProvider biomeProvider, DimensionSettings settings, long seed) {
        super(biomeProvider, settings.structureSettings());
        this.biomeProvider = biomeProvider;
        this.settings = settings;
        this.seed = seed;
        this.islandNoise = new SimplexNoiseGenerator(new Random(seed));
        this.detailNoise = new SimplexNoiseGenerator(new Random(seed + 1));
    }


    public BiomeProvider getCustomBiomeProvider() {
        return this.biomeProvider;
    }

    public BiomeProvider getBiomeProvider() {
        return this.biomeProvider;
    }

    @Override
    public int getSeaLevel() {
        return 64;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return 64;
    }

    @Override
    public IBlockReader getBaseColumn(int x, int z) {
        return null;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new SkyriaGenerator(this.biomeProvider.withSeed(seed), this.settings, seed);
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(pos.set(x, 0, z), Blocks.AIR.defaultBlockState(), false);
            }
        }
    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager structures, IChunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;
                generateCleanIsland(chunk, worldX, worldZ, x, z);

                if (MathHelper.abs(worldX + worldZ) % 7 == 0) {
                    generateSmoothCloud(chunk, x, z, 70 + MathHelper.abs(worldX + worldZ) % 30);
                }
            }
        }
    }

    private void generateCleanIsland(IChunk chunk, int worldX, int worldZ, int x, int z) {
        double islandValue = islandNoise.getValue(worldX * 0.02, worldZ * 0.02) * 0.5 + 0.5;

        if (islandValue > 0.65) {
            int baseY = 90 + (int)(islandValue * 20);
            float radius = 3.5f + (float)islandNoise.getValue(worldX * 0.1, worldZ * 0.1) * 2f;

            for (int y = baseY - (int)radius; y < baseY + 4; y++) {
                for (int dx = -(int)radius; dx <= radius; dx++) {
                    for (int dz = -(int)radius; dz <= radius; dz++) {
                        if (x + dx >= 0 && x + dx < 16 && z + dz >= 0 && z + dz < 16) {
                            float horizontalDist = MathHelper.sqrt(dx*dx + dz*dz);

                            float verticalDist = baseY - y;
                            if (horizontalDist <= radius * getVerticalMultiplier(y, baseY, radius)) {
                                double detail = detailNoise.getValue((worldX + dx) * 0.2, (worldZ + dz) * 0.2) * 0.5;

                                if (y >= baseY || (horizontalDist < radius * 0.8f)) {
                                    if (y == baseY || (horizontalDist < radius - 1 && y < baseY + 3)) {
                                        chunk.setBlockState(new BlockPos(x + dx, y, z + dz),
                                                ModBlocks.CLOUD_PIECE.get().defaultBlockState(), false);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (RANDOM.nextFloat() < 0.00095f) {
                generateOroniumSpike(chunk, x, z);
            }

            if (islandValue > 0.8 && x % 4 == 0 && z % 4 == 0) {
                chunk.setBlockState(new BlockPos(x, baseY + 3, z),
                        Blocks.CHORUS_PLANT.defaultBlockState(), false);
            }
        }
    }


    private void generateOroniumSpike(IChunk chunk, int x, int z) {
        int height = 3 + RANDOM.nextInt(5);
        int baseY = 60 + RANDOM.nextInt(100);

        for (int y = baseY; y < baseY + height; y++) {
            chunk.setBlockState(new BlockPos(x, y, z), ModBlocks.ORONIUM_ORE.get().defaultBlockState(), false);
        }

        if (RANDOM.nextBoolean()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (x + dx >= 0 && x + dx < 16 && z + dz >= 0 && z + dz < 16) {
                        chunk.setBlockState(new BlockPos(x + dx, baseY + height, z + dz), ModBlocks.ORONIUM_ORE.get().defaultBlockState(), false);
                    }
                }
            }
        }
    }

    private void generateSmoothCloud(IChunk chunk, int x, int z, int baseY) {
        float radius = 4f + (float)detailNoise.getValue(x * 10, z * 10) * 2f;

        for (int dy = 0; dy < 2; dy++) {
            for (int dx = -(int)radius; dx <= radius; dx++) {
                for (int dz = -(int)radius; dz <= radius; dz++) {
                    if (x + dx >= 0 && x + dx < 16 && z + dz >= 0 && z + dz < 16) {
                        float dist = MathHelper.sqrt(dx*dx + dz*dz);
                        if (dist <= radius) {
                            chunk.setBlockState(new BlockPos(x + dx, baseY + dy, z + dz),
                                    ModBlocks.SKYRIA_AIR.get().defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void applyCarvers(long seed, BiomeManager biomeManager, IChunk chunk, GenerationStage.Carving carving) {}

    @Override
    public void applyBiomeDecoration(WorldGenRegion region, StructureManager structures) {}

    @Override
    public void createStructures(DynamicRegistries registries, StructureManager structures, IChunk chunk,
                                 TemplateManager templates, long seed) {}

    private float getVerticalMultiplier(int y, int baseY, float radius) {
        if (y >= baseY) return 1.0f;

        float progress = (baseY - y) / radius;
        return MathHelper.sqrt(1 - progress * progress);
    }
}