package fr.acth2.ror.entities.models.lc;

import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LostCaverModel extends AnimatedGeoModel<EntityLostCaver> {

    @Override
    public ResourceLocation getModelLocation(EntityLostCaver object) {
        return new ResourceLocation(References.MODID, "geo/lost_caver.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLostCaver object) {
        return new ResourceLocation(References.MODID, "textures/entity/lc/lost_caver.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityLostCaver animatable) {
        return new ResourceLocation(References.MODID, "animations/lost_caver.animation.json");
    }
}