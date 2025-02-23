package fr.acth2.ror.entities.renderer;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.models.EntityExampleModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityExampleRenderer extends GeoEntityRenderer<EntityExample> {
    public EntityExampleRenderer(EntityRendererManager manager) {
        super(manager, new EntityExampleModel());
        this.shadowRadius = 0.5f;
    }
}
