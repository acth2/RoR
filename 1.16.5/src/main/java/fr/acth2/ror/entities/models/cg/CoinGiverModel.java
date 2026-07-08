package fr.acth2.ror.entities.models.cg;

import fr.acth2.ror.entities.entity.cg.EntityCoinGiver;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CoinGiverModel extends AnimatedGeoModel<EntityCoinGiver> {

    @Override
    public ResourceLocation getModelLocation(EntityCoinGiver object) {
        return new ResourceLocation(References.MODID, "geo/coin_giver.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCoinGiver object) {
        return new ResourceLocation(References.MODID, "textures/entity/cg/coin_giver.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCoinGiver animatable) {
        return new ResourceLocation(References.MODID, "animations/coin_giver.animation.json");
    }
}