package fr.acth2.ror.entities.entity.despiter;

import fr.acth2.ror.entities.constructors.despiter.DespiterEntity;
import fr.acth2.ror.entities.constructors.grasser.GrasserEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityDespiter extends DespiterEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;

    public EntityDespiter(EntityType<? extends DespiterEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityDespiter> controller = new AnimationController<>(this, "controller", 0, this::predicate);
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
                            new AnimationBuilder().addAnimation("animation.despiter.move", true)
                    );
                } else {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.despiter.idle", true)
                    );
                }
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.despiter.attack", false)
            );
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    stopEveryAnimations = true;
                    try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }
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
