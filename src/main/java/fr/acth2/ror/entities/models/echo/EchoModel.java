package fr.acth2.ror.entities.models.echo;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.echo.EntityEcho;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EchoModel extends AnimatedGeoModel<EntityEcho> {

    @Override
    public ResourceLocation getModelLocation(EntityEcho object) {
        return new ResourceLocation(References.MODID, "geo/echo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityEcho object) {
        return new ResourceLocation(References.MODID, "textures/echo/echo.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityEcho animatable) {
        return new ResourceLocation(References.MODID, "animations/echo.animation.json");
    }
}