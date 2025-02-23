package fr.acth2.ror.entities.renderer.rc;

import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.models.rc.RustedCoreModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RustedCoreRenderer extends GeoEntityRenderer<EntityRustedCore> {
    public RustedCoreRenderer(EntityRendererManager manager) {
        super(manager, new RustedCoreModel());
        this.shadowRadius = 1.5f;
    }
}
