package fr.acth2.ror.entities.renderer.axis;

import fr.acth2.ror.entities.entity.axis.EntityAxis;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.models.axis.AxisModel;
import fr.acth2.ror.entities.models.clucker.CluckerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AxisRenderer extends GeoEntityRenderer<EntityAxis> {
    public AxisRenderer(EntityRendererManager manager) {
        super(manager, new AxisModel());
        this.shadowRadius = 0.5f;
    }
}
