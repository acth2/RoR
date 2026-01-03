package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortalScanner {

    public static String scan(World world, BlockPos vesselPos) {
        String nsResult = scanNorthSouth(world, vesselPos);
        if (nsResult.isEmpty()) {
            return "";
        }

        String ewResult = scanEastWest(world, vesselPos);
        if (ewResult.isEmpty()) {
            return "";
        }

        return nsResult;
    }

    private static String scanNorthSouth(World world, BlockPos vesselPos) {
        return checkStructure(world, vesselPos, 2, 4);
    }

    private static String scanEastWest(World world, BlockPos vesselPos) {
        return checkStructure(world, vesselPos, 4, 2);
    }

    private static String checkStructure(World world, BlockPos vesselPos, int xOffset, int zOffset) {
        Block remnant = ModBlocks.REALM_REMNANT.get();
        Block powerContainer = ModBlocks.POWER_CONTAINER.get();
        BlockPos baseCenter = vesselPos.below();

        for (int i = -2; i <= 2; i++) {
            BlockPos hPos = baseCenter.offset(i, 0, 0);
            if (!world.getBlockState(hPos).is(remnant)) return "Missing realm_remnant at " + hPos;
            BlockPos vPos = baseCenter.offset(0, 0, i);
            if (!world.getBlockState(vPos).is(remnant)) return "Missing realm_remnant at " + vPos;
        }

        BlockPos[] pillarBases = {
            vesselPos.offset(-xOffset, -1, -zOffset),
            vesselPos.offset(xOffset, -1, -zOffset),
            vesselPos.offset(-xOffset, -1, zOffset),
            vesselPos.offset(xOffset, -1, zOffset)
        };

        for (BlockPos pBase : pillarBases) {
            for (int y = 0; y < 3; y++) {
                BlockPos pillarPos = pBase.above(y);
                if (!world.getBlockState(pillarPos).is(remnant)) {
                    return "The realm vessel request a remnant at" + pillarPos.getX() + ", " + pillarPos.getY() + ", " + pillarPos.getZ();
                }
            }
            BlockPos powerPos = pBase.above(3);
            if (!world.getBlockState(powerPos).is(powerContainer)) {
                return "The realm vessel request power at" + powerPos.getX() + ", " + powerPos.getY() + ", " + powerPos.getZ();
            }
        }

        return "";
    }
}