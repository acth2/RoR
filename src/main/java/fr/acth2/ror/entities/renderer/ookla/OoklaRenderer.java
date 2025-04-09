package fr.acth2.ror.entities.renderer.ookla;

import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.ookla.EntityOokla;
import fr.acth2.ror.entities.models.curser.CurserModel;
import fr.acth2.ror.entities.models.ookla.OoklaModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OoklaRenderer extends GeoEntityRenderer<EntityOokla> {
    public OoklaRenderer(EntityRendererManager manager) {
        super(manager, new OoklaModel());
        this.shadowRadius = 1.0f;
    }
}
