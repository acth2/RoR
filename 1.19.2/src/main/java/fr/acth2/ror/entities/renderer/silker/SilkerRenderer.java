package fr.acth2.ror.entities.renderer.silker;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.silker.EntitySilker;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import fr.acth2.ror.entities.models.silker.SilkerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SilkerRenderer extends GeoEntityRenderer<EntitySilker> {
    public SilkerRenderer(EntityRendererManager manager) {
        super(manager, new SilkerModel());
        this.shadowRadius = 1.0f;
    }
}
