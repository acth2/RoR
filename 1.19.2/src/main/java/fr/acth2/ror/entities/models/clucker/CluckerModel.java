package fr.acth2.ror.entities.models.clucker;

import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CluckerModel extends AnimatedGeoModel<EntityClucker> {

    @Override
    public ResourceLocation getModelLocation(EntityClucker object) {
        return new ResourceLocation(References.MODID, "geo/clucker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityClucker object) {
        return new ResourceLocation(References.MODID, "textures/entity/clucker/clucker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityClucker animatable) {
        return new ResourceLocation(References.MODID, "animations/clucker.animation.json");
    }
}