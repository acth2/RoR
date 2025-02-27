package fr.acth2.ror.entities.constructors.traveler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class TravelerEntity extends MonsterEntity {

    private static int hurtCounter = 0;
    private static final AtomicBoolean atomicFirstPayload = new AtomicBoolean(true);
    private static final AtomicBoolean atomicSecondPayload = new AtomicBoolean(true);
    private static final AtomicBoolean atomicThirdPayload = new AtomicBoolean(true);
    private static final AtomicBoolean atomicFinalPayload = new AtomicBoolean(true);

    protected TravelerEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        hurtCounter++;

        if (hurtCounter == 1 && atomicFirstPayload.getAndSet(false)) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(ITextComponent.nullToEmpty("[TRAVELER]: Mh?"), player.getUUID());
        }

        if (hurtCounter == 2 && atomicSecondPayload.getAndSet(false)) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(ITextComponent.nullToEmpty("[TRAVELER]: Stop that!"), player.getUUID());
        }

        if (hurtCounter == 3 && atomicThirdPayload.getAndSet(false)) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(ITextComponent.nullToEmpty("[TRAVELER]: What is wrong with you? Stop!"), player.getUUID());
        }

        if (hurtCounter > 3 && atomicFinalPayload.getAndSet(false)) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(ITextComponent.nullToEmpty("[TRAVELER]: That is too much."), player.getUUID());

            for (int i = 0; i < 10; i++) {
                double x = this.getX() + (this.random.nextDouble() - 0.5D) * 0.5D;
                double y = this.getY() + (this.random.nextDouble() - 0.5D) * 0.5D;
                double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 0.5D;
                this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 5, 5);
            }

            hurtCounter = 0;
            atomicFirstPayload.set(true);
            atomicSecondPayload.set(true);
            atomicThirdPayload.set(true);
            atomicFinalPayload.set(true);

            this.remove();
        }

        return super.hurt(source, amount);
    }



    @Override
    public int getLastHurtByMobTimestamp() {
        return 0;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isColliding(BlockPos p_242278_1_, BlockState p_242278_2_) {
        return false;
    }

    public PlayerEntity isToTrack() {
        for (PlayerEntity player : this.level.players()) {
            if (distanceTo(player) <= 10.0D) {
                return player;
            }
        }
        return null;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Integer.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 0.00D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE);
    }
}
