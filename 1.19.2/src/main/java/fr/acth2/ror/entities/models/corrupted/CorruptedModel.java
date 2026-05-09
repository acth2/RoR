package fr.acth2.ror.entities.models.corrupted;

import fr.acth2.ror.entities.entity.corrupted.EntityCorrupted;
import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CorruptedModel extends AnimatedGeoModel<EntityCorrupted> {

    @Override
    public ResourceLocation getModelLocation(EntityCorrupted object) {
        return new ResourceLocation(References.MODID, "geo/corrupted.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCorrupted object) {
        return new ResourceLocation(References.MODID, "textures/entity/corrupted/corrupted.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCorrupted animatable) {
        return new ResourceLocation(References.MODID, "animations/corrupted.animation.json");
    }
}