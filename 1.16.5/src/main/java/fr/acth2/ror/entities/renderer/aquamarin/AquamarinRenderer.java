package fr.acth2.ror.entities.renderer.aquamarin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.aquamarin.EntityAquamarin;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.entities.models.aquamarin.AquamarinModel;
import fr.acth2.ror.entities.models.seeker.SeekerModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class AquamarinRenderer extends GeoEntityRenderer<EntityAquamarin> {
    public AquamarinRenderer(EntityRendererManager manager) {
        super(manager, new AquamarinModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(EntityAquamarin animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}