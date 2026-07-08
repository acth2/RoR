package fr.acth2.ror.entities.renderer.bi;

import fr.acth2.ror.entities.entity.bi.EntityBloodInfectioner;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.models.bi.BloodInfectionerModel;
import fr.acth2.ror.entities.models.clucker.CluckerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BloodInfectionerRenderer extends GeoEntityRenderer<EntityBloodInfectioner> {
    public BloodInfectionerRenderer(EntityRendererManager manager) {
        super(manager, new BloodInfectionerModel());
        this.shadowRadius = 0.5f;
    }
}
