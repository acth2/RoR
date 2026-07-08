package fr.acth2.ror.entities.renderer.rm;

import fr.acth2.ror.entities.entity.rm.EntityRadiumMimesis;
import fr.acth2.ror.entities.entity.silker.EntitySilker;
import fr.acth2.ror.entities.models.rm.RadiumMimesisModel;
import fr.acth2.ror.entities.models.silker.SilkerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RadiumMimesisRenderer extends GeoEntityRenderer<EntityRadiumMimesis> {
    public RadiumMimesisRenderer(EntityRendererManager manager) {
        super(manager, new RadiumMimesisModel());
        this.shadowRadius = 1.0f;
    }
}
