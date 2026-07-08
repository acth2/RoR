package fr.acth2.ror.entities.models;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.utils.References;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import net.minecraft.util.ResourceLocation;

public class EntityExampleModel extends AnimatedGeoModel<EntityExample> {

    @Override
    public ResourceLocation getModelLocation(EntityExample object) {
        return new ResourceLocation(References.MODID, "geo/entityexample.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityExample object) {
        return new ResourceLocation(References.MODID, "textures/entity/entityexample.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityExample animatable) {
        return new ResourceLocation(References.MODID, "animations/entityexample.animation.json");
    }
}