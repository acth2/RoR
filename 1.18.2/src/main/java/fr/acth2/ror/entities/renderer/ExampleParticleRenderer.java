package fr.acth2.ror.entities.renderer;

import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.entities.models.ExampleParticleModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ExampleParticleRenderer extends GeoEntityRenderer<EntityExampleParticle> {
    public ExampleParticleRenderer(EntityRendererManager manager) {
        super(manager, new ExampleParticleModel());
        this.shadowRadius = 0.0f;
    }
}