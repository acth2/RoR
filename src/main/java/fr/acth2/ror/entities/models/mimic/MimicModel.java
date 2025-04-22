package fr.acth2.ror.entities.models.mimic;

import fr.acth2.ror.entities.entity.mimic.EntityMimic;
import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MimicModel extends AnimatedGeoModel<EntityMimic> {

    @Override
    public ResourceLocation getModelLocation(EntityMimic object) {
        return new ResourceLocation(References.MODID, "geo/mimic.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMimic object) {
        return new ResourceLocation(References.MODID, "textures/entity/mimic/mimic.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMimic animatable) {
        return new ResourceLocation(References.MODID, "animations/mimic.animation.json");
    }
}