package fr.acth2.ror.dimension.abyssaria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.World;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.StructureManager;
import net.minecraft.data.structures.StructureUpdater;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Random;

public class AbyssariaGenerator extends ChunkGenerator {

    private BiomeSource BiomeSource;
    private Random random;
    private SimplexNoise sNoise;
    private SimplexNoise sSulfurNoise;

    public static final Codec<AbyssariaGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(AbyssariaGenerator::getBiomeProvider),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> () -> generator.settings)
            ).apply(instance, (BiomeSource, settingsSupplier) ->
                    new AbyssariaGenerator(BiomeSource, settingsSupplier.get())
            )
    );

    private final NoiseGeneratorSettings settings;

    public AbyssariaGenerator(BiomeSource BiomeSource, NoiseGeneratorSettings settings) {
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
        return new Blockreader(new BlockState[0]);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new AbyssariaGenerator(this.BiomeSource, this.settings);
    }

    @Override
    public void buildSurface(WorldGenLevel p_225551_1_, IChunk p_225551_2_) {

    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager structures, IChunk chunk) {
        if (random == null) random = new Random(((WorldGenLevel) world).getSeed());
        if (sNoise == null) sNoise = new SimplexNoise(random);
        if (sSulfurNoise == null) sSulfurNoise = new SimplexNoise(new Random(((WorldGenLevel) world).getSeed() + 2L));

        final double basic_threshold = 0.3D;
        final double basic_scale = 0.02;

        // CAVE GENERATION
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y <= 128; y++) {
                    int worldX = chunk.getPos().x * 16 + x;
                    int worldZ = chunk.getPos().z * 16 + z;
                    BlockPos current_pos = new BlockPos(x, y, z);

                    if (y == 128 || y == 0) {
                        chunk.setBlockState(current_pos, Blocks.BEDROCK.defaultBlockState(), false);
                        continue;
                    }

                    if (sNoise.getValue(worldX * basic_scale, worldZ * basic_scale, y * basic_scale) >= basic_threshold) {
                        if (y == 1) {
                            chunk.setBlockState(current_pos, ModBlocks.ABYSSAL_GLUE.get().defaultBlockState(), false);
                        } else {
                            chunk.setBlockState(current_pos, Blocks.AIR.defaultBlockState(), false);
                        }
                    } else {
                        chunk.setBlockState(current_pos, ModBlocks.ABYSSAL_STONE.get().defaultBlockState(), false);
                    }
                }
            }
        }

        final double pillars_threshold = 0.3D;
        final double pillars_scale = 0.2;

        // DETAILS
        for (int y = 0; y <= 128; y++) {
            int worldX = chunk.getPos().x * 16 + 8;
            int worldZ = chunk.getPos().z * 16 + 8;

            if (sNoise.getValue(worldX * pillars_scale, worldZ * pillars_scale, y * pillars_scale) >= pillars_threshold) {
                int evenCounter = 0;
                for (int yp = 0; yp < 128; yp++) {
                    if (yp % 2 == 0) evenCounter++;
                    chunk.setBlockState(new BlockPos(worldX, yp, worldZ), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                    Bridge bridge = Bridge.EMPTY;

                    if (evenCounter == 10) {
                        if (random.nextInt(100) == 1) {
                            chunk.setBlockState(new BlockPos(worldX + 1, yp, worldZ), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX - 1, yp, worldZ), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX, yp, worldZ + 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX, yp, worldZ - 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX + 1, yp, worldZ + 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX - 1, yp, worldZ - 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX + 1, yp, worldZ - 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                            chunk.setBlockState(new BlockPos(worldX - 1, yp, worldZ + 1), ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);

                            final int BRIDGE_SEARCH_LENGTH = 15;
                            if (world.getBlockState(new BlockPos(worldX + BRIDGE_SEARCH_LENGTH, yp, worldZ)) == ModBlocks.ABYSSAL_BRICK.get().defaultBlockState()) bridge = Bridge.LEFT;
                            if (world.getBlockState(new BlockPos(worldX - BRIDGE_SEARCH_LENGTH, yp, worldZ)) == ModBlocks.ABYSSAL_BRICK.get().defaultBlockState()) bridge = Bridge.RIGHT;
                            if (world.getBlockState(new BlockPos(worldX, yp, worldZ + BRIDGE_SEARCH_LENGTH)) == ModBlocks.ABYSSAL_BRICK.get().defaultBlockState()) bridge = Bridge.UP;
                            if (world.getBlockState(new BlockPos(worldX, yp, worldZ - BRIDGE_SEARCH_LENGTH)) == ModBlocks.ABYSSAL_BRICK.get().defaultBlockState()) bridge = Bridge.DOWN;
                        }
                        evenCounter = 0;
                    }

                    if (bridge != Bridge.EMPTY) {
                        //RANDOM ICI
                        for (int i = 0; i < 7; i++) {
                            int currentY = yp;
                            if (i >= 2) currentY--;
                            if (i >= 4) currentY--;

                            BlockPos bridgePos = null;

                            switch (bridge) {
                                case LEFT:
                                    bridgePos = new BlockPos(worldX + i, currentY, worldZ);
                                    break;
                                case RIGHT:
                                    bridgePos = new BlockPos(worldX - i, currentY, worldZ);
                                    break;
                                case UP:
                                    bridgePos = new BlockPos(worldX, currentY, worldZ + i);
                                    break;
                                case DOWN:
                                    bridgePos = new BlockPos(worldX, currentY, worldZ - i);
                                    break;
                            }

                            BlockPos beamPos = bridgePos;

                            if (i == 3) {
                                for (int k = 0; k < 120; k++) {
                                    beamPos = beamPos.below();
                                    if (world.getBlockState(beamPos).getBlock() == Blocks.AIR) {
                                        chunk.setBlockState(beamPos, ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                                    } else break;
                                }
                            }

                            if (i == 6) {
                                chunk.setBlockState(beamPos, Blocks.AIR.defaultBlockState(), false);
                                for (int k = 0; k < 120; k++) {
                                    beamPos = beamPos.below();
                                    if (world.getBlockState(beamPos).getBlock() == Blocks.AIR) {
                                        chunk.setBlockState(beamPos, ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                                    } else break;
                                }
                            }

                            if (i != 6) chunk.setBlockState(bridgePos, ModBlocks.ABYSSAL_BRICK.get().defaultBlockState(), false);
                        }
                    }
                }
            }
        }


        final double ore_veins_threshold = 0.9D;
        final double ore_veins_scale = 0.1;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    int worldX = chunk.getPos().x * 16 + x;
                    int worldZ = chunk.getPos().z * 16 + z;

                    if (sNoise.getValue(worldX * ore_veins_scale, worldZ * ore_veins_scale, y * ore_veins_scale) >= ore_veins_threshold) {
                        BlockPos current_pos = new BlockPos(x, y, z);
                        BlockPos world_pos = new BlockPos(worldX, y, worldZ);
                        if (world.getBlockState(world_pos).getBlock() == ModBlocks.ABYSSAL_STONE.get())
                            chunk.setBlockState(current_pos, ModBlocks.CLOUPIS_ORE.get().defaultBlockState(), false);
                    }
                }
            }
        }

        final double andesite_threshold = 0.3D;
        final double andesite_scale = 0.1;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    int worldX = chunk.getPos().x * 16 + x;
                    int worldZ = chunk.getPos().z * 16 + z;

                    if (sSulfurNoise.getValue(worldX * andesite_scale, worldZ * andesite_scale, y * andesite_scale) >= andesite_threshold) {
                        BlockPos current_pos = new BlockPos(x, y, z);
                        BlockPos world_pos = new BlockPos(worldX, y, worldZ);
                        if (world.getBlockState(world_pos).getBlock() == ModBlocks.ABYSSAL_STONE.get()) {
                            chunk.setBlockState(current_pos, ModBlocks.ABYSSAL_ANDESITE.get().defaultBlockState(), false);
                        }

                        if (world.getBlockState(world_pos).getBlock() == ModBlocks.CLOUPIS_ORE.get()) {
                            chunk.setBlockState(current_pos, ModBlocks.SULFUR_ORE.get().defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void applyCarvers(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStep.Carving p_230350_5_) {}

    @Override
    public void applyBiomeDecoration(WorldGenLevel p_230351_1_, StructureManager p_230351_2_) {}

    @Override
    public void createStructures(RegistryAccess p_242707_1_, StructureManager p_242707_2_, IChunk p_242707_3_, TemplateManager p_242707_4_, long p_242707_5_) {}
}
