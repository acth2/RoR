package fr.acth2.ror.entities.entity.lc;

import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityLostCaver extends LostCaverEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityLostCaver(EntityType<? extends LostCaverEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityLostCaver> defaultController = new AnimationController<>(this, "defaultController", 0, this::defaultPredicate);
        data.addAnimationController(defaultController);
    }


    private <E extends IAnimatable> PlayState defaultPredicate(AnimationEvent<E> event) {
        setGlowing(true);
        if (!isAttacking()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lc.walk", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lc.seek", true));
            }

        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lc.attack", false));
        }
        return PlayState.CONTINUE;
    }



    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
