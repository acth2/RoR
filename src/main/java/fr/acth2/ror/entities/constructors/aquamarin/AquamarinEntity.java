package fr.acth2.ror.entities.constructors.aquamarin;

import fr.acth2.ror.entities.constructors.WaterMonsterEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AquamarinEntity extends WaterMonsterEntity {

    public AquamarinEntity(EntityType<? extends WaterMonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.5D, 10));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.5D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        if (target != null) {
            double deltaX = target.getX() - this.getX();
            double deltaY = target.getY() - this.getY();
            double deltaZ = target.getZ() - this.getZ();

            if (deltaY > 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0.03, 0));
            } else if (deltaY < 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.03, 0));
            }

            if (deltaX > 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.03, 0, 0));
            } else if (deltaX < 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(-0.03, 0, 0));
            }

            if (deltaZ > 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0, 0.03));
            } else if (deltaZ < 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0, -0.03));
            }

            this.yRot = -((float) MathHelper.atan2(deltaX, deltaZ)) * (180F / (float) Math.PI);
            this.yBodyRot = this.yRot;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.AQUAMARIN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.AQUAMARIN_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.AQUAMARIN_DIE.get();
    }

    @Override
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

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.5D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }
}