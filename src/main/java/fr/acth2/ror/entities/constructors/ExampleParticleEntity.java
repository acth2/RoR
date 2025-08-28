package fr.acth2.ror.entities.constructors;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.awt.Color;

public class ExampleParticleEntity extends MonsterEntity {

    protected ExampleParticleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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

            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    float hue = ((float)(x + 4) / 8.0f + (float)(z + 4) / 8.0f) / 2.0f;
                    Color color = Color.getHSBColor(hue, 1.0f, 1.0f);

                    float r = color.getRed() / 255.0f;
                    float g = color.getGreen() / 255.0f;
                    float b = color.getBlue() / 255.0f;

                    sendColoredParticle(serverWorld, x, 0, z, r, g, b, 2);
                }
            }
        }
        super.tick();
    }

    public void sendColoredParticle(ServerWorld world, int offsetX, int offsetY, int offsetZ,
                                    float red, float green, float blue, int count) {
        IParticleData particleData = new RedstoneParticleData(red, green, blue, 1.0f);

        world.sendParticles(
                particleData,
                this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ,
                count,
                0.1, 0.1, 0.1,
                0.05
        );
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