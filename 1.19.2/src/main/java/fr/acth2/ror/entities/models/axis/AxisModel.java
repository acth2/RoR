package fr.acth2.ror.entities.models.axis;

import fr.acth2.ror.entities.entity.axis.EntityAxis;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AxisModel extends AnimatedGeoModel<EntityAxis> {

    @Override
    public ResourceLocation getModelLocation(EntityAxis object) {
        return new ResourceLocation(References.MODID, "geo/axis.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAxis object) {
        return new ResourceLocation(References.MODID, "textures/entity/axis/axis.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAxis animatable) {
        return new ResourceLocation(References.MODID, "animations/axis.animation.json");
    }
}