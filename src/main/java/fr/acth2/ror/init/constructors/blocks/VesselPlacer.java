package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class VesselPlacer extends Block {
    public VesselPlacer(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() == ModItems.REALMS_VESSEL.get()) {
            if (!world.isClientSide) {
                String dimensionId = getDimensionFromItemName(itemStack);

                if (dimensionId != null) {
                    System.out.println("Detected dimension from item name: " + dimensionId);

                    if (isPortalBuilt(world, pos)) {
                        if (activatePortal(world, pos, dimensionId, player)) {
                            player.sendMessage(new StringTextComponent("Portal activated to " + getDimensionName(dimensionId)).withStyle(TextFormatting.GREEN), player.getUUID());

                            if (!player.isCreative()) {
                                itemStack.shrink(1);
                            }

                            return ActionResultType.SUCCESS;
                        } else {
                            player.sendMessage(new StringTextComponent("Failed to activate portal").withStyle(TextFormatting.RED), player.getUUID());
                            return ActionResultType.FAIL;
                        }
                    } else {
                        player.sendMessage(new StringTextComponent("Portal structure is incomplete").withStyle(TextFormatting.YELLOW), player.getUUID());
                        return ActionResultType.FAIL;
                    }
                } else {
                    player.sendMessage(new StringTextComponent("Realm Vessel is not configured. Right-click to select a dimension first.").withStyle(TextFormatting.YELLOW), player.getUUID());
                    return ActionResultType.FAIL;
                }
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    private String getDimensionFromItemName(ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            String displayName = itemStack.getHoverName().getString();

            if (displayName.contains("Skyria")) {
                return "ror:skyria";
            } else if (displayName.contains("Overworld")) {
                return "minecraft:overworld";
            }
        }

        if (itemStack.hasTag() && itemStack.getTag().contains("SelectedDimension")) {
            return itemStack.getTag().getString("SelectedDimension");
        }

        return null;
    }

    private String getDimensionName(String dimensionId) {
        if (dimensionId.equals("minecraft:overworld")) {
            return "Overworld";
        } else if (dimensionId.equals("ror:skyria")) {
            return "Skyria";
        } else {
            return dimensionId;
        }
    }

    private void spawnActivationParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (int i = 0; i < 64; ++i) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 5.0;
            double y = pos.getY() + 0.5 + random.nextDouble() * 5.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 5.0;

            world.addParticle(ParticleTypes.PORTAL, x, y, z,
                    (random.nextDouble() - 0.5) * 2.0,
                    -random.nextDouble(),
                    (random.nextDouble() - 0.5) * 2.0);
        }
    }

    private boolean activatePortal(World world, BlockPos pos, String dimensionId, PlayerEntity player) {
        if (world.isClientSide) {
            spawnActivationParticles(world, pos);
            world.playSound(player, pos, SoundEvents.PORTAL_TRIGGER, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            ServerWorld serverWorld = (ServerWorld) world;
            createPortalBlocks(world, pos, dimensionId);

            return true;
        }
    }

    private void createPortalBlocks(World world, BlockPos centerPos, String dimensionId) {
        Block portalBlock = dimensionId.equals("ror:skyria") ?
                ModBlocks.SKYRIA_PORTAL.get() : ModBlocks.OVERWORLD_PORTAL.get();
        boolean isNorthSouth = checkNorthSouthOrientation(world, centerPos);

        System.out.println("Creating portal with orientation: " + (isNorthSouth ? "North-South" : "East-West"));

        clearExistingPortalBlocks(world, centerPos, isNorthSouth);

        if (isNorthSouth) {
            createNorthSouthPortal(world, centerPos, portalBlock);
        } else {
            createEastWestPortal(world, centerPos, portalBlock);
        }
    }

    private boolean isPortalBuilt(World world, BlockPos centerPos) {
        boolean isNorthSouth = checkNorthSouthOrientation(world, centerPos);
        boolean isEastWest = checkEastWestOrientation(world, centerPos);

        if (!isNorthSouth && !isEastWest) {
            System.out.println("Portal orientation not detected");
            return false;
        }

        System.out.println("Portal orientation: " + (isNorthSouth ? "North-South" : "East-West"));

        if (isNorthSouth) {
            return checkNorthSouthPortal(world, centerPos);
        } else {
            return checkEastWestPortal(world, centerPos);
        }
    }

    private boolean checkNorthSouthOrientation(World world, BlockPos centerPos) {
        BlockPos northPos = centerPos.north(2);
        BlockPos southPos = centerPos.south(2);

        return world.getBlockState(northPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) &&
                world.getBlockState(southPos).getBlock().equals(ModBlocks.REALM_REMNANT.get());
    }

    private boolean checkEastWestOrientation(World world, BlockPos centerPos) {
        BlockPos eastPos = centerPos.east(2);
        BlockPos westPos = centerPos.west(2);

        return world.getBlockState(eastPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) &&
                world.getBlockState(westPos).getBlock().equals(ModBlocks.REALM_REMNANT.get());
    }

    private boolean checkNorthSouthPortal(World world, BlockPos centerPos) {
        for (int z = -2; z <= 2; z++) {
            BlockPos checkPos = new BlockPos(centerPos.getX(), centerPos.getY(), centerPos.getZ() + z);
            if (z == 0) {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
                    System.out.println("Missing vessel_placer at bottom center");
                    return false;
                }
            } else {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                    System.out.println("Missing realm_remnant at bottom row, z=" + z);
                    return false;
                }
            }
        }

        for (int z = -2; z <= 2; z++) {
            BlockPos checkPos = new BlockPos(centerPos.getX(), centerPos.getY() + 4, centerPos.getZ() + z);
            if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at top row, z=" + z);
                return false;
            }
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos leftPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() - 2);
            if (!world.getBlockState(leftPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at left column, y=" + y);
                return false;
            }

            BlockPos rightPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + 2);
            if (!world.getBlockState(rightPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at right column, y=" + y);
                return false;
            }
        }

        for (int z = -1; z <= 1; z++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos checkPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + z);
                if (world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) ||
                        world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
                    System.out.println("Inner area not empty at y=" + y + ", z=" + z);
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkEastWestPortal(World world, BlockPos centerPos) {
        for (int x = -2; x <= 2; x++) {
            BlockPos checkPos = new BlockPos(centerPos.getX() + x, centerPos.getY(), centerPos.getZ());
            if (x == 0) {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
                    System.out.println("Missing vessel_placer at bottom center");
                    return false;
                }
            } else {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                    System.out.println("Missing realm_remnant at bottom row, x=" + x);
                    return false;
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            BlockPos checkPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + 4, centerPos.getZ());
            if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at top row, x=" + x);
                return false;
            }
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos leftPos = new BlockPos(centerPos.getX() - 2, centerPos.getY() + y, centerPos.getZ());
            if (!world.getBlockState(leftPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at left column, y=" + y);
                return false;
            }

            BlockPos rightPos = new BlockPos(centerPos.getX() + 2, centerPos.getY() + y, centerPos.getZ());
            if (!world.getBlockState(rightPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                System.out.println("Missing realm_remnant at right column, y=" + y);
                return false;
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos checkPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ());
                if (world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) ||
                        world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
                    System.out.println("Inner area not empty at y=" + y + ", x=" + x);
                    return false;
                }
            }
        }

        return true;
    }

    private void clearExistingPortalBlocks(World world, BlockPos centerPos, boolean isNorthSouth) {
        if (isNorthSouth) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 1; y <= 3; y++) {
                    BlockPos portalPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + z);
                    if (world.getBlockState(portalPos).getBlock() == ModBlocks.SKYRIA_PORTAL.get() ||
                            world.getBlockState(portalPos).getBlock() == ModBlocks.OVERWORLD_PORTAL.get()) {
                        world.setBlock(portalPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        } else {
            for (int x = -1; x <= 1; x++) {
                for (int y = 1; y <= 3; y++) {
                    BlockPos portalPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ());
                    if (world.getBlockState(portalPos).getBlock() == ModBlocks.SKYRIA_PORTAL.get() ||
                            world.getBlockState(portalPos).getBlock() == ModBlocks.OVERWORLD_PORTAL.get()) {
                        world.setBlock(portalPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void createNorthSouthPortal(World world, BlockPos centerPos, Block portalBlock) {
        for (int z = -1; z <= 1; z++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos portalPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + z);
                world.setBlock(portalPos, portalBlock.defaultBlockState(), 3);
            }
        }
    }

    private void createEastWestPortal(World world, BlockPos centerPos, Block portalBlock) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos portalPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ());
                world.setBlock(portalPos, portalBlock.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0, 0.1, 0.0);
        }
    }
}