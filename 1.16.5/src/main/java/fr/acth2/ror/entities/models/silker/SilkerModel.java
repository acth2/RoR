package fr.acth2.ror.entities.models.silker;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.silker.EntitySilker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SilkerModel extends AnimatedGeoModel<EntitySilker> {

    @Override
    public ResourceLocation getModelLocation(EntitySilker object) {
        return new ResourceLocation(References.MODID, "geo/silker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySilker object) {
        return new ResourceLocation(References.MODID, "textures/entity/silker/silker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySilker animatable) {
        return new ResourceLocation(References.MODID, "animations/silker.animation.json");
    }
}