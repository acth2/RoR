package fr.acth2.ror.entities.models.ghost;

import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.ghost.EntityGhost;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GhostModel extends AnimatedGeoModel<EntityGhost> {

    @Override
    public ResourceLocation getModelLocation(EntityGhost object) {
        return new ResourceLocation(References.MODID, "geo/ghost.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGhost object) {
        return new ResourceLocation(References.MODID, "textures/entity/ghost/ghost.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGhost animatable) {
        return new ResourceLocation(References.MODID, "animations/ghost.animation.json");
    }
}