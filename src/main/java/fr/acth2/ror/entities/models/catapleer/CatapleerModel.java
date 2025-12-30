package fr.acth2.ror.entities.models.catapleer;

import fr.acth2.ror.entities.entity.catapleer.EntityCatapleer;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CatapleerModel extends AnimatedGeoModel<EntityCatapleer> {

    @Override
    public ResourceLocation getModelLocation(EntityCatapleer object) {
        return new ResourceLocation(References.MODID, "geo/catapleer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCatapleer object) {
        return new ResourceLocation(References.MODID, "textures/entity/catapleer/catapleer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCatapleer animatable) {
        return new ResourceLocation(References.MODID, "animations/catapleer.animation.json");
    }
}