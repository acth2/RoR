package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import fr.acth2.ror.utils.subscribers.mod.PortalScanner;
import net.minecraft.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

public class VesselPlacer extends Block {


    public static AbstractBlock.Properties defaultProperties() {
        return ((AbstractBlock.Properties) Props.stone())
                .strength(1.5f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .requiresCorrectToolForDrops();
    }

    public VesselPlacer() {
        super(defaultProperties());
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
                    PortalScanner.ScanResult scanResult = PortalScanner.scan(world, pos);
                    if (scanResult.success) {
                        if (isFrameBuilt(world, pos)) {
                            createPortalBlocks(world, pos, dimensionId, scanResult.axis);
                            player.sendMessage(new StringTextComponent("The realm vessel has been synced with " + getDimensionName(dimensionId)).withStyle(TextFormatting.GREEN), player.getUUID());
                            world.playSound(null, pos, ModSoundEvents.PORTAL_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        } else {
                            player.sendMessage(new StringTextComponent("The vessel ask for a frame").withStyle(TextFormatting.RED), player.getUUID());
                            return ActionResultType.FAIL;
                        }
                    } else {
                        player.sendMessage(new StringTextComponent(scanResult.error).withStyle(TextFormatting.RED), player.getUUID());
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

    private boolean isFrameBuilt(World world, BlockPos centerPos) {
        boolean isNorthSouth = checkNorthSouthOrientation(world, centerPos);
        boolean isEastWest = checkEastWestOrientation(world, centerPos);

        if (!isNorthSouth && !isEastWest) {
            return false;
        }

        if (isNorthSouth) {
            return checkNorthSouthPortal(world, centerPos);
        } else {
            return checkEastWestPortal(world, centerPos);
        }
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

    private void createPortalBlocks(World world, BlockPos centerPos, String dimensionId, Direction.Axis axis) {
        Block portalBlock = dimensionId.equals("ror:skyria") ? ModBlocks.SKYRIA_PORTAL.get() : ModBlocks.OVERWORLD_PORTAL.get();
        BlockState portalState = portalBlock.defaultBlockState().setValue(NetherPortalBlock.AXIS, axis);

        for (int y = 1; y <= 3; y++) {
            if (axis == Direction.Axis.X) {
                for (int x = -1; x <= 1; x++) {
                    world.setBlock(centerPos.offset(x, y, 0), portalState, 3);
                }
            } else { // axis == Direction.Axis.Z
                for (int z = -1; z <= 1; z++) {
                    world.setBlock(centerPos.offset(0, y, z), portalState, 3);
                }
            }
        }
        world.sendBlockUpdated(centerPos, world.getBlockState(centerPos), world.getBlockState(centerPos), 3);
    }
}