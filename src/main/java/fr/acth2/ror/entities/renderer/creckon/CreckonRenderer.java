package fr.acth2.ror.entities.renderer.creckon;

import fr.acth2.ror.entities.entity.corrupted.EntityCorrupted;
import fr.acth2.ror.entities.models.corrupted.CorruptedModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreckonRenderer extends GeoEntityRenderer<EntityCorrupted> {
    public CreckonRenderer(EntityRendererManager manager) {
        super(manager, new CorruptedModel());
        this.shadowRadius = 1.0f;
    }
}
