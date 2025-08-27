package fr.acth2.ror.entities.models.rift;

import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RiftModel extends AnimatedGeoModel<EntityRift> {

    @Override
    public ResourceLocation getModelLocation(EntityRift object) {
        return new ResourceLocation(References.MODID, "geo/empty.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRift object) {
        return new ResourceLocation(References.MODID, "textures/entity/empty.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRift animatable) {
        return new ResourceLocation(References.MODID, null);
    }
}