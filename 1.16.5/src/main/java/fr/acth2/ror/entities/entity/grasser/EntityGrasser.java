package fr.acth2.ror.entities.entity.grasser;

import fr.acth2.ror.entities.constructors.grasser.GrasserEntity;
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

public class EntityGrasser extends GrasserEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;

    public EntityGrasser(EntityType<? extends GrasserEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityGrasser> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!isAttacking()) {
            if (!stopEveryAnimations) {
                if (event.isMoving()) {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.grasser.walk", true)
                    );
                } else {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.grasser.idle", true)
                    );
                }
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.grasser.attack", false)
            );
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    stopEveryAnimations = true;
                    try { Thread.sleep(500); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    stopEveryAnimations = false;
                }
            }.start();
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
