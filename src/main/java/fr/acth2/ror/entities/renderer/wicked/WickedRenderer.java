package fr.acth2.ror.entities.renderer.wicked;

import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.entities.models.wicked.WickedModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WickedRenderer extends GeoEntityRenderer<EntityWicked> {
    public WickedRenderer(EntityRendererManager manager) {
        super(manager, new WickedModel());
        this.shadowRadius = 0.5f;
    }
}
