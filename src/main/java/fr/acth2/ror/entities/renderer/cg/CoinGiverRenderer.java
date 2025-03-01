package fr.acth2.ror.entities.renderer.cg;

import fr.acth2.ror.entities.entity.cg.EntityCoinGiver;
import fr.acth2.ror.entities.models.cg.CoinGiverModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CoinGiverRenderer extends GeoEntityRenderer<EntityCoinGiver> {
    public CoinGiverRenderer(EntityRendererManager manager) {
        super(manager, new CoinGiverModel());
        this.shadowRadius = 0.5f;
    }
}
