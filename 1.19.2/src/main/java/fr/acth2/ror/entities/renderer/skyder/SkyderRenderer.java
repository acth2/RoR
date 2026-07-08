package fr.acth2.ror.entities.renderer.skyder;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.se.EntitySkyEjector;
import fr.acth2.ror.entities.entity.skyder.EntitySkyder;
import fr.acth2.ror.entities.models.se.SkyEjectorModel;
import fr.acth2.ror.entities.models.skyder.SkyderModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SkyderRenderer extends GeoEntityRenderer<EntitySkyder> {
    public SkyderRenderer(EntityRendererManager manager) {
        super(manager, new SkyderModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(EntitySkyder animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}