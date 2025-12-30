package fr.acth2.ror.entities.constructors.catapleer;

import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class CatapleerEntity extends MonsterEntity {

    public CatapleerEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            List<PlayerEntity> players = this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(3.0D));
            for (PlayerEntity player : players) {
                if (player.swinging && this.distanceToSqr(player) < 4.0D) {
                    ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 15, 0.5, 0.5, 0.5, 0.0);
                    this.remove();

                    int coinsToAdd = 50;
                    CoinsManager.addCoins((ServerPlayerEntity) player, coinsToAdd);
                    CoinsManager.syncCoins((ServerPlayerEntity) player);

                    return;
                }
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean flag = super.doHurtTarget(target);
        if (flag) {
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addEffect(new EffectInstance(Effects.WEAKNESS, 75, 3));
            }
            if (!this.level.isClientSide) {
                handleDuplication();
            }
        }
        return flag;
    }

    private void handleDuplication() {
        if (this.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) this.level;
            int count = serverWorld.getEntities(this.getType(), e -> true).size();

            if (count < 15) {
                serverWorld.sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 20, 0.5, 0.5, 0.5, 0.0);
                this.remove();

                for (int i = 0; i < 2; i++) {
                    CatapleerEntity newEntity = (CatapleerEntity) this.getType().create(this.level);
                    if (newEntity != null) {
                        newEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
                        newEntity.setHealth(1.0f);
                        this.level.addFreshEntity(newEntity);
                    }
                }
            }
        }
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return true;
    }

    @Override
    protected boolean isSunBurnTick() {
        return true;
    }

    @Override
    public void setGlowing(boolean p_184195_1_) {
        super.setGlowing(p_184195_1_);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }
}
