package fr.acth2.ror.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.EntityExampleInvader;
import fr.acth2.ror.entities.entity.echo.EntityEcho;
import fr.acth2.ror.entities.models.EntityExampleModel;
import fr.acth2.ror.entities.models.ExampleInvaderModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ExampleInvaderRenderer extends GeoEntityRenderer<EntityExampleInvader> {
    public ExampleInvaderRenderer(EntityRendererManager manager) {
        super(manager, new ExampleInvaderModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(EntityExampleInvader animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
