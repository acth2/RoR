package fr.acth2.ror.entities.renderer.hopper;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HopperRenderer extends GeoEntityRenderer<EntityHopper> {
    public HopperRenderer(EntityRendererManager manager) {
        super(manager, new HopperModel());
        this.shadowRadius = 1.0f;
    }
}
