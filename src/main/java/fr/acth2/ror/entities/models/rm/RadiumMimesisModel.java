package fr.acth2.ror.entities.models.rm;

import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.entity.rm.EntityRadiumMimesis;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RadiumMimesisModel extends AnimatedGeoModel<EntityRadiumMimesis> {

    @Override
    public ResourceLocation getModelLocation(EntityRadiumMimesis object) {
        return new ResourceLocation(References.MODID, "geo/radium_mimesis.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRadiumMimesis object) {
        return new ResourceLocation(References.MODID, "textures/entity/rm/radium_mimesis.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRadiumMimesis animatable) {
        return new ResourceLocation(References.MODID, "animations/radium_mimesis.animation.json");
    }
}