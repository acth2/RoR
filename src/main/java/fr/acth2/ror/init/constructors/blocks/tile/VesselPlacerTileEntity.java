package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.VesselPlacerSkinBlock;
import fr.acth2.ror.init.constructors.blocks.VesselPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class VesselPlacerTileEntity extends TileEntity implements ITickableTileEntity {

    public VesselPlacerTileEntity() {
        super(ModTileEntities.VESSEL_PLACER_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (level == null || !level.isClientSide) {
            return;
        }

        BlockState blockState = getBlockState();
        Block block = blockState.getBlock();
        
        float r = 0, g = 0, b = 0;
        boolean shouldAnimate = false;

        if (block instanceof VesselPlacer) {
            Block portalBlock = getActivePortalBlock(level, worldPosition);
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

        if (!shouldAnimate) {
            return;
        }
        
        RedstoneParticleData colorOptions = new RedstoneParticleData(r, g, b, 1.0F);
        Random random = level.random;
        long time = level.getGameTime();

        double centerX = worldPosition.getX() + 0.5D;
        double centerY = worldPosition.getY() + 0.5D;
        double centerZ = worldPosition.getZ() + 0.5D;

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
            level.addParticle(colorOptions, px, py + 2, pz, motionX, motionY + 2, motionZ);
        }

        if (time % 20 == 0) {
            level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.BLOCKS, 0.2F, 1.5F, false);
        }
    }

    private Block getActivePortalBlock(World world, BlockPos pos) {
        Block above = world.getBlockState(pos.above()).getBlock();
        if (above == ModBlocks.SKYRIA_PORTAL.get() || above == ModBlocks.OVERWORLD_PORTAL.get()) {
            return above;
        }
        return null;
    }
}