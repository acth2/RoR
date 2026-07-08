package fr.acth2.ror.impl;

import com.mojang.math.Vector3f;
import fr.acth2.ror.api.sound.ISoundHelper;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class SoundHelper119 implements ISoundHelper {

    @Override
    public void playBeaconAmbient(Object worldObj, int x, int y, int z, float volume, float pitch) {
        Level level = (Level) worldObj;
        level.playLocalSound(x, y, z, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, volume, pitch, false);
    }

    @Override
    public void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                             double mx, double my, double mz,
                                             float r, float g, float b, float size) {
        Level level = (Level) worldObj;
        level.addParticle(
                new DustParticleOptions(new Vector3f(r, g, b), size),
                x, y, z, mx, my, mz
        );
    }
}