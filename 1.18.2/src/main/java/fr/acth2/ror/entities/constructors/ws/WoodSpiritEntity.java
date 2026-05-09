package fr.acth2.ror.entities.constructors.ws;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class WoodSpiritEntity extends MonsterEntity {

    public WoodSpiritEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.WOODSPIRIT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.WOODSPIRIT_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.WOODSPIRIT_DIE.get();
    }

    public int getAmbientSoundInterval() {
        return 30;
    }

    private static AtomicBoolean giveCoinsOnce = new AtomicBoolean(true);

    @Override
    public void tick() {
        super.tick();
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
            ((LivingEntity) target).addEffect(new EffectInstance(Effects.LEVITATION, 25, 3));
        }
        return flag;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
}
