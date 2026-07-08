package fr.acth2.ror.entities.models.curser;

import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CurserModel extends AnimatedGeoModel<EntityCurser> {

    @Override
    public ResourceLocation getModelLocation(EntityCurser object) {
        return new ResourceLocation(References.MODID, "geo/curser.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCurser object) {
        return new ResourceLocation(References.MODID, "textures/entity/curser/curser.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCurser animatable) {
        return new ResourceLocation(References.MODID, "animations/curser.animation.json");
    }
}