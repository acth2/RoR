package fr.acth2.ror.entities.constructors.bri;

import fr.acth2.ror.entities.constructors.ExampleInvaderEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class BrokenInsurrectionistAttackGoal extends Goal {
    public static final float RANGED_ATTACK_MIN_RANGE = 6.0f;

    final BrokenInsurrectionistEntity mob;
    private LivingEntity target;
    private int attackTime = -1;
    private final double speedModifier;
    private int seeTime;
    private int ticksUntilNextAttack;
    public AtomicBoolean allowDistanceAttack = new AtomicBoolean(false);

    public BrokenInsurrectionistAttackGoal(BrokenInsurrectionistEntity mob, double speedIn) {
        this.mob = mob;
        this.speedModifier = speedIn;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
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

        if (d0 <= RANGED_ATTACK_MIN_RANGE) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(target, d0);
            allowDistanceAttack.set(false);
        } else {
            this.mob.getNavigation().stop();
            LivingEntity alteredTarget = target;
            alteredTarget.setPos(target.getX() + 5, target.getY(), target.getZ() + 5);
            this.mob.getNavigation().moveTo(alteredTarget, this.speedModifier);
            allowDistanceAttack.set(true);
        }
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
    }

    protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(p_190102_1_);
        if (p_190102_2_ <= d0 && this.ticksUntilNextAttack <= 0) {
            this.resetAttackCooldown();
            this.mob.swing(Hand.MAIN_HAND);
            this.mob.doHurtTarget(p_190102_1_);
        }
    }

    protected double getAttackReachSqr(LivingEntity p_179512_1_) {
        return (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_179512_1_.getBbWidth());
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = 20;
    }
}