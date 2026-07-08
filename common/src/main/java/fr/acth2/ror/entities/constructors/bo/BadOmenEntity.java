package fr.acth2.ror.entities.constructors.bo;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BadOmenEntity extends CreeperEntity implements IChargeableMob {
    private int noSeeTicks = 0;
    private static final int MAX_NO_SEE_TICKS = 20;
    private static final int BLINDNESS_DURATION = 100;
    private static final int BLINDNESS_AMPLIFIER = 0;
    public BadOmenEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 10, true);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new FlyRandomlyGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity player = this.level.getNearestPlayer(this, 15.0D);
        if (player != null && this.canSee(player)) {
            noSeeTicks = 0;
            this.getNavigation().moveTo(player, 1.2D);
        } else {
            noSeeTicks++;
        }

        this.setNoGravity(noSeeTicks > MAX_NO_SEE_TICKS);
    }
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.BADOMEN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.BADOMEN_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.BADOMEN_DIE.get();
    }

    public int getAmbientSoundInterval() {
        return 30;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(Effects.BLINDNESS, BLINDNESS_DURATION, BLINDNESS_AMPLIFIER));
            this.playSound(ModSoundEvents.RUSTEDCORE_HIT.get(), 1.0F, 1.0F);
            return true;
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8D));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            if (noSeeTicks > MAX_NO_SEE_TICKS) {
                this.moveRelative(this.getSpeed(), travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.91D));
            } else {
                super.travel(travelVector);
            }
        }
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    static class FlyRandomlyGoal extends Goal {
        private final BadOmenEntity entity;

        public FlyRandomlyGoal(BadOmenEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return entity.noSeeTicks > entity.MAX_NO_SEE_TICKS && entity.getNavigation().isDone();
        }

        @Override
        public void tick() {
            if (entity.getRandom().nextInt(50) == 0) {
                double x = entity.getX() + (entity.getRandom().nextFloat() * 2.0F - 1.0F) * 5.0F;
                double y = entity.getY() + (entity.getRandom().nextFloat() * 2.0F - 1.0F) * 5.0F;
                double z = entity.getZ() + (entity.getRandom().nextFloat() * 2.0F - 1.0F) * 5.0F;

                int minY = 0;
                int maxY = 256;
                y = Math.min(Math.max(y, minY + 5), maxY - 5);

                entity.getNavigation().moveTo(x, y, z, 1.0D);
            }
        }
    }
}