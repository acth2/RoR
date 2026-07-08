package fr.acth2.ror.entities.models.creckon;

import fr.acth2.ror.entities.entity.creckon.EntityCreckon;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CreckonModel extends AnimatedGeoModel<EntityCreckon> {

    @Override
    public ResourceLocation getModelLocation(EntityCreckon object) {
        return new ResourceLocation(References.MODID, "geo/creckon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCreckon object) {
        return new ResourceLocation(References.MODID, "textures/entity/creckon/creckon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCreckon animatable) {
        return new ResourceLocation(References.MODID, "animations/creckon.animation.json");
    }
}