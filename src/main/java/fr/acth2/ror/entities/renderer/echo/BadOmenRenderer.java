package fr.acth2.ror.entities.renderer.echo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.echo.EntityEcho;
import fr.acth2.ror.entities.models.bo.BadOmenModel;
import fr.acth2.ror.entities.models.echo.EchoModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BadOmenRenderer extends GeoEntityRenderer<EntityBadOmen> {
    public BadOmenRenderer(EntityRendererManager manager) {
        super(manager, new BadOmenModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(EntityBadOmen animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}