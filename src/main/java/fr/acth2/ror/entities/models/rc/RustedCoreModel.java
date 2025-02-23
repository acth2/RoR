package fr.acth2.ror.entities.models.rc;

import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RustedCoreModel extends AnimatedGeoModel<EntityRustedCore> {

    @Override
    public ResourceLocation getModelLocation(EntityRustedCore object) {
        return new ResourceLocation(References.MODID, "geo/rusted_core.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRustedCore object) {
        return new ResourceLocation(References.MODID, "textures/entity/rc/rusted_core.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRustedCore animatable) {
        return new ResourceLocation(References.MODID, "animations/rusted_core.animation.json");
    }
}