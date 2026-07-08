package fr.acth2.ror.entities.constructors.mimic;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MimicEntity extends Monster {

    protected int noTargetTicks = 0;
    private static final int MAX_NO_TARGET_TICKS = 100;

    private boolean isAwake = false;
    private boolean firstHit = false;

    protected MimicEntity(EntityType<? extends Monster> type, World worldIn) {
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
            this.goalSelector.addGoal(3, new LookAtGoal(this, Player.class, 8.0F));
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

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
        return ModSoundEvents.MIMIC_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.MIMIC_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.MIMIC_DIE.get();
    }

    public int getAmbientSoundInterval() {
        return 30;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    public static AttributeSupplier.MutableAttribute createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D);
    }

    public boolean isAwake() {
        return ((MimicEntity) this).isAwake;
    }

    public boolean sleep() {
        return !isAwake();
    }
}
