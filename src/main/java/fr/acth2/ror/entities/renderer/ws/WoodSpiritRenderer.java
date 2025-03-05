package fr.acth2.ror.entities.renderer.ws;

import fr.acth2.ror.entities.entity.ws.EntityWoodSpirit;
import fr.acth2.ror.entities.models.ws.WoodSpiritModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WoodSpiritRenderer extends GeoEntityRenderer<EntityWoodSpirit> {
    public WoodSpiritRenderer(EntityRendererManager manager) {
        super(manager, new WoodSpiritModel());
        this.shadowRadius = 0.5f;
    }
}
