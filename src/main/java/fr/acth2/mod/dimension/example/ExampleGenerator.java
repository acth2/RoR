package fr.acth2.mod.dimension.example;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.acth2.mod.entities.entity.EntityExample;
import fr.acth2.mod.init.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.ChunkPrimer;
import fr.acth2.mod.init.ModBlocks;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExampleGenerator extends ChunkGenerator {

    private BiomeProvider biomeProvider;

    public static final Codec<ExampleGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter(ExampleGenerator::getBiomeProvider),
                    DimensionSettings.CODEC.fieldOf("settings").forGetter(generator -> () -> generator.settings)
            ).apply(instance, (biomeProvider, settingsSupplier) ->
                    new ExampleGenerator(biomeProvider, settingsSupplier.get())
            )
    );

    private final DimensionSettings settings;

    public ExampleGenerator(BiomeProvider biomeProvider, DimensionSettings settings) {
        super(biomeProvider, settings.structureSettings());
        this.biomeProvider = biomeProvider;
        this.settings = settings;
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
    public int getBaseHeight(int p_222529_1_, int p_222529_2_, Heightmap.Type p_222529_3_) {
        return 64;
    }

    @Override
    public IBlockReader getBaseColumn(int p_230348_1_, int p_230348_2_) {
        return null;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new ExampleGenerator(this.biomeProvider, this.settings);
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion p_225551_1_, IChunk p_225551_2_) {

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
    public void applyCarvers(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStage.Carving p_230350_5_) {

    }

    @Override
    public void applyBiomeDecoration(WorldGenRegion p_230351_1_, StructureManager p_230351_2_) {

    }

    @Override
    public void createStructures(DynamicRegistries p_242707_1_, StructureManager p_242707_2_, IChunk p_242707_3_, TemplateManager p_242707_4_, long p_242707_5_) {

    }


}
