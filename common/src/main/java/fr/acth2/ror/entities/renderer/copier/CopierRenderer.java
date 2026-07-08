package fr.acth2.ror.entities.renderer.copier;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.copier.EntityCopier;
import fr.acth2.ror.entities.models.clucker.CluckerModel;
import fr.acth2.ror.entities.models.copier.CopierModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CopierRenderer extends GeoEntityRenderer<EntityCopier> {
    public CopierRenderer(EntityRendererManager manager) {
        super(manager, new CopierModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(EntityCopier animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
