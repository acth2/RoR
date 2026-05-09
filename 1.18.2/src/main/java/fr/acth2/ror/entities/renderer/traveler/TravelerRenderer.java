package fr.acth2.ror.entities.renderer.traveler;

import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.entities.models.traveler.TravelerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TravelerRenderer extends GeoEntityRenderer<EntityTraveler> {
    public TravelerRenderer(EntityRendererManager manager) {
        super(manager, new TravelerModel());
        this.shadowRadius = 0.0f;
    }
}
