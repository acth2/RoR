package fr.acth2.ror.entities.models.grasser;

import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrasserModel extends AnimatedGeoModel<EntityGrasser> {

    @Override
    public ResourceLocation getModelLocation(EntityGrasser object) {
        return new ResourceLocation(References.MODID, "geo/grasser.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGrasser object) {
        return new ResourceLocation(References.MODID, "textures/entity/grasser/grasser.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGrasser animatable) {
        return new ResourceLocation(References.MODID, "animations/grasser.animation.json");
    }
}