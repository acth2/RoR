package fr.acth2.ror.entities.models.fussle;

import fr.acth2.ror.entities.entity.fussle.EntityFussle;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FussleModel extends AnimatedGeoModel<EntityFussle> {

    @Override
    public ResourceLocation getModelLocation(EntityFussle object) {
        return new ResourceLocation(References.MODID, "geo/fussle.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFussle object) {
        return new ResourceLocation(References.MODID, "textures/entity/fussle/fussle.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityFussle animatable) {
        return new ResourceLocation(References.MODID, "animations/fussle.animation.json");
    }
}