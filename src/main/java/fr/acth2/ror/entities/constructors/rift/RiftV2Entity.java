package fr.acth2.ror.entities.constructors.rift;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class RiftV2Entity extends MonsterEntity {

    protected RiftV2Entity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
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
        if (!this.level.isClientSide && this.isAlive()) {
            ServerWorld serverWorld = (ServerWorld) this.level;
            serverWorld.sendParticles(
                    ParticleTypes.ASH,
                    this.getX(), this.getY(), this.getZ(),
                    10,
                    0.2, 0.2, 0.2,
                    0.1
            );

            serverWorld.sendParticles(
                    ParticleTypes.BUBBLE_POP,
                    this.getX(), this.getY(), this.getZ(),
                    125,
                    0.2, 0.2, 0.2,
                    0.1
            );

            serverWorld.sendParticles(
                    ParticleTypes.BUBBLE_POP,
                    this.getX(), this.getY() + 0.2, this.getZ(),
                    125,
                    0.2, 0.2, 0.2,
                    0.1
            );

            serverWorld.sendParticles(
                    ParticleTypes.BUBBLE_POP,
                    this.getX(), this.getY() + 0.4, this.getZ(),
                    125,
                    0.2, 0.2, 0.2,
                    0.1
            );
        }
        super.tick();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.WHISP_AMBIENT.get();
    }

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
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }
}
