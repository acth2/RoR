package fr.acth2.ror.entities.models;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.EntityExampleInvader;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ExampleInvaderModel extends AnimatedGeoModel<EntityExampleInvader> {

    @Override
    public ResourceLocation getModelLocation(EntityExampleInvader object) {
        return new ResourceLocation(References.MODID, "geo/invaders/example_invader.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityExampleInvader object) {
        return new ResourceLocation(References.MODID, "textures/entity/invaders/example_invader.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityExampleInvader animatable) {
        return new ResourceLocation(References.MODID, "animations/invaders/example_invader.animation.json");
    }
}