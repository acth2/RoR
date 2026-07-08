package fr.acth2.ror.api.particle;

public interface IParticleHelper {
    void addRedstoneParticle(Object worldObj, double x, double y, double z,
                             float red, float green, float blue, float size);

    void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                      double mx, double my, double mz,
                                      float r, float g, float b, float size);
}
