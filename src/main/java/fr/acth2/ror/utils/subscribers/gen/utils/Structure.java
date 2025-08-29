package fr.acth2.ror.utils.subscribers.gen.utils;

import fr.acth2.ror.utils.subscribers.gen.utils.parser.StructureParser;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
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

    public ResourceLocation getStructureLocation() {return structureLocation; }

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
        System.out.println("=== STRUCTURE GENERATION START ===");
        System.out.println("Structure: " + structureLocation);
        System.out.println("Position: " + pos);

        boolean isSkyria = world.getLevel().dimension().location().toString().equals("ror:skyria");
        System.out.println("Is Skyria dimension: " + isSkyria);

        BlockPos surfacePos;

        if (!isSkyria) {
            String biomeName = world.getBiome(pos).getRegistryName().toString();
            System.out.println("Biome: " + biomeName);
            System.out.println("Biome in allowed list: " + allowedBiomes.contains(biomeName));

            if (!allowedBiomes.contains(biomeName)) {
                System.out.println("=== STRUCTURE GENERATION FAILED: Wrong biome ===");
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

            surfacePos = new BlockPos(pos.getX(), Math.min(Math.max(mutablePos.getY(), minY), maxY), pos.getZ());

            if (!world.getBlockState(surfacePos.below()).getMaterial().isSolid()) {
                System.out.println("=== STRUCTURE GENERATION FAILED: No solid ground below ===");
                return false;
            }
        } else {
            System.out.println("Y level: " + pos.getY() + " (minY: " + minY + ", maxY: " + maxY + ")");

            if (pos.getY() < minY) {
                System.out.println("=== STRUCTURE GENERATION FAILED: Y too low ===");
                return false;
            }

            BlockPos belowPos = pos.below();
            BlockPos abovePos = pos.above();

            boolean hasCloudBelow = isCloudBlock(world, belowPos);
            boolean hasAirAtPos = world.isEmptyBlock(pos);
            boolean hasAirAbove = world.isEmptyBlock(abovePos);

            System.out.println("Cloud below: " + hasCloudBelow + " at " + belowPos);
            System.out.println("Air at position: " + hasAirAtPos);
            System.out.println("Air above: " + hasAirAbove + " at " + abovePos);

            if (!hasCloudBelow || !hasAirAtPos || !hasAirAbove) {
                System.out.println("=== STRUCTURE GENERATION FAILED: Invalid position conditions ===");
                return false;
            }

            surfacePos = pos;
        }

        try {
            System.out.println("Parsing structure definition...");
            StructureParser.StructureDefinition def = StructureParser.parse(
                    world.getLevel().getServer().getDataPackRegistries().getResourceManager(),
                    structureLocation
            );
            System.out.println("Structure parsed successfully");

            if (def.blocks != null && def.blocks.length > 0) {
                for (StructureParser.StructureDefinition.BlockEntry entry : def.blocks) {
                    if (entry.width > 50 || entry.length > 50 || entry.height > 100) {
                        System.out.println("WARNING: Structure is very large - may cause performance issues");
                    }
                }

                BlockPos centerPos = surfacePos.offset(-def.blocks[0].radius, 0, -def.blocks[0].radius);
                System.out.println("Center position: " + centerPos);

                for (StructureParser.StructureDefinition.BlockEntry entry : def.blocks) {
                    if ("cylinder".equals(entry.shape)) {
                        System.out.println("Generating cylinder...");
                        generateCylinder(world, centerPos, entry);
                    } else if ("hollow_box".equals(entry.shape)) {
                        System.out.println("Generating hollow box...");
                        generateHollowBox(world, centerPos, entry);
                    }
                }
            } else {
                System.out.println("=== STRUCTURE GENERATION FAILED: No blocks defined in structure ===");
                return false;
            }

            System.out.println("=== STRUCTURE GENERATION SUCCESS ===");
            return true;

        } catch (Exception e) {
            System.err.println("=== STRUCTURE GENERATION FAILED: Exception ===");
            System.err.println("Failed to generate structure " + structureLocation + ": ");
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCloudBlock(ISeedReader world, BlockPos pos) {
        String blockName = world.getBlockState(pos).getBlock().getRegistryName().toString();
        return blockName.equals("ror:cloud_piece");
    }

    private void generateCylinder(ISeedReader world, BlockPos center, StructureParser.StructureDefinition.BlockEntry entry) {
        world.getLevel().getServer().execute(() -> {
            try {
                for (int y = 0; y < entry.height; y++) {
                    for (int x = -entry.radius; x <= entry.radius; x++) {
                        for (int z = -entry.radius; z <= entry.radius; z++) {
                            if (x*x + z*z <= entry.radius*entry.radius) {
                                BlockPos pos = center.offset(x, y, z);
                                BlockState state = getBlockState(entry.block);

                                if (entry.layers != null) {
                                    if (y == entry.height-1 && entry.layers.top != null) {
                                        state = getBlockState(entry.layers.top);
                                    }
                                    else if (y == 0 && entry.layers.bottom != null) {
                                        state = getBlockState(entry.layers.bottom);
                                    }
                                }

                                world.setBlock(pos, state, 2);
                            }
                        }
                    }

                    if (y % 3 == 0) {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error generating cylinder: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void generateHollowBox(ISeedReader world, BlockPos center, StructureParser.StructureDefinition.BlockEntry entry) {
        if (entry.hollow_box == null) {
            System.err.println("Missing hollow_box configuration for structure");
            return;
        }

        StructureParser.StructureDefinition.HollowBoxEntry hollowConfig = entry.hollow_box;
        StructureParser.StructureDefinition.EntranceEntry entranceConfig = entry.entrance;
        StructureParser.StructureDefinition.PlatformEntry platformConfig = entry.platform;
        int width = entry.width;
        int height = entry.height;
        int length = entry.length;

        System.out.println("Generating hollow box: " + width + "x" + height + "x" + length);

        BlockState cornerBlock = getBlockState(hollowConfig.corner_block);
        BlockState edgeBlock = getBlockState(hollowConfig.edge_block);
        BlockState faceBlock = getBlockState(hollowConfig.face_block);
        BlockState interiorBlock = getBlockState(hollowConfig.interior_block);
        BlockState platformBlock = getBlockState(platformConfig != null ? platformConfig.block : "minecraft:stone");
        BlockState railingBlock = getBlockState(platformConfig != null && platformConfig.railing ? platformConfig.railing_block : "minecraft:stone_wall");
        BlockState airBlock = getBlockState("minecraft:air");

        long startTime = System.currentTimeMillis();
        int blocksPlaced = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < length; z++) {
                    BlockPos pos = center.offset(x - width/2, y, z - length/2);

                    boolean shouldMakeAirRoof = hollowConfig.air_roof && y == height - 1;
                    boolean hasPlatform = platformConfig != null && platformConfig.enabled;

                    if (shouldMakeAirRoof && !hasPlatform) {
                        world.setBlock(pos, airBlock, 2);
                        blocksPlaced++;
                        continue;
                    }

                    if (entranceConfig != null && isEntrancePosition(x, y, z, width, height, length, entranceConfig)) {
                        world.setBlock(pos, airBlock, 2);
                        blocksPlaced++;
                        continue;
                    }

                    BlockState stateToPlace = determineHollowBoxBlock(x, y, z, width, height, length,
                            cornerBlock, edgeBlock, faceBlock, interiorBlock);

                    world.setBlock(pos, stateToPlace, 2);
                    blocksPlaced++;

                    if (blocksPlaced % 64 == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }

            if (y % 5 == 0) {
                System.out.println("Generated layer " + y + "/" + height);
            }
        }

        if (platformConfig != null && platformConfig.enabled) {
            generatePlatform(world, center, width, height, length, platformConfig,
                    platformBlock, railingBlock, cornerBlock, edgeBlock, faceBlock);
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Hollow box generation completed: " + blocksPlaced + " blocks in " + duration + "ms");
    }

    private void generatePlatform(ISeedReader world, BlockPos center, int width, int height, int length,
                                  StructureParser.StructureDefinition.PlatformEntry platformConfig, BlockState platformBlock, BlockState railingBlock,
                                  BlockState cornerBlock, BlockState edgeBlock, BlockState faceBlock) {

        System.out.println("Generating platform...");
        int platformY = height - 1;
        int platformThickness = platformConfig.thickness;
        BlockState airBlock = getBlockState("minecraft:air");
        int blocksPlaced = 0;

        for (int layer = 0; layer < platformThickness; layer++) {
            int currentY = platformY + layer;

            for (int x = -1; x <= width; x++) {
                for (int z = -1; z <= length; z++) {
                    BlockPos pos = center.offset(x - width/2, currentY, z - length/2);

                    boolean isCenter = (x == width/2 && z == length/2);
                    if (isCenter && layer == 0) {
                        world.setBlock(pos, airBlock, 2);
                        blocksPlaced++;
                        continue;
                    }

                    if (!platformConfig.extend_walls && (x >= 0 && x < width && z >= 0 && z < length)) {
                        continue;
                    }

                    boolean isCorner = (x == -1 || x == width) && (z == -1 || z == length);
                    boolean isEdge = (x == -1 || x == width || z == -1 || z == length);

                    if (layer == platformThickness - 1 && platformConfig.railing && isEdge) {
                        if (isCorner) {
                            world.setBlock(pos, cornerBlock, 2);
                        } else {
                            world.setBlock(pos, railingBlock, 2);
                        }
                    } else {
                        world.setBlock(pos, platformBlock, 2);
                    }
                    blocksPlaced++;

                    if (blocksPlaced % 64 == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }
        }

        if (platformConfig.extend_walls) {
            for (int layer = 0; layer < platformThickness; layer++) {
                int currentY = platformY + layer;

                for (int x = 0; x < width; x++) {
                    for (int z = 0; z < length; z++) {
                        boolean isCornerX = (x == 0 || x == width - 1);
                        boolean isCornerZ = (z == 0 || z == length - 1);

                        boolean isCenter = (x == width/2 && z == length/2);
                        if (isCenter && layer == 0) {
                            continue;
                        }

                        if (isCornerX && isCornerZ) {
                            BlockPos pos = center.offset(x - width/2, currentY, z - length/2);
                            world.setBlock(pos, cornerBlock, 2);
                        } else if (isCornerX || isCornerZ) {
                            BlockPos pos = center.offset(x - width/2, currentY, z - length/2);
                            world.setBlock(pos, edgeBlock, 2);
                        }
                        blocksPlaced++;

                        if (blocksPlaced % 64 == 0) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Platform generation completed: " + blocksPlaced + " blocks");
    }

    private boolean isEntrancePosition(int x, int y, int z, int width, int height, int length, StructureParser.StructureDefinition.EntranceEntry entranceConfig) {
        int entranceWidth = entranceConfig.width;
        int entranceHeight = entranceConfig.height;
        String wall = entranceConfig.wall.toLowerCase();

        boolean isInEntranceHeight = y < entranceHeight;
        boolean isInEntranceWidth = false;

        switch (wall) {
            case "north":
                isInEntranceWidth = (z == 0) && (x >= (width - entranceWidth) / 2 && x < (width + entranceWidth) / 2);
                break;
            case "south":
                isInEntranceWidth = (z == length - 1) && (x >= (width - entranceWidth) / 2 && x < (width + entranceWidth) / 2);
                break;
            case "east":
                isInEntranceWidth = (x == width - 1) && (z >= (length - entranceWidth) / 2 && z < (length + entranceWidth) / 2);
                break;
            case "west":
                isInEntranceWidth = (x == 0) && (z >= (length - entranceWidth) / 2 && z < (length + entranceWidth) / 2);
                break;
        }

        return isInEntranceHeight && isInEntranceWidth;
    }


    private BlockState determineHollowBoxBlock(int x, int y, int z, int width, int height, int length,
                                               BlockState cornerBlock, BlockState edgeBlock,
                                               BlockState faceBlock, BlockState interiorBlock) {
        boolean isCornerX = (x == 0 || x == width - 1);
        boolean isCornerZ = (z == 0 || z == length - 1);
        boolean isCornerY = (y == 0 || y == height - 1);

        if (isCornerX && isCornerZ && isCornerY) {
            return cornerBlock;
        }

        if ((isCornerX && isCornerZ) || (isCornerX && isCornerY) || (isCornerZ && isCornerY)) {
            return edgeBlock;
        }

        if (isCornerX || isCornerZ || isCornerY) {
            return faceBlock;
        }

        return interiorBlock;
    }


    private BlockState getBlockState(String blockId) {
        if (blockId == null) {
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:air")).defaultBlockState();
        }

        try {
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId)).defaultBlockState();
        } catch (Exception e) {
            System.err.println("Failed to get block state for: " + blockId);
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:air")).defaultBlockState();
        }
    }
}