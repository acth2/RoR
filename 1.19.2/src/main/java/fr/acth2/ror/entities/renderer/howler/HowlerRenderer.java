package fr.acth2.ror.entities.renderer.howler;

import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.entity.howler.EntityHowler;
import fr.acth2.ror.entities.models.grasser.GrasserModel;
import fr.acth2.ror.entities.models.howler.HowlerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HowlerRenderer extends GeoEntityRenderer<EntityHowler> {
    public HowlerRenderer(EntityRendererManager manager) {
        super(manager, new HowlerModel());
        this.shadowRadius = 1.0f;
    }
}
