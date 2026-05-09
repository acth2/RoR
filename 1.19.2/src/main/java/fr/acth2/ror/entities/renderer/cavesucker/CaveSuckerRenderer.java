package fr.acth2.ror.entities.renderer.cavesucker;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.entities.entity.cavesucker.EntityCaveSucker;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.entities.models.cavesucker.CaveSuckerModel;
import fr.acth2.ror.entities.models.seeker.SeekerModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CaveSuckerRenderer extends GeoEntityRenderer<EntityCaveSucker> {
    public CaveSuckerRenderer(EntityRendererManager manager) {
        super(manager, new CaveSuckerModel());
        this.shadowRadius = 0.5f;
    }
}