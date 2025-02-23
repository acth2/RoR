package fr.acth2.ror.entities.entity.hopper;

import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityHopper extends HopperEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;

    public EntityHopper(EntityType<? extends HopperEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityHopper> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        this.setGlowing(true);
        if (!isAttacking()) {
            if (!stopEveryAnimations) {
                if (event.isMoving()) {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.hopper.walk", true)
                    );
                } else {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.hopper.idle", true)
                    );
                }
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.hopper.attack", false)
            );
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    stopEveryAnimations = true;
                    try { Thread.sleep(1250); } catch (InterruptedException e) { throw new RuntimeException(e); }
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
