package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.particles.ParticleTypes;
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

        Block portalBlock = getActivePortalBlock(level, worldPosition);
        if (portalBlock == null) {
            return;
        }

        Random random = level.random;
        long time = level.getGameTime();

        // --- Color Setup ---
        float r, g, b;
        if (portalBlock == ModBlocks.OVERWORLD_PORTAL.get()) {
            r = 0.1f; g = 0.8f; b = 0.1f; // Vibrant Green
        } else { // Skyria
            r = 0.1f; g = 0.1f; b = 0.9f; // Vibrant Blue
        }
        RedstoneParticleData mainColorOptions = new RedstoneParticleData(r, g, b, 1.0F);
        RedstoneParticleData secondaryBlueOptions = new RedstoneParticleData(0.3f, 0.5f, 1.0f, 1.0F);


        double centerX = worldPosition.getX() + 0.5D;
        double centerY = worldPosition.getY() + 2.5D; // Portal Core Y-level
        double centerZ = worldPosition.getZ() + 0.5D;

        // --- Effect 1: Continuous Vortex (The "Lines") ---
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
            level.addParticle(mainColorOptions, px, py, pz, motionX, motionY, motionZ);
        }

        // --- Effect 2: Smaller, Blue Atmospheric Siphon ---
        for (int i = 0; i < 8; ++i) {
            double px = centerX + (random.nextDouble() - 0.5D) * 6.0D; // Reduced radius
            double py = centerY + (random.nextDouble() - 0.5D) * 4.0D; // Reduced height
            double pz = centerZ + (random.nextDouble() - 0.5D) * 6.0D; // Reduced radius
            double motionX = (centerX - px) * 0.08D;
            double motionY = (centerY - py) * 0.08D;
            double motionZ = (centerZ - pz) * 0.08D;
            level.addParticle(secondaryBlueOptions, px, py, pz, motionX, motionY, motionZ);
        }

        // --- Soundscape ---
        if (time % 20 == 0) {
            level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.BLOCKS, 0.2F, 1.5F, false);
        }
        if (time % 35 == 0) {
            level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundEvents.CONDUIT_AMBIENT_SHORT, SoundCategory.BLOCKS, 0.3F, 1.2F, false);
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