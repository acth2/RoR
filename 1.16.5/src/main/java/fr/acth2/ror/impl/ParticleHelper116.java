package fr.acth2.ror.impl;

import fr.acth2.ror.api.particle.IParticleHelper;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.world.World;

public class ParticleHelper116 implements IParticleHelper {

    @Override
    public void addRedstoneParticle(Object worldObj, double x, double y, double z,
                                    float red, float green, float blue, float size) {
        World world = (World) worldObj;
        world.addParticle(new RedstoneParticleData(red, green, blue, size),
                x, y, z, 0, 0, 0);
    }

    @Override
    public void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                             double mx, double my, double mz,
                                             float r, float g, float b, float size) {
        World world = (World) worldObj;
        world.addParticle(new RedstoneParticleData(r, g, b, size),
                x, y, z, mx, my, mz);
    }
}
