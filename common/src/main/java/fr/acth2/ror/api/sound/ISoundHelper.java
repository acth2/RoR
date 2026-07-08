package fr.acth2.ror.api.sound;

public interface ISoundHelper {
    void playBeaconAmbient(Object worldObj, int x, int y, int z, float volume, float pitch);
    void addColoredParticleWithMotion(Object worldObj, double x, double y, double z,
                                      double mx, double my, double mz,
                                      float r, float g, float b, float size);
}