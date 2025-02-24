package fr.acth2.ror.entities.models.wicked;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WickedModel extends AnimatedGeoModel<EntityWicked> {

    @Override
    public ResourceLocation getModelLocation(EntityWicked object) {
        return new ResourceLocation(References.MODID, "geo/wicked.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWicked object) {
        return new ResourceLocation(References.MODID, "textures/entity/wicked/wicked.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWicked animatable) {
        return new ResourceLocation(References.MODID, "animations/wicked.animation.json");
    }
}