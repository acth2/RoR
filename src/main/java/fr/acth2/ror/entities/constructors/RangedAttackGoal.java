package fr.acth2.ror.entities.constructors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RangedAttackGoal extends Goal {

    private int attackCooldown = 0;
    public static final int ATTACK_COOLDOWN_TIME = 40;

    public static final float RANGED_ATTACK_RANGE = 20.0f;
    public static final float RANGED_ATTACK_MIN_RANGE = 6.0f;

    final ExampleInvaderEntity mob;
    private LivingEntity target;
    private int attackTime = -1;
    private final double speedModifier;
    private int seeTime;

    public RangedAttackGoal(ExampleInvaderEntity mob, double speedIn) {
        this.mob = mob;
        this.speedModifier = speedIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob.spawnCooldown > 0) return false;

        LivingEntity livingentity = mob.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.target = livingentity;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public void tick() {
        double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean canSee = this.mob.getSensing().canSee(this.target);

        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (d0 <= RANGED_ATTACK_MIN_RANGE * RANGED_ATTACK_MIN_RANGE || this.seeTime < 5) {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        } else {
            this.mob.getNavigation().stop();
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
    }
}