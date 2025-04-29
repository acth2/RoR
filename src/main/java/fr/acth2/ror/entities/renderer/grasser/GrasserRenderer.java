package fr.acth2.ror.entities.renderer.grasser;

import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.models.grasser.GrasserModel;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GrasserRenderer extends GeoEntityRenderer<EntityGrasser> {
    public GrasserRenderer(EntityRendererManager manager) {
        super(manager, new GrasserModel());
        this.shadowRadius = 1.0f;
    }
}
