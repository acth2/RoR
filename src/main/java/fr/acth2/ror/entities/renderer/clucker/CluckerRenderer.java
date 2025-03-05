package fr.acth2.ror.entities.renderer.clucker;

import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.models.clucker.CluckerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CluckerRenderer extends GeoEntityRenderer<EntityClucker> {
    public CluckerRenderer(EntityRendererManager manager) {
        super(manager, new CluckerModel());
        this.shadowRadius = 0.5f;
    }
}
