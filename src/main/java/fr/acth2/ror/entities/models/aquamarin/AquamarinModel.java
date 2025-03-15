package fr.acth2.ror.entities.models.aquamarin;

import fr.acth2.ror.entities.entity.aquamarin.EntityAquamarin;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AquamarinModel extends AnimatedGeoModel<EntityAquamarin> {

    @Override
    public ResourceLocation getModelLocation(EntityAquamarin object) {
        return new ResourceLocation(References.MODID, "geo/aquamarin.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAquamarin object) {
        return new ResourceLocation(References.MODID, "textures/entity/aquamarin/aquamarin.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAquamarin animatable) {
        return new ResourceLocation(References.MODID, "animations/aquamarin.animation.json");
    }
}