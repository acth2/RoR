package fr.acth2.ror.entities.renderer.bri;

import fr.acth2.ror.entities.entity.bri.EntityBrokenInsurrectionist;
import fr.acth2.ror.entities.models.bri.BrokenInsurrectionistModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BrokenInsurrectionistRenderer extends GeoEntityRenderer<EntityBrokenInsurrectionist> {
    public BrokenInsurrectionistRenderer(EntityRendererManager manager) {
        super(manager, new BrokenInsurrectionistModel());
        this.shadowRadius = 0.5f;
    }
}
