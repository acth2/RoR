package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import fr.acth2.ror.utils.subscribers.mod.PortalScanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
                    PortalScanner.ScanResult scanResult = PortalScanner.scan(world, pos);
                    if (scanResult.success) {
                        createPortalBlocks(world, pos, dimensionId, scanResult.axis);
                        
                        player.sendMessage(new StringTextComponent("The realm vessel has been synced with " + getDimensionName(dimensionId)).withStyle(TextFormatting.GREEN), player.getUUID());
                        world.playSound(null, pos, ModSoundEvents.PORTAL_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return ActionResultType.SUCCESS;
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