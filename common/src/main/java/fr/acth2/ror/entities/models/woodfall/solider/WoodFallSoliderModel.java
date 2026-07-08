package fr.acth2.ror.entities.models.woodfall.solider;

import fr.acth2.ror.entities.entity.woodfall.solider.EntityWoodFallSolider;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WoodFallSoliderModel extends AnimatedGeoModel<EntityWoodFallSolider> {

    @Override
    public ResourceLocation getModelLocation(EntityWoodFallSolider object) {
        return new ResourceLocation(References.MODID, "geo/woodfall_solider.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWoodFallSolider object) {
        return new ResourceLocation(References.MODID, "textures/entity/woodfall/solider/woodfall_solider.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWoodFallSolider animatable) {
        return new ResourceLocation(References.MODID, "animations/woodfall_solider.animation.json");
    }
}