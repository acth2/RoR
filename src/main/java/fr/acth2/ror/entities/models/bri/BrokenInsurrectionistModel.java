package fr.acth2.ror.entities.models.bri;

import fr.acth2.ror.entities.entity.bri.EntityBrokenInsurrectionist;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrokenInsurrectionistModel extends AnimatedGeoModel<EntityBrokenInsurrectionist> {

    @Override
    public ResourceLocation getModelLocation(EntityBrokenInsurrectionist object) {
        return new ResourceLocation(References.MODID, "geo/broken_insurrectionist.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBrokenInsurrectionist object) {
        return new ResourceLocation(References.MODID, "textures/entity/bri/broken_insurrectionist.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBrokenInsurrectionist animatable) {
        return new ResourceLocation(References.MODID, "animations/broken_insurrectionist.animation.json");
    }
}