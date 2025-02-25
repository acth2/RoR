package fr.acth2.ror.entities.renderer.woodfall;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import fr.acth2.ror.entities.models.woodfall.WoodFallModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WoodFallRenderer extends GeoEntityRenderer<EntityWoodFall> {
    public WoodFallRenderer(EntityRendererManager manager) {
        super(manager, new WoodFallModel());
        this.shadowRadius = 1.0f;
    }
}
