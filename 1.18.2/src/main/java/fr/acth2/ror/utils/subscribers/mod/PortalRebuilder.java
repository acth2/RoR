package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PortalRebuilder {

    public static void rebuild(World world, BlockPos vesselPos) {
        // Attempt to rebuild both orientations. The logic is smart enough not to place blocks where they already exist.
        rebuildNorthSouth(world, vesselPos);
        rebuildEastWest(world, vesselPos);
    }

    private static void rebuildNorthSouth(World world, BlockPos vesselPos) {
        // In N-S, the long side is along the Z-axis.
        checkAndPlaceStructure(world, vesselPos, 2, 4);
    }

    private static void rebuildEastWest(World world, BlockPos vesselPos) {
        // In E-W, the long side is along the X-axis.
        checkAndPlaceStructure(world, vesselPos, 4, 2);
    }

    private static void checkAndPlaceStructure(World world, BlockPos vesselPos, int xOffset, int zOffset) {
        Block remnant = ModBlocks.REALM_REMNANT.get();
        BlockState remnantState = remnant.defaultBlockState();
        Block powerContainer = ModBlocks.POWER_CONTAINER.get();
        BlockState powerState = powerContainer.defaultBlockState();
        BlockPos baseCenter = vesselPos.below();

        // --- Base Ring ---
        for (int i = -2; i <= 2; i++) {
            checkAndPlace(world, baseCenter.offset(i, 0, 0), remnantState);
            checkAndPlace(world, baseCenter.offset(0, 0, i), remnantState);
        }

        // --- Pillars and Power Containers ---
        BlockPos[] pillarBases = {
            vesselPos.offset(-xOffset, -1, -zOffset),
            vesselPos.offset(xOffset, -1, -zOffset),
            vesselPos.offset(-xOffset, -1, zOffset),
            vesselPos.offset(xOffset, -1, zOffset)
        };

        for (BlockPos pBase : pillarBases) {
            for (int y = 0; y < 3; y++) {
                checkAndPlace(world, pBase.above(y), remnantState);
            }
            checkAndPlace(world, pBase.above(3), powerState);
        }
    }

    private static void checkAndPlace(World world, BlockPos pos, BlockState targetState) {
        if (!world.getBlockState(pos).equals(targetState)) {
            world.setBlock(pos, targetState, 3);
        }
    }
}