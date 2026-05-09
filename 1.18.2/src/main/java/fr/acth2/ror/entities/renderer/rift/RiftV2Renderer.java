package fr.acth2.ror.entities.renderer.rift;

import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.entities.entity.rift.EntityRiftV2;
import fr.acth2.ror.entities.models.rift.RiftModel;
import fr.acth2.ror.entities.models.rift.RiftV2Model;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RiftV2Renderer extends GeoEntityRenderer<EntityRiftV2> {
    public RiftV2Renderer(EntityRendererManager manager) {
        super(manager, new RiftV2Model());
        this.shadowRadius = 0.0f;
    }
}