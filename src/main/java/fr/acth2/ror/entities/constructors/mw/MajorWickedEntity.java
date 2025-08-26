package fr.acth2.ror.entities.constructors.mw;


import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.*;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class MajorWickedEntity extends MonsterEntity {

    public MajorWickedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }
    private int fireballCooldown = 0;


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
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void aiStep() {
        if (fireballCooldown <= 0 && this.canSee(this.getTarget())) {
            shootFireballAtTarget();
            fireballCooldown = 25;
        }
        super.aiStep();
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.WICKED_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.WICKED_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.WICKED_DIE.get();
    }

    public int getAmbientSoundInterval() {
        return 60;
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
            ((LivingEntity) target).addEffect(new EffectInstance(Effects.BLINDNESS, 75, 0));
            ((LivingEntity) target).addEffect(new EffectInstance(Effects.POISON, 75, 0));
        }
        return flag;
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
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

}
