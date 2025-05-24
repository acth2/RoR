package fr.acth2.ror.entities.models.flyer;

import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.flyer.EntityFlyer;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlyerModel extends AnimatedGeoModel<EntityFlyer> {

    @Override
    public ResourceLocation getModelLocation(EntityFlyer object) {
        return new ResourceLocation(References.MODID, "geo/flyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlyer object) {
        return new ResourceLocation(References.MODID, "textures/entity/flyer/flyer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityFlyer animatable) {
        return new ResourceLocation(References.MODID, "animations/flyer.animation.json");
    }
}