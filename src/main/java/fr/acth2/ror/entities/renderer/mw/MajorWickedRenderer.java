package fr.acth2.ror.entities.renderer.mw;

import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.entities.models.mw.MajorWickedModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MajorWickedRenderer extends GeoEntityRenderer<EntityMajorWicked> {
    public MajorWickedRenderer(EntityRendererManager manager) {
        super(manager, new MajorWickedModel());
        this.shadowRadius = 0.5f;
    }
}
