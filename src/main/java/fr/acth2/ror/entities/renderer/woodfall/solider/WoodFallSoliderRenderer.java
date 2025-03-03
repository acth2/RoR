package fr.acth2.ror.entities.renderer.woodfall.solider;

import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.entities.entity.woodfall.solider.EntityWoodFallSolider;
import fr.acth2.ror.entities.models.woodfall.WoodFallModel;
import fr.acth2.ror.entities.models.woodfall.solider.WoodFallSoliderModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WoodFallSoliderRenderer extends GeoEntityRenderer<EntityWoodFallSolider> {
    public WoodFallSoliderRenderer(EntityRendererManager manager) {
        super(manager, new WoodFallSoliderModel());
        this.shadowRadius = 0.35f;
    }
}
