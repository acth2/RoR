package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class PortalBuilder {

    public static void findOrCreatePortal(ServerWorld world, BlockPos searchCenter) {
        Optional<BlockPos> existingPortal = BlockPos.findClosestMatch(searchCenter, 16, 16,
                (pos) -> world.getBlockState(pos).is(ModBlocks.VESSEL_PLACER.get()));

        if (!existingPortal.isPresent()) {
            build(world, new BlockPos(searchCenter.getX(), 110, searchCenter.getZ()));
        }
    }

    private static void build(ServerWorld world, BlockPos vesselPos) {
        Block remnant;
        Block powerContainer;
        Block vesselPlacer = ModBlocks.VESSEL_PLACER.get();
        Block portalBlock = ModBlocks.OVERWORLD_PORTAL.get();

        if (world.dimension().equals(ModDimensions.SKYRIA)) {
            remnant = ModBlocks.SKYRIA_BRICK.get();
            powerContainer = ModBlocks.POLISHED_SKYRIA_BRICK.get();
        } else {
            remnant = ModBlocks.REALM_REMNANT.get();
            powerContainer = ModBlocks.POWER_CONTAINER.get();
        }
        BlockState remnantState = remnant.defaultBlockState();
        BlockState powerState = powerContainer.defaultBlockState();
        BlockState vesselState = vesselPlacer.defaultBlockState();

        for (int y = -2; y <= 7; y++) {
            for (int x = -6; x <= 6; x++) {
                for (int z = -6; z <= 6; z++) {
                    world.setBlock(vesselPos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        world.setBlock(vesselPos, vesselState, 3);

        BlockPos baseCenter = vesselPos.below();
        for (int i = -2; i <= 2; i++) {
            world.setBlock(baseCenter.offset(i, 0, 0), remnantState, 3);
            world.setBlock(baseCenter.offset(0, 0, i), remnantState, 3);
        }

        int xOffset = 2;
        int zOffset = 4;
        BlockPos[] pillarBases = {
            vesselPos.offset(-xOffset, -1, -zOffset), vesselPos.offset(xOffset, -1, -zOffset),
            vesselPos.offset(-xOffset, -1, zOffset), vesselPos.offset(xOffset, -1, zOffset)
        };
        for (BlockPos pBase : pillarBases) {
            for (int y = 0; y < 3; y++) {
                world.setBlock(pBase.above(y), remnantState, 3);
            }
            world.setBlock(pBase.above(3), powerState, 3);
        }

        BlockState portalState = portalBlock.defaultBlockState().setValue(NetherPortalBlock.AXIS, Direction.Axis.Z);
        for (int z = -1; z <= 1; z++) {
            for (int y = 1; y <= 3; y++) {
                world.setBlock(vesselPos.offset(0, y, z), portalState, 3);
            }
        }
        
        world.sendBlockUpdated(vesselPos, vesselState, vesselState, 3);
    }
}