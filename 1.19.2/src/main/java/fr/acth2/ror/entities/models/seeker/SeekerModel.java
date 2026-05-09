package fr.acth2.ror.entities.models.seeker;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SeekerModel extends AnimatedGeoModel<EntitySeeker> {

    @Override
    public ResourceLocation getModelLocation(EntitySeeker object) {
        return new ResourceLocation(References.MODID, "geo/seeker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySeeker object) {
        return new ResourceLocation(References.MODID, "textures/entity/seeker/seeker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySeeker animatable) {
        return new ResourceLocation(References.MODID, "animations/seeker.animation.json");
    }
}