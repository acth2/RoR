package fr.acth2.ror.entities.renderer.mimic;

import fr.acth2.ror.entities.entity.mimic.EntityMimic;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.models.mimic.MimicModel;
import fr.acth2.ror.entities.models.rc.RustedCoreModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MimicRenderer extends GeoEntityRenderer<EntityMimic> {
    public MimicRenderer(EntityRendererManager manager) {
        super(manager, new MimicModel());
        this.shadowRadius = 1.0f;
    }
}
