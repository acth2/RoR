package fr.acth2.ror.init.constructors.throwable.entiity;

import fr.acth2.ror.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpyingProjectile extends ThrowableEntity {

    private float damage = 12.0F;

    public SpyingProjectile(EntityType<? extends SpyingProjectile> type, World world) {
        super(type, world);
    }

    public SpyingProjectile(World world, LivingEntity shooter) {
        super(ModEntities.WICKED_PROJECTILE.get(), shooter, world);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) result.getEntity();
            entity.hurt(DamageSource.MAGIC, damage);
        }
        this.remove();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive()) {
            ServerWorld serverWorld = (ServerWorld) this.level;
            IParticleData particleData = new RedstoneParticleData(0, 0, 0, 10.0f);

            serverWorld.sendParticles(
                    particleData,
                    this.getX(), this.getY(), this.getZ(),
                    5,
                    0.1, 0.1, 0.1,
                    0.05
            );
        }
    }
}