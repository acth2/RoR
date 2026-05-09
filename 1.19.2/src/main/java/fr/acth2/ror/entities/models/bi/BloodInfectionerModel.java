package fr.acth2.ror.entities.models.bi;

import fr.acth2.ror.entities.entity.bi.EntityBloodInfectioner;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BloodInfectionerModel extends AnimatedGeoModel<EntityBloodInfectioner> {

    @Override
    public ResourceLocation getModelLocation(EntityBloodInfectioner object) {
        return new ResourceLocation(References.MODID, "geo/blood_infectioner.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBloodInfectioner object) {
        return new ResourceLocation(References.MODID, "textures/entity/bi/blood_infectioner.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBloodInfectioner animatable) {
        return new ResourceLocation(References.MODID, "animations/blood_infectioner.animation.json");
    }
}