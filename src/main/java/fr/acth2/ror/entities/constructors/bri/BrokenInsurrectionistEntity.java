package fr.acth2.ror.entities.constructors.bri;


import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BrokenInsurrectionistEntity extends MonsterEntity {

    private int fireballCooldown = 0;
    private boolean isShooting = false;

    protected BrokenInsurrectionistEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noPhysics = true;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new MeleeAttackGoal(this, 1.0D, false));

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.addBehaviourGoals();
    }

    @Override
    public void tick() {
        super.tick();

        if (fireballCooldown > 0) {
            fireballCooldown--;
        }
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
        BlockPos pos = this.blockPosition();
        return (world.getBlockState(pos.below()).getMaterial().isLiquid() ||
                world.dimensionType().ultraWarm()) &&
                super.checkSpawnRules(world, reason);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source == DamageSource.LAVA || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
            return true;
        }


        return super.isInvulnerableTo(source);
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
        this.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 2, 0, false, false));
        super.baseTick();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 180.0F, 180.0F);

            if (fireballCooldown <= 0 && this.canSee(this.getTarget())) {
                shootFireballAtTarget();
                fireballCooldown = 100;
            } else {
                isShooting = false;
            }
        }

        if (this.isInLava()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5).add(0, 0.1, 0));
        }
    }

    public boolean isShooting() {
        return isShooting;
    }

    private void shootFireballAtTarget() {
        if (this.getTarget() == null) return;

        Vector3d vec3d = this.getViewVector(1.0F);
        double d2 = this.getTarget().getX() - (this.getX() + vec3d.x * 2.0D);
        double d3 = this.getTarget().getY(0.5D) - (this.getY(0.5D) + 0.5D);
        double d4 = this.getTarget().getZ() - (this.getZ() + vec3d.z * 2.0D);

        SmallFireballEntity fireball = new SmallFireballEntity(this.level, this, d2, d3, d4);
        fireball.setPos(this.getX() + vec3d.x * 2.0D, this.getY(0.5D) + 0.5D, this.getZ() + vec3d.z * 2.0D);
        this.level.addFreshEntity(fireball);
        isShooting = true;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }
}
