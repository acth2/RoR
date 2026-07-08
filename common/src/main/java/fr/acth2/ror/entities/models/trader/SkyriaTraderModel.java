package fr.acth2.ror.entities.models.trader;

import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.entities.entity.traders.EntitySkyriaTrader;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkyriaTraderModel extends AnimatedGeoModel<EntitySkyriaTrader> {

    @Override
    public ResourceLocation getModelLocation(EntitySkyriaTrader object) {
        return new ResourceLocation(References.MODID, "geo/trader.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySkyriaTrader object) {
        return new ResourceLocation(References.MODID, "textures/entity/traders/skyria_trader.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySkyriaTrader animatable) {
        return new ResourceLocation(References.MODID, "animations/trader.animation.json");
    }
}