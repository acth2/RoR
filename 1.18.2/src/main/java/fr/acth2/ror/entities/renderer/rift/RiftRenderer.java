package fr.acth2.ror.entities.renderer.rift;

import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.entities.models.rift.RiftModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RiftRenderer extends GeoEntityRenderer<EntityRift> {
    public RiftRenderer(EntityRendererManager manager) {
        super(manager, new RiftModel());
        this.shadowRadius = 0.0f;
    }
}