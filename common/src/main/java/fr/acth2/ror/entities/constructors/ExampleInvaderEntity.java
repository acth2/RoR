package fr.acth2.ror.entities.constructors;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

import javax.annotation.Nullable;

public class ExampleInvaderEntity extends MonsterEntity {
    public boolean stopEveryAnimations = false;
    public boolean triggerQuitAnim = false;
    public int spawnCooldown = 84;
    public final ServerBossInfo bossInfo = (ServerBossInfo) new ServerBossInfo(
            getDisplayName(),
            BossInfo.Color.RED,
            BossInfo.Overlay.PROGRESS
    ).setDarkenScreen(true);

    protected ExampleInvaderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.bossInfo.setVisible(false);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false) {
            @Override
            public boolean canUse() {
                return spawnCooldown <= 0 && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return spawnCooldown <= 0 && super.canContinueToUse();
            }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return spawnCooldown <= 0 && super.canUse();
            }
        });

        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide && this.tickCount % 5 == 0) {
            for (int i = 0; i < 3; i++) {
                this.level.addParticle(
                        ParticleTypes.ENTITY_EFFECT,
                        this.getRandomX(0.5D),
                        this.getRandomY(),
                        this.getRandomZ(0.5D),
                        0.8, 0.1, 0.1
                );
            }
        }

        if (!this.level.isClientSide && this.tickCount % 20 == 0) {
            this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(30))
                    .forEach(player -> {
                        player.addEffect(new EffectInstance(
                                Effects.GLOWING,
                                30,
                                0,
                                false,
                                false
                        ));
                    });
        }


        if (spawnCooldown > 0) {
            spawnCooldown--;
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
            this.xxa = 0;
            this.zza = 0;

            if (spawnCooldown == 0) {
                this.bossInfo.setVisible(true);
                // Play a sound
            }
        }
        boolean playerNearby = !this.level.getEntitiesOfClass(
                PlayerEntity.class,
                this.getBoundingBox().inflate(10)
        ).isEmpty();

        if (!playerNearby && spawnCooldown <= 0) {
            triggerQuitAnim = true;
            this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            this.goalSelector.disableControlFlag(Goal.Flag.LOOK);
        } else if (playerNearby && triggerQuitAnim) {
            triggerQuitAnim = false;
            this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
            this.goalSelector.enableControlFlag(Goal.Flag.LOOK);
            if (spawnCooldown <= 0) {
                spawnCooldown = 20;
            }
        }

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
        stopEveryAnimations = false;
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void tickDeath() {
        this.bossInfo.setPercent(0.0F);
        this.bossInfo.setVisible(false);
        super.tickDeath();
    }

    @Override
    public void remove() {
        super.remove();
        this.bossInfo.removeAllPlayers();
    }

    @Override
    public boolean isNoAi() {
        return spawnCooldown > 0 || super.isNoAi();
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return super.getHurtSound(p_184601_1_);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    public int getAmbientSoundInterval() {
        return 120;
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
                .add(Attributes.MAX_HEALTH, 85.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, Double.MAX_VALUE);
    }
}