package fr.acth2.ror.entities.models.ookla;

import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.ookla.EntityOokla;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OoklaModel extends AnimatedGeoModel<EntityOokla> {

    @Override
    public ResourceLocation getModelLocation(EntityOokla object) {
        return new ResourceLocation(References.MODID, "geo/ookla.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityOokla object) {
        return new ResourceLocation(References.MODID, "textures/entity/ookla/ookla.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityOokla animatable) {
        return new ResourceLocation(References.MODID, "animations/ookla.animation.json");
    }
}