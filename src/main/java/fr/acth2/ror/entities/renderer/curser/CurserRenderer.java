package fr.acth2.ror.entities.renderer.curser;

import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.models.curser.CurserModel;
import fr.acth2.ror.entities.models.rc.RustedCoreModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CurserRenderer extends GeoEntityRenderer<EntityCurser> {
    public CurserRenderer(EntityRendererManager manager) {
        super(manager, new CurserModel());
        this.shadowRadius = 1.0f;
    }
}
