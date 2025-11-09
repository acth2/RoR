package fr.acth2.ror.entities.constructors.spying;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.awt.*;

public class SpyingEntity extends MonsterEntity {

    protected SpyingEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
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
        if (this.getTarget() != null) {
            Vector3d targetPos = this.getTarget().position();
            Vector3d myPos = this.position();

            Vector3d direction = targetPos.subtract(myPos).normalize();

            double dx = direction.x;
            double dy = direction.y;
            double dz = direction.z;

            float yaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90f;
            float pitch = (float)Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

            this.yRot = yaw;
            this.xRot = pitch;

            this.yHeadRot = yaw;
            if (this.distanceTo(this.getTarget()) < 6) {
                this.kill();
                this.setInvisible(true);
            }
        }
        super.tick();
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
                .add(Attributes.ATTACK_DAMAGE, 225.0D);
    }
}