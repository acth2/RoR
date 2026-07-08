package fr.acth2.ror.entities.constructors.bri;

import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BrokenInsurrectionistEntity extends Monster {

    private int fireballCooldown = 0;
    private boolean isShooting = false;

    public BrokenInsurrectionistEntity(EntityType<? extends Monster> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new BrokenInsurrectionistMovementController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(4, new LookAtGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (fireballCooldown > 0) {
            fireballCooldown--;
        }

        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 30.0F, 30.0F);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source == DamageSource.LAVA || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.BROKEN_INSURRECTIONIST_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.BROKEN_INSURRECTIONIST_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.BROKEN_INSURRECTIONIST_DIE.get();
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public void baseTick() {
        clearFire();
        this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2, 0, false, false));
        super.baseTick();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 180.0F, 180.0F);

            if (fireballCooldown <= 0 && this.canSee(this.getTarget())) {
                shootFireballAtTarget();
                fireballCooldown = 25;
            } else {
                isShooting = false;
            }

            if (this.distanceToSqr(this.getTarget()) > 4.0D) {
                this.getNavigation().moveTo(this.getTarget(), 1.0D);
            }
        }
    }

    public boolean isShooting() {
        return isShooting;
    }

    private void shootFireballAtTarget() {
        if (this.getTarget() == null) return;
        LivingEntity target = this.getTarget();
        double deltaX = target.getX() - this.getX();
        double deltaY = target.getY(0.5D) - this.getY(0.5D);
        double deltaZ = target.getZ() - this.getZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float)(Math.atan2(deltaZ, deltaX) * (180D / Math.PI)) - 90.0F;
        float pitch = (float)(-Math.atan2(deltaY, distance) * (180D / Math.PI));

        this.yRot = yaw;
        this.xRot = pitch;
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
        Vector3d vec3d = this.getViewVector(1.0F);

        double spawnX = this.getX() + vec3d.x * 1.5D;
        double spawnY = this.getY(0.5D) + 0.5D;
        double spawnZ = this.getZ() + vec3d.z * 1.5D;
        WickedProjectile fireball = new WickedProjectile(this.level, this);
        fireball.setDamage(6);

        fireball.shootFromRotation(this, pitch, yaw, 0.0F, 1.5F, 1.0F);
        fireball.setPos(spawnX, spawnY, spawnZ);
        this.level.addFreshEntity(fireball);
        isShooting = true;
    }

    public static AttributeSupplier.MutableAttribute createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    static class BrokenInsurrectionistMovementController extends MovementController {
        private final BrokenInsurrectionistEntity entity;

        public BrokenInsurrectionistMovementController(BrokenInsurrectionistEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @Override
        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vec3d = new Vector3d(this.wantedX - entity.getX(),
                        this.wantedY - entity.getY(),
                        this.wantedZ - entity.getZ());
                double d0 = vec3d.length();

                if (d0 < entity.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(vec3d.scale(this.speedModifier * 0.05D / d0)));

                    if (entity.getTarget() != null) {
                        Vector3d lookVec = new Vector3d(entity.getTarget().getX() - entity.getX(),
                                entity.getTarget().getY() - entity.getY(),
                                entity.getTarget().getZ() - entity.getZ());
                        entity.yRot = -((float)Math.atan2(lookVec.x, lookVec.z)) * (180F / (float)Math.PI);
                        entity.yBodyRot = entity.yRot;
                    }
                }
            }
        }
    }
}