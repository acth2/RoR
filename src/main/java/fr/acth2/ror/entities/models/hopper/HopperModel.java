package fr.acth2.ror.entities.models.hopper;

import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HopperModel extends AnimatedGeoModel<EntityHopper> {

    @Override
    public ResourceLocation getModelLocation(EntityHopper object) {
        return new ResourceLocation(References.MODID, "geo/hopper.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHopper object) {
        return new ResourceLocation(References.MODID, "textures/entity/hopper/hopper.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHopper animatable) {
        return new ResourceLocation(References.MODID, "animations/hopper.animation.json");
    }
}