package fr.acth2.ror.entities.models.lb;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.lb.EntityLavaBeing;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LavaBeingModel extends AnimatedGeoModel<EntityLavaBeing> {

    @Override
    public ResourceLocation getModelLocation(EntityLavaBeing object) {
        return new ResourceLocation(References.MODID, "geo/lava_being.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLavaBeing object) {
        return new ResourceLocation(References.MODID, "textures/entity/lb/lava_being.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityLavaBeing animatable) {
        return new ResourceLocation(References.MODID, "animations/lava_being.animation.json");
    }
}