package fr.acth2.ror.entities.goals;

import fr.acth2.ror.init.constructors.items.WickedStaff;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.MathHelper;
import java.util.EnumSet;

public class RangedWickedAttackGoal<T extends MonsterEntity & IRangedAttackMob> extends Goal {
    private final T mob;
    private final double moveSpeed;
    private int attackCooldown;
    private final float maxAttackDistance;
    private final float minAttackDistance = 5.0F;
    private int attackTime = -1;
    private int strafeTime = 0;
    private boolean strafeLeft;

    public RangedWickedAttackGoal(T mob, double moveSpeed, int attackCooldown, float maxAttackDistance) {
        this.mob = mob;
        this.moveSpeed = moveSpeed;
        this.attackCooldown = attackCooldown;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive() && this.isHoldingStaff();
    }

    private boolean isHoldingStaff() {
        return this.mob.isHolding(item -> item instanceof WickedStaff);
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) return;

        double distanceSq = this.mob.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
        boolean canSee = this.mob.getSensing().canSee(target);
        boolean inRange = distanceSq <= this.maxAttackDistance && distanceSq >= (minAttackDistance * minAttackDistance);

        if (!inRange) {
            this.mob.getNavigation().moveTo(target, this.moveSpeed);
            this.strafeTime = 0;
        } else if (canSee) {
            this.mob.getNavigation().stop();

            if (--this.strafeTime <= 0) {
                this.strafeLeft = this.mob.getRandom().nextBoolean();
                this.strafeTime = 20 + this.mob.getRandom().nextInt(20);
            }

            float strafeDirection = this.strafeLeft ? 90 : -90;
            float angle = (float)Math.toRadians(this.mob.yRot + strafeDirection);
            double strafeX = MathHelper.sin(angle) * 0.5;
            double strafeZ = MathHelper.cos(angle) * 0.5;

            this.mob.getMoveControl().strafe((float)strafeX, (float)strafeZ);
            this.mob.lookAt(target, 30.0F, 30.0F);
        }

        if (--this.attackTime <= 0 && inRange && canSee) {
            float distanceFactor = MathHelper.sqrt(distanceSq) / (float)MathHelper.sqrt(this.maxAttackDistance);
            this.mob.performRangedAttack(target, distanceFactor);
            this.attackTime = this.attackCooldown;
        }
    }
}