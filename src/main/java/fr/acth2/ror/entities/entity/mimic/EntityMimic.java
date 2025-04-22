package fr.acth2.ror.entities.entity.mimic;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.mimic.MimicEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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

public class EntityMimic extends MimicEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean wasRightClicked = false;
    private int attackCooldown = 0;

    public EntityMimic(EntityType<? extends MimicEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityMimic> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (hurtTime > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.attacked", false));
            return PlayState.CONTINUE;
        }

        if (!isAwake()) {
            if (wasRightClicked) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.trapped", true));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.sleep", true));
                return PlayState.CONTINUE;
            }
        }

        if (isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.attack", false));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mimic.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if (!this.isAwake()) {
            this.wasRightClicked = true;
            this.activate();

            player.hurt(DamageSource.mobAttack(this), 10.0F);
            attackCooldown = 20;

            return ActionResultType.SUCCESS;
        }
        return super.interactAt(player, vec, hand);
    }


    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target != null) {
            this.noTargetTicks = 0;
        }
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_)
                || false;
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (!isAwake() && wasRightClicked && attackCooldown <= 0) {
            PlayerEntity player = this.level.getNearestPlayer(this, 2.0);
            if (player != null) {
                player.hurt(DamageSource.mobAttack(this), 10.0F);
                attackCooldown = 20;
            }
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
