package fr.acth2.ror.entities.entity.bri;

import fr.acth2.ror.entities.constructors.bri.BrokenInsurrectionistEntity;
import fr.acth2.ror.entities.constructors.lavabeing.LavaBeingEntity;
import fr.acth2.ror.entities.entity.lb.EntityLavaBeing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityBrokenInsurrectionist extends BrokenInsurrectionistEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public static boolean startShooting = false;
    private boolean stopEveryAnimations = false;

    public EntityBrokenInsurrectionist(EntityType<? extends BrokenInsurrectionistEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityBrokenInsurrectionist> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (startShooting || isShooting() && !stopEveryAnimations) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.broken_insurrectionist.magic", true)
            );
        } else if (isAttacking() && !stopEveryAnimations) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.broken_insurrectionist.melee", true)
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
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.broken_insurrectionist.idle", true)
            );
        }
        return PlayState.CONTINUE;
    }

    @Override
    public boolean doHurtTarget(Entity p_70652_1_) {
        startShooting = true;
        return super.doHurtTarget(p_70652_1_);
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}