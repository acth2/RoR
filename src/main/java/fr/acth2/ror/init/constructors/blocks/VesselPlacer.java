package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import fr.acth2.ror.utils.subscribers.mod.PortalScanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VesselPlacer extends Block {
    public VesselPlacer(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new VesselPlacerTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() == ModItems.REALMS_VESSEL.get()) {
            if (!world.isClientSide) {
                String dimensionId = getDimensionFromItemName(itemStack);

                if (dimensionId != null) {
                    String scanResult = PortalScanner.scan(world, pos);
                    if (scanResult.isEmpty()) {
                        if (activatePortal(world, pos, dimensionId, player)) {
                            player.sendMessage(new StringTextComponent("The realm vessel has been synced with " + getDimensionName(dimensionId)).withStyle(TextFormatting.GREEN), player.getUUID());
                            return ActionResultType.SUCCESS;
                        } else {
                            player.sendMessage(new StringTextComponent("Failed to activate portal").withStyle(TextFormatting.RED), player.getUUID());
                            return ActionResultType.FAIL;
                        }
                    } else {
                        player.sendMessage(new StringTextComponent(scanResult).withStyle(TextFormatting.RED), player.getUUID());
                        return ActionResultType.FAIL;
                    }
                } else {
                    player.sendMessage(new StringTextComponent("The realm vessel dont have any instruction..").withStyle(TextFormatting.YELLOW), player.getUUID());
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
            if (displayName.contains("Skyria")) return "ror:skyria";
            if (displayName.contains("Overworld")) return "minecraft:overworld";
        }
        if (itemStack.hasTag() && itemStack.getTag().contains("SelectedDimension")) {
            return itemStack.getTag().getString("SelectedDimension");
        }
        return null;
    }

    private String getDimensionName(String dimensionId) {
        if (dimensionId.equals("minecraft:overworld")) return "Overworld";
        if (dimensionId.equals("ror:skyria")) return "Skyria";
        return dimensionId;
    }

    private boolean activatePortal(World world, BlockPos pos, String dimensionId, PlayerEntity player) {
        if (!world.isClientSide) {
            createPortalBlocks(world, pos, dimensionId);
        }
        world.playSound(player, pos, SoundEvents.PORTAL_TRIGGER, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    private void createPortalBlocks(World world, BlockPos centerPos, String dimensionId) {
        Block portalBlock = dimensionId.equals("ror:skyria") ?
                ModBlocks.SKYRIA_PORTAL.get() : ModBlocks.OVERWORLD_PORTAL.get();
        boolean isNorthSouth = checkNorthSouthOrientation(world, centerPos);

        clearExistingPortalBlocks(world, centerPos, isNorthSouth);

        if (isNorthSouth) {
            createNorthSouthPortal(world, centerPos, portalBlock);
        } else {
            createEastWestPortal(world, centerPos, portalBlock);
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
                    return false;
                }
            } else {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                    return false;
                }
            }
        }

        for (int z = -2; z <= 2; z++) {
            BlockPos checkPos = new BlockPos(centerPos.getX(), centerPos.getY() + 4, centerPos.getZ() + z);
            if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos leftPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() - 2);
            if (!world.getBlockState(leftPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }

            BlockPos rightPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + 2);
            if (!world.getBlockState(rightPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }
        }

        for (int z = -1; z <= 1; z++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos checkPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + z);
                if (world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) ||
                        world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
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
                    return false;
                }
            } else {
                if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                    return false;
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            BlockPos checkPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + 4, centerPos.getZ());
            if (!world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }
        }

        for (int y = 1; y <= 3; y++) {
            BlockPos leftPos = new BlockPos(centerPos.getX() - 2, centerPos.getY() + y, centerPos.getZ());
            if (!world.getBlockState(leftPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }

            BlockPos rightPos = new BlockPos(centerPos.getX() + 2, centerPos.getY() + y, centerPos.getZ());
            if (!world.getBlockState(rightPos).getBlock().equals(ModBlocks.REALM_REMNANT.get())) {
                return false;
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos checkPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ());
                if (world.getBlockState(checkPos).getBlock().equals(ModBlocks.REALM_REMNANT.get()) ||
                        world.getBlockState(checkPos).getBlock().equals(ModBlocks.VESSEL_PLACER.get())) {
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
        BlockState portalState = portalBlock.defaultBlockState();

        if (portalState.hasProperty(NetherPortalBlock.AXIS)) {
            portalState = portalState.setValue(NetherPortalBlock.AXIS, Direction.Axis.Z);
        }
        else if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z);
        }
        else if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
        }
        else {
        }

        for (int z = -1; z <= 1; z++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos portalPos = new BlockPos(centerPos.getX(), centerPos.getY() + y, centerPos.getZ() + z);
                world.setBlock(portalPos, portalState, 3);
            }
        }
    }

    private void createEastWestPortal(World world, BlockPos centerPos, Block portalBlock) {
        BlockState portalState = portalBlock.defaultBlockState();

        if (portalState.hasProperty(NetherPortalBlock.AXIS)) {
            portalState = portalState.setValue(NetherPortalBlock.AXIS, Direction.Axis.X);
            System.out.println("Set AXIS to X for east-west portal");
        }
        else if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);
            System.out.println("Set HORIZONTAL_AXIS to X for east-west portal");
        }
        else if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST);
            System.out.println("Set HORIZONTAL_FACING to EAST for east-west portal");
        }
        else {
            System.out.println("No rotation property found for east-west portal");
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                BlockPos portalPos = new BlockPos(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ());
                world.setBlock(portalPos, portalState, 3);
            }
        }
    }
}