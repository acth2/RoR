package fr.acth2.ror.entities.models.skyder;

import fr.acth2.ror.entities.entity.cavesucker.EntityCaveSucker;
import fr.acth2.ror.entities.entity.skyder.EntitySkyder;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkyderModel extends AnimatedGeoModel<EntitySkyder> {

    @Override
    public ResourceLocation getModelLocation(EntitySkyder object) {
        return new ResourceLocation(References.MODID, "geo/skyder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySkyder object) {
        return new ResourceLocation(References.MODID, "textures/entity/skyder/skyder.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySkyder animatable) {
        return new ResourceLocation(References.MODID, "animations/skyder.animation.json");
    }
}