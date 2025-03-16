package fr.acth2.ror.entities.renderer.fussle;

import fr.acth2.ror.entities.entity.fussle.EntityFussle;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.models.fussle.FussleModel;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FussleRenderer extends GeoEntityRenderer<EntityFussle> {
    public FussleRenderer(EntityRendererManager manager) {
        super(manager, new FussleModel());
        this.shadowRadius = 1.0f;
    }
}
