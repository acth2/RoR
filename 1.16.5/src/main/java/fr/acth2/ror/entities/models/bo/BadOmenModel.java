package fr.acth2.ror.entities.models.bo;

import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BadOmenModel extends AnimatedGeoModel<EntityBadOmen> {

    @Override
    public ResourceLocation getModelLocation(EntityBadOmen object) {
        return new ResourceLocation(References.MODID, "geo/bad_omen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBadOmen object) {
        return new ResourceLocation(References.MODID, "textures/entity/bo/bad_omen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBadOmen animatable) {
        return new ResourceLocation(References.MODID, "animations/bad_omen.animation.json");
    }
}