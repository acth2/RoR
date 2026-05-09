package fr.acth2.ror.entities.entity.howler;

import fr.acth2.ror.entities.constructors.grasser.GrasserEntity;
import fr.acth2.ror.entities.constructors.howler.HowlerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityHowler extends HowlerEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityHowler(EntityType<? extends HowlerEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityHowler> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.howler.move", true)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.howler.idle", true)
            );
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
