package fr.acth2.ror.api;

import fr.acth2.ror.api.block.IMaterialHelper;
import fr.acth2.ror.api.particle.IParticleHelper;
import fr.acth2.ror.api.platform.IPlatformHelper;
import fr.acth2.ror.api.registry.IRegistryHelper;
import fr.acth2.ror.api.sound.ISoundHelper;
import fr.acth2.ror.api.BlockEntity.ITileEntityHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final IMaterialHelper MATERIALS = load(IMaterialHelper.class);
    public static final ITileEntityHelper TILE_ENTITIES = load(ITileEntityHelper.class);
    public static final IParticleHelper PARTICLES = load(IParticleHelper.class);
    public static final ISoundHelper SOUNDS = load(ISoundHelper.class);

    private static <T> T load(Class<T> clazz) {
        for (T service : ServiceLoader.load(clazz)) {
            return service;
        }
        throw new RuntimeException(
                "[RoR] No implementation found for service: " + clazz.getName()
                        + "\nMake sure a META-INF/services/ file exists in the active version module."
        );
    }
}