package fr.acth2.ror.entities.entity;

import fr.acth2.ror.entities.constructors.ExampleInvaderEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import static fr.acth2.ror.entities.constructors.RangedAttackGoal.ATTACK_COOLDOWN_TIME;

public class EntityExampleInvader extends ExampleInvaderEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private int quitAnimTimer = 0;
    private boolean isReturning = false;

    public EntityExampleInvader(EntityType<? extends ExampleInvaderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (isReturning && quitAnimTimer > 0) {
            quitAnimTimer--;
            if (quitAnimTimer <= 0) {
                isReturning = false;
                bossInfo.setVisible(false);
            }
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean shouldBlockExplode(Explosion p_174816_1_, IBlockReader p_174816_2_, BlockPos p_174816_3_, BlockState p_174816_4_, float p_174816_5_) {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.spawnCooldown > 0) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.invader.spawn", true)
            );
            return PlayState.CONTINUE;
        }

        if (this.triggerQuitAnim) {
            if (!isReturning) {
                isReturning = true;
                quitAnimTimer = 15;
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.invader.returning", false)
                );
            }
            return PlayState.CONTINUE;
        }

        if (((ExampleInvaderEntity)event.getAnimatable()).isShooting) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.invader.shoot", false)
            );
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.invader.move", true)
            );
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(
                new AnimationBuilder().addAnimation("animation.invader.idle", true)
        );
        return PlayState.CONTINUE;
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason spawnReason) {
        return super.checkSpawnRules(world, spawnReason);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}