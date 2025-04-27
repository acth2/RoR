package fr.acth2.ror.entities.models.se;

import fr.acth2.ror.entities.entity.ghost.EntityGhost;
import fr.acth2.ror.entities.entity.se.EntitySkyEjector;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkyEjectorModel extends AnimatedGeoModel<EntitySkyEjector> {

    @Override
    public ResourceLocation getModelLocation(EntitySkyEjector object) {
        return new ResourceLocation(References.MODID, "geo/sky_ejector.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySkyEjector object) {
        return new ResourceLocation(References.MODID, "textures/entity/se/sky_ejector.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySkyEjector animatable) {
        return new ResourceLocation(References.MODID, "animations/sky_ejector.animation.json");
    }
}