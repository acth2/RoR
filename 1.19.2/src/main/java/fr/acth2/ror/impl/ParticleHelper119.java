package fr.acth2.ror.impl;

import com.mojang.math.Vector3f;
import fr.acth2.ror.api.particle.IParticleHelper;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;

public class ParticleHelper119  implements IParticleHelper {

    @Override
    public void addRedstoneParticle(Object worldObj, double x, double y, double z,
                                    float red, float green, float blue, float size) {
        Level level = (Level) worldObj;
        level.addParticle(new DustParticleOptions(new Vector3f(red, green, blue), size),
                x, y, z, 0, 0, 0);
    }

    @Override
    public void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                             double mx, double my, double mz,
                                             float r, float g, float b, float size) {
        Level level = (Level) worldObj;
        level.addParticle(new DustParticleOptions(new Vector3f(r, g, b), size),
                x, y, z, mx, my, mz);
    }
}