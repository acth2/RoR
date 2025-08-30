package fr.acth2.ror.entities.renderer.trader;

import fr.acth2.ror.entities.entity.traders.EntitySkyriaTrader;
import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.entities.models.trader.SkyriaTraderModel;
import fr.acth2.ror.entities.models.traveler.TravelerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SkyriaTraderRenderer extends GeoEntityRenderer<EntitySkyriaTrader> {
    public SkyriaTraderRenderer(EntityRendererManager manager) {
        super(manager, new SkyriaTraderModel());
        this.shadowRadius = 0.0f;
    }
}
