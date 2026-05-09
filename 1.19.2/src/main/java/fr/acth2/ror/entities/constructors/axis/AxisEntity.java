package fr.acth2.ror.entities.constructors.axis;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AxisEntity extends MonsterEntity {
    private static final double ATTRACTION_STRENGTH = 0.15D;
    private static final int PULL_DURATION = 500;
    private static final int COOLDOWN_DURATION = 5000;

    private long pullingStartTime = -1;
    private boolean isPulling = false;
    private boolean isVulnerable = false;

    public AxisEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.AXIS_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.AXIS_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.AXIS_DIE.get();
    }

    public int getAmbientSoundInterval() {
        return 60;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide && this.isAlive() && this.getTarget() != null) {
            boolean hasNearbyAxisEntities = !this.level.getEntitiesOfClass(
                    AxisEntity.class,
                    this.getBoundingBox().inflate(16.0),
                    entity -> entity != this && entity.isAlive()
            ).isEmpty();

            if (!hasNearbyAxisEntities) {
                LivingEntity target = this.getTarget();
                long currentTime = System.currentTimeMillis();

                if (pullingStartTime == -1) {
                    pullingStartTime = currentTime;
                    isPulling = true;
                }

                long elapsed = currentTime - pullingStartTime;

                if (isPulling) {
                    if (elapsed < PULL_DURATION) {
                        isVulnerable = true;

                        double deltaX = this.getX() - target.getX();
                        double deltaY = this.getY() - target.getY();
                        double deltaZ = this.getZ() - target.getZ();

                        double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                        if (distanceSquared > 0) {
                            double distance = Math.sqrt(distanceSquared);
                            double moveX = deltaX / distance * ATTRACTION_STRENGTH;
                            double moveY = deltaY / distance * ATTRACTION_STRENGTH;
                            double moveZ = deltaZ / distance * ATTRACTION_STRENGTH;

                            target.teleportTo(
                                    target.getX() + moveX,
                                    target.getY() + moveY,
                                    target.getZ() + moveZ
                            );
                        }
                    } else {
                        isPulling = false;
                        isVulnerable = false;
                        pullingStartTime = currentTime;
                    }
                } else {
                    if (elapsed >= COOLDOWN_DURATION) {
                        isPulling = true;
                        pullingStartTime = currentTime;
                    }
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isVulnerable) {
            return super.hurt(source, amount * 2);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean flag = super.doHurtTarget(target);
        if (flag && target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 75, 2));
        }
        return flag;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0D);
    }
}