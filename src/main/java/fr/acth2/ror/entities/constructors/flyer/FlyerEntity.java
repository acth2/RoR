package fr.acth2.ror.entities.constructors.flyer;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
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
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.List;
import java.util.EnumSet;
import java.util.Random;

public class FlyerEntity extends MonsterEntity implements IFlyingAnimal {
    private static final int BLINDNESS_DURATION = 100;
    private static final int BLINDNESS_AMPLIFIER = 0;
    private static final double AGGRO_RANGE = 20.0D;
    private static final double ATTACK_RANGE = 2.0D;
    private static final double MIN_ALTITUDE = 5.0D;
    private static final double CIRCLE_RADIUS = 5.0D;
    public Random random = new Random();

    private int circlingCooldown = 0;
    private Vector3d circlingCenter = null;

    public FlyerEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.setNoGravity(true);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingPathNavigator = new FlyingPathNavigator(this, world);
        flyingPathNavigator.setCanOpenDoors(false);
        flyingPathNavigator.setCanFloat(true);
        flyingPathNavigator.setCanPassDoors(true);
        return flyingPathNavigator;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FlyerAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(2, new MaintainAltitudeGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new FlyerCircleGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isOnGround()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));
        }

        if (circlingCooldown > 0) {
            circlingCooldown--;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.FLYER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.FLYER_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.FLYER_DIE.get();
    }

    @Override
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
            player.addEffect(new EffectInstance(Effects.NIGHT_VISION, BLINDNESS_DURATION, BLINDNESS_AMPLIFIER));
            this.playSound(ModSoundEvents.RUSTEDCORE_HIT.get(), 1.0F, 1.0F);
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
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.91D));
        }
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.FLYING_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, AGGRO_RANGE);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private int countAttackers(PlayerEntity player) {
        int count = 0;
        List<FlyerEntity> flyers = this.level.getEntitiesOfClass(FlyerEntity.class,
                this.getBoundingBox().inflate(AGGRO_RANGE));

        for (FlyerEntity flyer : flyers) {
            if (flyer.getTarget() == player && flyer != this) {
                count++;
            }
        }
        return count;
    }

    boolean isTooCloseToGround() {
        return this.getY() - this.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, this.blockPosition()).getY() < MIN_ALTITUDE;
    }

    private void flyToRandomHeight() {
        double x = this.getX() + (this.random.nextDouble() - 0.5) * 5.0;
        double y = this.getY() + 5.0 + this.random.nextDouble() * 10.0;
        double z = this.getZ() + (this.random.nextDouble() - 0.5) * 5.0;
        this.getNavigation().moveTo(x, y, z, 1.0D);
    }

    static class FlyerAttackGoal extends Goal {
        private final FlyerEntity flyer;
        private LivingEntity target;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private final double attackInterval = 20;
        private long lastCanUseCheck;
        private int diveCooldown = 0;
        private boolean isDiving = false;

        public FlyerAttackGoal(FlyerEntity flyer, double speedModifier, boolean followWhenUnseen) {
            this.flyer = flyer;
            this.speedModifier = speedModifier;
            this.followingTargetEvenIfNotSeen = followWhenUnseen;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            long gameTime = this.flyer.level.getGameTime();
            if (gameTime - this.lastCanUseCheck < 20L) {
                return false;
            }
            this.lastCanUseCheck = gameTime;

            this.target = this.flyer.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.target != null && this.target.isAlive() &&
                    (this.followingTargetEvenIfNotSeen || this.flyer.canSee(this.target));
        }

        @Override
        public void start() {
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.isDiving = false;
            this.diveCooldown = 0;
        }

        @Override
        public void stop() {
            this.target = null;
            this.flyer.getNavigation().stop();
            this.isDiving = false;
        }

        @Override
        public void tick() {
            if (this.target == null) return;

            int attackers = flyer.countAttackers((PlayerEntity) target);
            boolean shouldCircle = attackers >= 3;

            this.flyer.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

            if (shouldCircle) {
                if (diveCooldown > 0) {
                    diveCooldown--;
                }

                if (!isDiving) {
                    if (--this.ticksUntilNextPathRecalculation <= 0) {
                        this.ticksUntilNextPathRecalculation = 10 + this.flyer.getRandom().nextInt(10);

                        double angle = flyer.random.nextDouble() * Math.PI * 2;
                        double x = this.target.getX() + Math.cos(angle) * 5.0;
                        double y = this.target.getY() + 5.0 + flyer.random.nextDouble() * 3.0;
                        double z = this.target.getZ() + Math.sin(angle) * 5.0;

                        this.flyer.getNavigation().moveTo(x, y, z, this.speedModifier * 0.7);

                        if (diveCooldown <= 0 && flyer.random.nextInt(100) < 5) {
                            isDiving = true;
                        }
                    }
                } else {
                    double distanceSq = this.flyer.distanceToSqr(this.target);

                    if (distanceSq > 9.0) {
                        double x = this.target.getX();
                        double y = this.target.getY() + 1.0;
                        double z = this.target.getZ();
                        this.flyer.getNavigation().moveTo(x, y, z, this.speedModifier * 1.3);
                    } else {
                        this.checkAndPerformAttack(this.target, distanceSq);

                        isDiving = false;
                        diveCooldown = 60 + flyer.random.nextInt(80);
                    }
                }
            } else {
                double distanceSq = this.flyer.distanceToSqr(this.target);

                if (--this.ticksUntilNextPathRecalculation <= 0) {
                    this.ticksUntilNextPathRecalculation = 4 + this.flyer.getRandom().nextInt(7);

                    Vector3d targetLook = this.target.getLookAngle();
                    double x = this.target.getX() - targetLook.x * 2.0;
                    double y = this.target.getY() + 2.5D;
                    double z = this.target.getZ() - targetLook.z * 2.0;

                    if (!this.flyer.getNavigation().moveTo(x, y, z, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }
                }

                this.checkAndPerformAttack(this.target, distanceSq);
            }
        }

        protected void checkAndPerformAttack(LivingEntity target, double distanceSq) {
            double attackRange = ATTACK_RANGE * ATTACK_RANGE;
            if (distanceSq <= attackRange && this.ticksUntilNextAttack <= 0) {
                this.ticksUntilNextAttack = (int)this.attackInterval;
                this.flyer.doHurtTarget(target);
            }
        }
    }

    static class FlyerCircleGoal extends Goal {
        private final FlyerEntity flyer;
        private double angle = 0;
        private double circleHeight = 0;

        public FlyerCircleGoal(FlyerEntity flyer) {
            this.flyer = flyer;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return flyer.circlingCenter != null && flyer.circlingCooldown > 0;
        }

        @Override
        public void start() {
            this.angle = flyer.random.nextDouble() * Math.PI * 2;
            this.circleHeight = flyer.circlingCenter.y + 5 + flyer.random.nextDouble() * 5;
        }

        @Override
        public void tick() {
            if (flyer.circlingCenter == null) return;

            angle += 0.05;          if (angle > Math.PI * 2) angle -= Math.PI * 2;

            double x = flyer.circlingCenter.x + Math.cos(angle) * CIRCLE_RADIUS;
            double z = flyer.circlingCenter.z + Math.sin(angle) * CIRCLE_RADIUS;

            double y = circleHeight + Math.sin(angle * 2) * 1.5;

                        flyer.getNavigation().moveTo(x, y, z, 1.0D);

            flyer.getLookControl().setLookAt(
                    flyer.circlingCenter.x,
                    flyer.circlingCenter.y + 1.5,
                    flyer.circlingCenter.z
            );
        }

        @Override
        public void stop() {
            flyer.circlingCenter = null;
        }
    }
} class MaintainAltitudeGoal extends Goal {
    private final FlyerEntity flyer;
    private int checkCooldown = 0;

    public MaintainAltitudeGoal(FlyerEntity flyer) {
        this.flyer = flyer;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (flyer.getTarget() != null || checkCooldown > 0) {
            return false;
        }
        return flyer.isTooCloseToGround();
    }

    @Override
    public boolean canContinueToUse() {
        return flyer.getTarget() == null &&
                flyer.isTooCloseToGround() &&
                !flyer.getNavigation().isDone();
    }

    @Override
    public void start() {
        double x = flyer.getX() + (flyer.random.nextDouble() - 0.5) * 5.0;
        double y = flyer.getY() + 5.0 + flyer.random.nextDouble() * 10.0;
        double z = flyer.getZ() + (flyer.random.nextDouble() - 0.5) * 5.0;

        flyer.getNavigation().moveTo(x, y, z, 1.0D);
        checkCooldown = 20;     }

    @Override
    public void tick() {
        if (checkCooldown > 0) {
            checkCooldown--;
        }

        if (flyer.getNavigation().isDone() && flyer.isTooCloseToGround()) {
            start();
        }
    }

    @Override
    public void stop() {
        checkCooldown = 20;
    }
}