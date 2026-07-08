package fr.acth2.ror.entities.entity.creckon;

import fr.acth2.ror.entities.constructors.creckon.CreckonEntity;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityCreckon extends CreckonEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityCreckon(EntityType<? extends CreckonEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityCreckon> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.creckon.move", true)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.creckon.idle", true)
            );
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
