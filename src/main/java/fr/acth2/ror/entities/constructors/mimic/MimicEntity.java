package fr.acth2.ror.entities.constructors.mimic;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MimicEntity extends MonsterEntity {

    protected int noTargetTicks = 0;
    private static final int MAX_NO_TARGET_TICKS = 100;

    private boolean isAwake = false;
    private boolean firstHit = false;

    protected MimicEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    public void activate() {
        if (!isAwake) {
            this.isAwake = true;

            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
            this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
            this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

            this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
            this.goalSelector.enableControlFlag(Goal.Flag.LOOK);

            if (this.firstHit) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0D);
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0D);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!firstHit) {
            firstHit = true;
            activate();
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isAwake) {
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
            this.navigation.stop();
            this.setAggressive(false);
        }

        if (this.isAwake()) {
            if (this.getTarget() == null) {
                noTargetTicks++;

                if (noTargetTicks >= MAX_NO_TARGET_TICKS) {
                    disappearAngrily();
                }
            } else {
                noTargetTicks = 0;
            }
        }
    }

    private void disappearAngrily() {
        if (!this.level.isClientSide) {
            for (int i = 0; i < 10; i++) {
                double x = this.getX() + (this.random.nextDouble() - 0.5D) * 0.5D;
                double y = this.getY() + (this.random.nextDouble() - 0.5D) * 0.5D;
                double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 0.5D;
                this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 5, 5);
            }
            this.level.playSound(null, this.blockPosition(), SoundEvents.VILLAGER_NO, SoundCategory.HOSTILE, 1.0f, 0.8f);

            this.remove();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return super.getHurtSound(p_184601_1_);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    public boolean isAwake() {
        return ((MimicEntity) this).isAwake;
    }

    public boolean sleep() {
        return !isAwake();
    }
}
