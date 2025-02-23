package fr.acth2.ror.entities.renderer.lc;

import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.entities.models.lc.LostCaverModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LostCaverRenderer extends GeoEntityRenderer<EntityLostCaver> {
    public LostCaverRenderer(EntityRendererManager manager) {
        super(manager, new LostCaverModel());
        this.shadowRadius = 1.5f;
    }
}
