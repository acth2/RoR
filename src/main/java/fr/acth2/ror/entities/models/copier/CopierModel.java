package fr.acth2.ror.entities.models.copier;

import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.copier.EntityCopier;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CopierModel extends AnimatedGeoModel<EntityCopier> {

    @Override
    public ResourceLocation getModelLocation(EntityCopier object) {
        return new ResourceLocation(References.MODID, "geo/copier.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCopier object) {
        return new ResourceLocation(References.MODID, "textures/entity/copier/copier.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCopier animatable) {
        return new ResourceLocation(References.MODID, "animations/copier.animation.json");
    }
}