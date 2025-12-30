package fr.acth2.ror.entities.renderer.catapleer;

import fr.acth2.ror.entities.entity.catapleer.EntityCatapleer;
import fr.acth2.ror.entities.entity.cavesucker.EntityCaveSucker;
import fr.acth2.ror.entities.models.catapleer.CatapleerModel;
import fr.acth2.ror.entities.models.cavesucker.CaveSuckerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CatapleerRenderer extends GeoEntityRenderer<EntityCatapleer> {
    public CatapleerRenderer(EntityRendererManager manager) {
        super(manager, new CatapleerModel());
        this.shadowRadius = 0.5f;
    }
}