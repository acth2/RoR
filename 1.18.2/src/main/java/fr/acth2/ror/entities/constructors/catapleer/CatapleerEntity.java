package fr.acth2.ror.entities.constructors.catapleer;

import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class CatapleerEntity extends Monster {

    public CatapleerEntity(EntityType<? extends Monster> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            List<Player> players = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(3.0D));
            for (Player player : players) {
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
                ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 3));
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

    public static AttributeSupplier.MutableAttribute createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }
}
