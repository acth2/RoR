package fr.acth2.ror.impl;

import fr.acth2.ror.api.sound.ISoundHelper;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SoundHelper116 implements ISoundHelper {

    @Override
    public void playBeaconAmbient(Object worldObj, int x, int y, int z, float volume, float pitch) {
        World world = (World) worldObj;
        world.playLocalSound(x, y, z, SoundEvents.BEACON_AMBIENT, SoundCategory.BLOCKS, volume, pitch, false);
    }

    @Override
    public void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                             double mx, double my, double mz,
                                             float r, float g, float b, float size) {
        World world = (World) worldObj;
        world.addParticle(new RedstoneParticleData(r, g, b, size), x, y, z, mx, my, mz);
    }
}