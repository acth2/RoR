package fr.acth2.ror.init.constructors.throwable.entiity;

import fr.acth2.ror.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WickedProjectile extends ThrowableEntity {

    private float damage = 12.0F;

    public WickedProjectile(EntityType<? extends WickedProjectile> type, World world) {
        super(type, world);
    }

    public WickedProjectile(World world, LivingEntity shooter) {
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
            entity.hurt(DamageSource.MAGIC, damage); // Apply magic damage
        }
        this.remove(); // Remove the projectile after hitting an entity
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive()) {
            ServerWorld serverWorld = (ServerWorld) this.level;
            serverWorld.sendParticles(
                    ParticleTypes.ENCHANT,
                    this.getX(), this.getY(), this.getZ(),
                    10,
                    0.2, 0.2, 0.2,
                    0.1
            );

            serverWorld.sendParticles(
                    ParticleTypes.WITCH,
                    this.getX(), this.getY(), this.getZ(),
                    50,
                    0.5, 0.5, 0.5,
                    0.2
            );
        }
    }
}