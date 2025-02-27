package fr.acth2.ror.entities.models.ws;

import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.ws.EntityWoodSpirit;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WoodSpiritModel  extends AnimatedGeoModel<EntityWoodSpirit> {

    @Override
    public ResourceLocation getModelLocation(EntityWoodSpirit object) {
        return new ResourceLocation(References.MODID, "geo/wood_spirit.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWoodSpirit object) {
        return new ResourceLocation(References.MODID, "textures/entity/ws/wood_spirit.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWoodSpirit animatable) {
        return new ResourceLocation(References.MODID, "animations/wood_spirit.animation.json");
    }
}