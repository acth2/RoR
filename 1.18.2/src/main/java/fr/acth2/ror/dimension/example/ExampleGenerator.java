package fr.acth2.ror.dimension.example;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import fr.acth2.ror.init.ModBlocks;
import net.minecraft.world.level.StructureManager;
import net.minecraft.data.structures.StructureUpdater;

public class ExampleGenerator extends ChunkGenerator {

    private BiomeSource BiomeSource;

    public static final Codec<ExampleGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(ExampleGenerator::getBiomeProvider),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> () -> generator.settings)
            ).apply(instance, (BiomeSource, settingsSupplier) ->
                    new ExampleGenerator(BiomeSource, settingsSupplier.get())
            )
    );

    private final NoiseGeneratorSettings settings;

    public ExampleGenerator(BiomeSource BiomeSource, NoiseGeneratorSettings settings) {
        super(BiomeSource, settings.structureSettings());
        this.BiomeSource = BiomeSource;
        this.settings = settings;
    }

    public BiomeSource getCustomBiomeProvider() {
        return this.BiomeSource;
    }

    public BiomeSource getBiomeProvider() {
        return this.BiomeSource;
    }

    @Override
    public int getSeaLevel() {
        return 64;
    }

    @Override
    public int getBaseHeight(int p_222529_1_, int p_222529_2_, Heightmap.Type p_222529_3_) {
        return 64;
    }

    @Override
    public BlockGetter getBaseColumn(int p_230348_1_, int p_230348_2_) {
        return null;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new ExampleGenerator(this.BiomeSource, this.settings);
    }

    @Override
    public void buildSurface(WorldGenLevel p_225551_1_, IChunk p_225551_2_) {

    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager structures, IChunk chunk) {
        int baseY = 64;
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;
                BlockPos pos = new BlockPos(x, baseY, z);
                if ((worldX + worldZ) % 2 == 0) {
                    chunk.setBlockState(pos, ModBlocks.EXAMPLE_BLOCK.get().defaultBlockState(), false);
                } else {
                    chunk.setBlockState(pos, Blocks.BLACK_CONCRETE.defaultBlockState(), false);
                }
            }
        }

        double frequency = 0.05;
        double threshold = 0.95;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;
                double noise = Math.abs(Math.sin(worldX * frequency) * Math.cos(worldZ * frequency));
                if (noise > threshold) {
                    BlockPos raisedPos = new BlockPos(x, baseY + 1, z);
                    chunk.setBlockState(raisedPos, Blocks.BLACK_CONCRETE.defaultBlockState(), false);
                }
            }
        }

    }


    @Override
    public void applyCarvers(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStep.Carving p_230350_5_) {

    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel p_230351_1_, StructureManager p_230351_2_) {

    }

    @Override
    public void createStructures(RegistryAccess p_242707_1_, StructureManager p_242707_2_, IChunk p_242707_3_, TemplateManager p_242707_4_, long p_242707_5_) {

    }


}
