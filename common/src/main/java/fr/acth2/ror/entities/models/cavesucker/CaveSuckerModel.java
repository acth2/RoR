package fr.acth2.ror.entities.models.cavesucker;

import fr.acth2.ror.entities.entity.cavesucker.EntityCaveSucker;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CaveSuckerModel extends AnimatedGeoModel<EntityCaveSucker> {

    @Override
    public ResourceLocation getModelLocation(EntityCaveSucker object) {
        return new ResourceLocation(References.MODID, "geo/cave_sucker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCaveSucker object) {
        return new ResourceLocation(References.MODID, "textures/entity/cv/cave_sucker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCaveSucker animatable) {
        return new ResourceLocation(References.MODID, "animations/cave_sucker.animation.json");
    }
}