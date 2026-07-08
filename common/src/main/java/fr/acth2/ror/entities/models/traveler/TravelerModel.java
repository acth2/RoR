package fr.acth2.ror.entities.models.traveler;

import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TravelerModel extends AnimatedGeoModel<EntityTraveler> {

    @Override
    public ResourceLocation getModelLocation(EntityTraveler object) {
        return new ResourceLocation(References.MODID, "geo/traveler.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTraveler object) {
        return new ResourceLocation(References.MODID, "textures/entity/traveler/traveler.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTraveler animatable) {
        return new ResourceLocation(References.MODID, "animations/traveler.animation.json");
    }
}