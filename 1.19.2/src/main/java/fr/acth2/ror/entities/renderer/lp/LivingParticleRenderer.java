package fr.acth2.ror.entities.renderer.lp;

import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.entities.entity.lp.EntityLivingParticle;
import fr.acth2.ror.entities.models.ExampleParticleModel;
import fr.acth2.ror.entities.models.lp.LivingParticleModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LivingParticleRenderer extends GeoEntityRenderer<EntityLivingParticle> {
    public LivingParticleRenderer(EntityRendererManager manager) {
        super(manager, new LivingParticleModel());
        this.shadowRadius = 0.0f;
    }
}