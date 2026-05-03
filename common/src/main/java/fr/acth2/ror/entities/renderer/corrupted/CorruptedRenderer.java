package fr.acth2.ror.entities.renderer.corrupted;

import fr.acth2.ror.entities.entity.corrupted.EntityCorrupted;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.models.corrupted.CorruptedModel;
import fr.acth2.ror.entities.models.hopper.HopperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CorruptedRenderer extends GeoEntityRenderer<EntityCorrupted> {
    public CorruptedRenderer(EntityRendererManager manager) {
        super(manager, new CorruptedModel());
        this.shadowRadius = 1.0f;
    }
}
