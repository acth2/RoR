package fr.acth2.ror.entities.models.mw;

import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MajorWickedModel extends AnimatedGeoModel<EntityMajorWicked> {

    @Override
    public ResourceLocation getModelLocation(EntityMajorWicked object) {
        return new ResourceLocation(References.MODID, "geo/major_wicked.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMajorWicked object) {
        return new ResourceLocation(References.MODID, "textures/entity/mw/major_wicked.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMajorWicked animatable) {
        return new ResourceLocation(References.MODID, "animations/major_wicked.animation.json");
    }
}