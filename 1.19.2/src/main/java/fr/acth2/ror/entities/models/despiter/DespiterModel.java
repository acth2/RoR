package fr.acth2.ror.entities.models.despiter;

import fr.acth2.ror.entities.entity.despiter.EntityDespiter;
import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DespiterModel extends AnimatedGeoModel<EntityDespiter> {

    @Override
    public ResourceLocation getModelLocation(EntityDespiter object) {
        return new ResourceLocation(References.MODID, "geo/despiter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityDespiter object) {
        return new ResourceLocation(References.MODID, "textures/entity/despiter/despiter.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityDespiter animatable) {
        return new ResourceLocation(References.MODID, "animations/despiter.animation.json");
    }
}