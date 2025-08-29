package fr.acth2.ror.entities.models.lp;

import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.entities.entity.lp.EntityLivingParticle;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LivingParticleModel extends AnimatedGeoModel<EntityLivingParticle> {

    @Override
    public ResourceLocation getModelLocation(EntityLivingParticle object) {
        return new ResourceLocation(References.MODID, "geo/empty.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLivingParticle object) {
        return new ResourceLocation(References.MODID, "textures/entity/empty.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityLivingParticle animatable) {
        return new ResourceLocation(References.MODID, null);
    }
}