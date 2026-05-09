package fr.acth2.ror.entities.models.howler;

import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.entity.howler.EntityHowler;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HowlerModel extends AnimatedGeoModel<EntityHowler> {

    @Override
    public ResourceLocation getModelLocation(EntityHowler object) {
        return new ResourceLocation(References.MODID, "geo/howler.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHowler object) {
        return new ResourceLocation(References.MODID, "textures/entity/howler/howler.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHowler animatable) {
        return new ResourceLocation(References.MODID, "animations/howler.animation.json");
    }
}