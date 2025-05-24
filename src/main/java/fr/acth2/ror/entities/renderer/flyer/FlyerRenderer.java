package fr.acth2.ror.entities.renderer.flyer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.flyer.EntityFlyer;
import fr.acth2.ror.entities.models.bo.BadOmenModel;
import fr.acth2.ror.entities.models.flyer.FlyerModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class FlyerRenderer extends GeoEntityRenderer<EntityFlyer> {
    public FlyerRenderer(EntityRendererManager manager) {
        super(manager, new FlyerModel());
        this.shadowRadius = 0.24f;
    }
}