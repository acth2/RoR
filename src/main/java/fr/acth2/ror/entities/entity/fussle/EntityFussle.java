package fr.acth2.ror.entities.entity.fussle;

import fr.acth2.ror.entities.constructors.fussle.FussleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityFussle extends FussleEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;

    public EntityFussle(EntityType<? extends FussleEntity> type, World worldIn) {
        super(type, worldIn);
        this.setGlowing(true);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityFussle> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!stopEveryAnimations) {
            if (event.isMoving()) {
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.fussle.walk", true)
                );
            } else {
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.fussle.sit", true)
                );
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}