package fr.acth2.ror.entities.renderer.creckon;

import fr.acth2.ror.entities.entity.creckon.EntityCreckon;
import fr.acth2.ror.entities.models.creckon.CreckonModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreckonRenderer extends GeoEntityRenderer<EntityCreckon> {
    public CreckonRenderer(EntityRendererManager manager) {
        super(manager, new CreckonModel());
        this.shadowRadius = 1.0f;
    }
}
