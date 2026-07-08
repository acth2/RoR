package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.api.Services;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.VesselPlacerSkinBlock;
import fr.acth2.ror.init.constructors.blocks.VesselPlacer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class VesselPlacerTileEntity extends TileEntity implements ITickableTileEntity {

    public VesselPlacerTileEntity() {
        super(ModTileEntities.VESSEL_PLACER_TILE_ENTITY.get());
    }

    @SuppressWarnings("unchecked")
    public VesselPlacerTileEntity(Object pos, Object state) {
        super(ModTileEntities.VESSEL_PLACER_TILE_ENTITY.get());
    }

    public static void doTick(Object teObj) {
        VesselPlacerTileEntity te = (VesselPlacerTileEntity) teObj;

        // client-side only
        if (te.getLevel() == null || !te.getLevel().isClientSide()) return;

        BlockState blockState = te.getBlockState();
        Block block = blockState.getBlock();

        float r = 0, g = 0, b = 0;
        boolean shouldAnimate = false;

        if (block instanceof VesselPlacer) {
            Block portalBlock = getActivePortalBlock(te.getLevel(), te.getBlockPos());
            if (portalBlock != null) {
                shouldAnimate = true;
                if (portalBlock == ModBlocks.OVERWORLD_PORTAL.get()) {
                    r = 0.1f; g = 0.8f; b = 0.1f;
                } else {
                    r = 0.1f; g = 0.1f; b = 0.9f;
                }
            }
        } else if (block instanceof VesselPlacerSkinBlock) {
            shouldAnimate = true;
            r = 0.1f; g = 0.8f; b = 0.1f;
        } else {
            return;
        }

        if (!shouldAnimate) return;

        long time = te.getLevel().getGameTime();
        double centerX = te.getBlockPos().getX() + 0.5D;
        double centerY = te.getBlockPos().getY() + 0.5D;
        double centerZ = te.getBlockPos().getZ() + 0.5D;

        int numStreams = 16;
        float radius = 4.5f;

        for (int i = 0; i < numStreams; i++) {
            double angle = (i * (360.0 / numStreams) + (time * 1.5)) % 360.0;
            double angleRad = Math.toRadians(angle);
            double yAngle = (time * 3.0 + i * 25) % 360;
            double yOffset = Math.sin(Math.toRadians(yAngle)) * 2.0;
            double px = centerX + Math.cos(angleRad) * radius;
            double pz = centerZ + Math.sin(angleRad) * radius;
            double py = centerY + yOffset;
            double motionX = (centerX - px) * 0.1D;
            double motionY = (centerY - py) * 0.1D;
            double motionZ = (centerZ - pz) * 0.1D;

            Services.PARTICLES.addColoredParticleWithMotion(
                    te.getLevel(),
                    px, py + 2, pz,
                    motionX, motionY + 2, motionZ,
                    r, g, b, 1.0f
            );
        }

        if (time % 20 == 0) {
            Services.SOUNDS.playBeaconAmbient(
                    te.getLevel(),
                    te.getBlockPos().getX(),
                    te.getBlockPos().getY(),
                    te.getBlockPos().getZ(),
                    0.2F, 1.5F
            );
        }
    }

    private static Block getActivePortalBlock(Object worldObj, Object posObj) {
        net.minecraft.world.level.Level world = (net.minecraft.world.level.Level) worldObj;
        BlockPos pos = (BlockPos) posObj;
        Block above = world.getBlockState(pos.above()).getBlock();
        if (above == ModBlocks.SKYRIA_PORTAL.get() || above == ModBlocks.OVERWORLD_PORTAL.get()) {
            return above;
        }
        return null;
    }

    @Override
    public void tick() {
        doTick(this);
    }
}