package fr.acth2.ror.entities.entity.rc;

import fr.acth2.ror.entities.constructors.rc.RustedCoreEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityRustedCore extends RustedCoreEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityRustedCore(EntityType<? extends RustedCoreEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityRustedCore> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        EntityRustedCore entity = (EntityRustedCore) event.getAnimatable();

        if (entity.getSwellDir() <= 0) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.rc.idle", true)
            );
        } else {
            this.playSound(ModSoundEvents.RUSTEDCORE_EXPLODE.get(), 2.0F, 1.0F);
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.rc.explode", false)
            );
        }

        return PlayState.CONTINUE;
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_)
                || false;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }
}
