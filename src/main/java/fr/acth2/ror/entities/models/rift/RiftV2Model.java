package fr.acth2.ror.entities.models.rift;

import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.entities.entity.rift.EntityRiftV2;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RiftV2Model extends AnimatedGeoModel<EntityRiftV2> {

    @Override
    public ResourceLocation getModelLocation(EntityRiftV2 object) {
        return new ResourceLocation(References.MODID, "geo/empty.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRiftV2 object) {
        return new ResourceLocation(References.MODID, "textures/entity/empty.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRiftV2 animatable) {
        return new ResourceLocation(References.MODID, null);
    }
}