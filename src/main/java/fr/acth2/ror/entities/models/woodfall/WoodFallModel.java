package fr.acth2.ror.entities.models.woodfall;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WoodFallModel extends AnimatedGeoModel<EntityWoodFall> {

    @Override
    public ResourceLocation getModelLocation(EntityWoodFall object) {
        return new ResourceLocation(References.MODID, "geo/woodfall.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWoodFall object) {
        return new ResourceLocation(References.MODID, "textures/entity/woodfall/woodfall.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWoodFall animatable) {
        return new ResourceLocation(References.MODID, "animations/woodfall.animation.json");
    }
}