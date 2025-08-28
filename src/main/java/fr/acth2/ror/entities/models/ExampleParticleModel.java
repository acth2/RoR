package fr.acth2.ror.entities.models;

import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ExampleParticleModel extends AnimatedGeoModel<EntityExampleParticle> {

    @Override
    public ResourceLocation getModelLocation(EntityExampleParticle object) {
        return new ResourceLocation(References.MODID, "geo/empty.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityExampleParticle object) {
        return new ResourceLocation(References.MODID, "textures/entity/empty.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityExampleParticle animatable) {
        return new ResourceLocation(References.MODID, null);
    }
}