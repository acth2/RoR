package fr.acth2.ror.structures;

import com.mojang.serialization.Codec;
import fr.acth2.ror.structures.pieces.ExamplePiece;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class RealmRemnant  extends Structure<NoFeatureConfig> {
    public RealmRemnant(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return RealmRemnant.Start::new;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        //return chunkRandom.nextInt(100) < 5;
        return chunkRandom.nextInt(2) == 0; //for test purposes
    }


    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ,
                     MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator,
                                   TemplateManager templateManager, int chunkX, int chunkZ,
                                   Biome biome, NoFeatureConfig noFeatureConfig) {
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;
            int y = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);

            int width = 5;
            int height = 10;
            MutableBoundingBox boundingBox = new MutableBoundingBox(
                    x - width/2, y, z - width/2,
                    x + width/2, y + height, z + width/2
            );

            this.pieces.add(new ExamplePiece(templateManager, new BlockPos(x, y, z), boundingBox));
            this.calculateBoundingBox();
        }
    }
}