package fr.acth2.ror.entities.renderer.despiter;

import fr.acth2.ror.entities.entity.despiter.EntityDespiter;
import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.models.despiter.DespiterModel;
import fr.acth2.ror.entities.models.grasser.GrasserModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DespiterRenderer extends GeoEntityRenderer<EntityDespiter> {
    public DespiterRenderer(EntityRendererManager manager) {
        super(manager, new DespiterModel());
        this.shadowRadius = 1.0f;
    }
}
