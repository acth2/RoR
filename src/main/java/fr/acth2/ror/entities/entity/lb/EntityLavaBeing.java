package fr.acth2.ror.entities.entity.lb;

import fr.acth2.ror.entities.constructors.lavabeing.LavaBeingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityLavaBeing extends LavaBeingEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityLavaBeing(EntityType<? extends LavaBeingEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityLavaBeing> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isAttacking()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.lava_being.shoot", true)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.lava_being.idle", true)
            );
        }
        return PlayState.CONTINUE;
    }



    private boolean isAttacking() {
        return this.getTarget() != null;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
