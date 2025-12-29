package fr.acth2.ror.entities.constructors.spying;

import fr.acth2.ror.init.constructors.throwable.entiity.SpyingProjectile;
import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class SpyingEntity extends MonsterEntity {

    private int teleportCooldown = 0;
    private int teleportCount = 0;
    private int fireballCooldown = 0;

    protected SpyingEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }


    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return true;
    }

    @Override
    public void push(double p_70024_1_, double p_70024_3_, double p_70024_5_) { }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.teleportCooldown > 0) {
            this.teleportCooldown--;
        }

        if (fireballCooldown > 0) {
            fireballCooldown--;
        }

        if (this.getTarget() != null && this.getTarget() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.getTarget();
            double distance = this.distanceTo(player);

            if (distance < 4) {
                this.remove();
            } else if (distance < 16 && this.teleportCooldown == 0) {
                teleportAway();
            }

            Vector3d targetPos = player.position();
            Vector3d myPos = this.position();
            Vector3d direction = targetPos.subtract(myPos).normalize();
            float yaw = (float)Math.toDegrees(Math.atan2(direction.z, direction.x)) - 90f;
            float pitch = (float)Math.toDegrees(Math.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
            this.yRot = yaw;
            this.xRot = -pitch;
            this.yHeadRot = yaw;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (fireballCooldown <= 0 && this.getTarget() != null && this.canSee(this.getTarget())) {
            shootFireballAtTarget();
            fireballCooldown = 100;
        }
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
        SpyingProjectile fireball = new SpyingProjectile(this.level, this);
        fireball.setDamage(6);

        fireball.shootFromRotation(this, pitch, yaw, 0.0F, 1.5F, 1.0F);
        fireball.setPos(spawnX, spawnY, spawnZ);
        this.level.addFreshEntity(fireball);
    }

    private boolean teleportAway() {
        if (this.level.isClientSide() || !this.isAlive()) return false;

        if (this.teleportCount >= 2) {
            this.remove();
            return true;
        }

        for (int i = 0; i < 64; ++i) {
            if (tryRandomTeleport()) {
                this.teleportCooldown = 100;
                this.teleportCount++;
                return true;
            }
        }
        return false;
    }

    private boolean tryRandomTeleport() {
        Random random = this.getRandom();
        double x = this.getX() + (random.nextDouble() - 0.5D) * 32.0D;
        double y = this.getY() + (random.nextInt(16) - 8);
        double z = this.getZ() + (random.nextDouble() - 0.5D) * 32.0D;

        if (this.getTarget() != null) {
            if (new BlockPos(x, y, z).distSqr(this.getTarget().blockPosition()) < 100) {
                return false;
            }
        }

        BlockPos.Mutable mutablePos = new BlockPos.Mutable(x, y, z);

        if (this.level.getBrightness(LightType.SKY, mutablePos) > 0) {
            return false;
        }

        while (mutablePos.getY() > 0 && !this.level.getBlockState(mutablePos).getMaterial().isSolid()) {
            mutablePos.move(0, -1, 0);
        }

        if (this.level.getBlockState(mutablePos).getMaterial().isSolid()) {
            BlockPos targetPos = mutablePos.immutable();
            if (this.level.noCollision(this, this.getBoundingBox().move(targetPos.getX() + 0.5D - this.getX(), targetPos.getY() + 1 - this.getY(), targetPos.getZ() + 0.5D - this.getZ()))) {
                this.teleportTo(targetPos.getX() + 0.5D, targetPos.getY() + 1, targetPos.getZ() + 0.5D);
                return true;
            }
        }
        return false;
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.WHISP_AMBIENT.get();
    }

    public int getAmbientSoundInterval() {
        return 1;
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
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }
}
