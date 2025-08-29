package fr.acth2.ror.entities.constructors.lp;

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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LivingParticleEntity extends MonsterEntity {
    private int colorCycle = 0;
    private final int COLOR_CYCLE_SPEED = 2;

    protected LivingParticleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
    public void push(double p_70024_1_, double p_70024_3_, double p_70024_5_) { }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    public void tick() {
        if (this.level.isClientSide) {
            colorCycle = (colorCycle + COLOR_CYCLE_SPEED) % 360;
            float[] rgb = getRGBFromHue(colorCycle);

            spawnChromaParticles(rgb[0], rgb[1], rgb[2]);
        }

        if (!this.level.isClientSide) {
            if (this.tickCount % 20 == 0) {
                List<PlayerEntity> players = this.level.getEntitiesOfClass(
                        PlayerEntity.class,
                        this.getBoundingBox().inflate(10.0D),
                        player -> true
                );

                for (PlayerEntity player : players) {
                    player.addEffect(new EffectInstance(
                            Effects.REGENERATION,
                            100,
                            0
                    ));
                    player.addEffect(new EffectInstance(
                            Effects.DAMAGE_BOOST,
                            100,
                            0
                    ));
                }
            }
        }
        super.tick();
    }

    public void sendColoredParticle(ServerWorld world, int offsetX, int offsetY, int offsetZ,
                                    float red, float green, float blue, int count, int size) {
        IParticleData particleData = new RedstoneParticleData(red, green, blue, size);

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
        return ModSoundEvents.LIVING_PARTICLE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.LIVING_PARTICLE_DIE.get();
    }

    private float[] getRGBFromHue(int hue) {
        float h = hue / 360.0f;
        int i = (int) (h * 6);
        float f = h * 6 - i;
        float p = 0;
        float q = 1 - f;
        float t = f;

        float r, g, b;
        switch (i % 6) {
            case 0: r = 1; g = t; b = p; break;
            case 1: r = q; g = 1; b = p; break;
            case 2: r = p; g = 1; b = t; break;
            case 3: r = p; g = q; b = 1; break;
            case 4: r = t; g = p; b = 1; break;
            default: r = 1; g = p; b = q; break;
        }

        return new float[]{r, g, b};
    }

    private void spawnChromaParticles(float r, float g, float b) {
        IParticleData particleData = new RedstoneParticleData(r, g, b, 4.0f);
        for (int i = 0; i < 5; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;

            this.level.addParticle(
                    particleData,
                    this.getX() + offsetX,
                    this.getY() + offsetY + 0.5,
                    this.getZ() + offsetZ,
                    0, 0, 0
            );
        }
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
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }
}
