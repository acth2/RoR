package fr.acth2.ror.entities.renderer.lb;

import fr.acth2.ror.entities.entity.lb.EntityLavaBeing;
import fr.acth2.ror.entities.entity.mimic.EntityMimic;
import fr.acth2.ror.entities.models.lb.LavaBeingModel;
import fr.acth2.ror.entities.models.mimic.MimicModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LavaBeingRenderer extends GeoEntityRenderer<EntityLavaBeing> {
    public LavaBeingRenderer(EntityRendererManager manager) {
        super(manager, new LavaBeingModel());
        this.shadowRadius = 0.0f;
    }
}
