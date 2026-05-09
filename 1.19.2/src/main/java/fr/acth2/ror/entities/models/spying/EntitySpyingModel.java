package fr.acth2.ror.entities.models.spying;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.entities.entity.spying.EntitySpying;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EntitySpyingModel extends AnimatedGeoModel<EntitySpying> {

    @Override
    public ResourceLocation getModelLocation(EntitySpying object) {
        return new ResourceLocation(References.MODID, "geo/spying.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySpying object) {
        return new ResourceLocation(References.MODID, "textures/entity/spying/spying.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySpying animatable) {
        return new ResourceLocation(References.MODID, null);
    }
}