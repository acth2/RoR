package fr.acth2.ror.utils.subscribers.gen.utils;

import fr.acth2.ror.utils.subscribers.gen.utils.parser.StructureParser;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class Structure {
    private final ResourceLocation structureLocation;
    private final int minY;
    private final int maxY;
    private final List<String> allowedBiomes;
    private final int rarity;

    public Structure(ResourceLocation structureLocation, int minY, int maxY, List<String> allowedBiomes, int rarity) {
        this.structureLocation = structureLocation;
        this.minY = minY;
        this.maxY = maxY;
        this.allowedBiomes = allowedBiomes;
        this.rarity = rarity;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getRarity() {
        return rarity;
    }

    public boolean generate(ISeedReader world, TemplateManager templateManager, Random random, BlockPos pos) {
        String biomeName = world.getBiome(pos).getRegistryName().toString();
        if (!allowedBiomes.contains(biomeName)) {
            return false;
        }

        BlockPos.Mutable mutablePos = new BlockPos.Mutable(pos.getX(), world.getMaxBuildHeight(), pos.getZ());
        while (mutablePos.getY() > 0) {
            BlockState state = world.getBlockState(mutablePos);

            if (state.getMaterial().isSolid() &&
                    !state.is(BlockTags.LEAVES) &&
                    !state.is(BlockTags.LOGS)) {
                break;
            }
            mutablePos.move(0, -1, 0);
        }

        int surfaceY = Math.min(Math.max(mutablePos.getY(), minY), maxY);
        BlockPos surfacePos = new BlockPos(pos.getX(), surfaceY, pos.getZ());

        if (!world.getBlockState(surfacePos.below()).getMaterial().isSolid()) {
            return false;
        }

        world.setBlock(surfacePos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 2);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.setBlock(surfacePos.offset(x, 0, z), Blocks.COBBLESTONE.defaultBlockState(), 2);
            }
        }

        return true;
    }

    private void generateCylinder(ISeedReader world, BlockPos center, StructureParser.StructureDefinition.BlockEntry entry) {
        for (int y = 0; y < entry.height; y++) {
            for (int x = -entry.radius; x <= entry.radius; x++) {
                for (int z = -entry.radius; z <= entry.radius; z++) {
                    if (x*x + z*z <= entry.radius*entry.radius) {
                        BlockPos pos = center.offset(x, y, z);
                        BlockState state = ForgeRegistries.BLOCKS.getValue(
                                new ResourceLocation(entry.block)
                        ).defaultBlockState();

                        if (entry.layers != null && y == entry.height-1 && entry.layers.top != null) {
                            state = ForgeRegistries.BLOCKS.getValue(
                                    new ResourceLocation(entry.layers.top)
                            ).defaultBlockState();
                        }

                        world.setBlock(pos, state, 2);
                    }
                }
            }
        }
    }
}