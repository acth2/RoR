package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PortalScanner {

    public static class ScanResult {
        public final boolean success;
        @Nullable
        public final Direction.Axis axis;
        public final String error;

        private ScanResult(boolean success, @Nullable Direction.Axis axis, String error) {
            this.success = success;
            this.axis = axis;
            this.error = error;
        }

        public static ScanResult success(Direction.Axis axis) {
            return new ScanResult(true, axis, "");
        }

        public static ScanResult failure(String error) {
            return new ScanResult(false, null, error);
        }
    }

    public static ScanResult scan(World world, BlockPos vesselPos) {
        ScanResult nsResult = scanNorthSouth(world, vesselPos);
        if (nsResult.success) {
            return nsResult;
        }

        ScanResult ewResult = scanEastWest(world, vesselPos);
        if (ewResult.success) {
            return ewResult;
        }

        return ScanResult.failure(nsResult.error);
    }

    private static ScanResult scanNorthSouth(World world, BlockPos vesselPos) {
        return checkStructure(world, vesselPos, 2, 4, Direction.Axis.Z);
    }

    private static ScanResult scanEastWest(World world, BlockPos vesselPos) {
        return checkStructure(world, vesselPos, 4, 2, Direction.Axis.X);
    }

    private static ScanResult checkStructure(World world, BlockPos vesselPos, int xPillarOffset, int zPillarOffset, Direction.Axis portalAxis) {
        Block remnant = ModBlocks.REALM_REMNANT.get();
        Block powerContainer = ModBlocks.POWER_CONTAINER.get();
        BlockPos baseCenter = vesselPos.below();

        // Base Ring
        for (int i = -2; i <= 2; i++) {
            BlockPos hPos = baseCenter.offset(i, 0, 0);
            if (!world.getBlockState(hPos).is(remnant)) return ScanResult.failure("Missing an element at " + hPos.getX() + ", " + hPos.getY() + ", " + hPos.getZ());
            BlockPos vPos = baseCenter.offset(0, 0, i);
            if (!world.getBlockState(vPos).is(remnant)) return ScanResult.failure("Missing an element at " + vPos.getX() + ", " + vPos.getY() + ", " + vPos.getZ());
        }

        BlockPos[] pillarBases = {
            vesselPos.offset(-xPillarOffset, -1, -zPillarOffset),
            vesselPos.offset(xPillarOffset, -1, -zPillarOffset),
            vesselPos.offset(-xPillarOffset, -1, zPillarOffset),
            vesselPos.offset(xPillarOffset, -1, zPillarOffset)
        };

        for (BlockPos pBase : pillarBases) {
            for (int y = 0; y < 3; y++) {
                BlockPos pillarPos = pBase.above(y);
                if (!world.getBlockState(pillarPos).is(remnant)) return ScanResult.failure("Missing an element at "  + pillarPos.getX() + ", " + pillarPos.getY() + ", " + pillarPos.getZ());
            }
            BlockPos powerPos = pBase.above(3);
            if (!world.getBlockState(powerPos).is(powerContainer)) return ScanResult.failure("Missing an element at " + powerPos.getX() + ", " + powerPos.getY() + ", " + powerPos.getZ());
        }

        return ScanResult.success(portalAxis);
    }
}