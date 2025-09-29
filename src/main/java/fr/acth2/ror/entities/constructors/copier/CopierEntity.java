package fr.acth2.ror.entities.constructors.copier;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CopierEntity extends MonsterEntity {

    private PlayerEntity targetPlayer;
    private Vector3d lastPlayerPosition;
    private boolean hasTeleported = false;
    private Vector3d positionOffset = Vector3d.ZERO;

    protected CopierEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            if (!hasTeleported) {
                findAndTeleportToPlayer();
            } else if (targetPlayer != null && targetPlayer.isAlive()) {
                mimicPlayerMovement();
                checkCollision();
            } else {
                hasTeleported = false;
                targetPlayer = null;
            }
        }
    }

    private void findAndTeleportToPlayer() {
        List<PlayerEntity> players = this.level.players()
                .stream()
                .filter(player -> player != null && player.isAlive() && player.distanceTo(this) <= 10)
                .sorted(Comparator.comparingDouble(player -> player.distanceToSqr(this)))
                .collect(Collectors.toList());

        if (!players.isEmpty()) {
            targetPlayer = players.get(0);
            teleportBehindPlayer();
        }
    }

    private void teleportBehindPlayer() {
        if (targetPlayer == null) return;

        Vector3d playerLook = targetPlayer.getLookAngle();
        Vector3d horizontalLook = new Vector3d(playerLook.x, 0, playerLook.z).normalize();

        Vector3d behindPlayer = targetPlayer.position()
                .subtract(horizontalLook.x * 2, 0, horizontalLook.z * 2);

        double correctY = findCorrectYPosition(behindPlayer);

        positionOffset = new Vector3d(behindPlayer.x - targetPlayer.position().x,
                correctY - targetPlayer.position().y,
                behindPlayer.z - targetPlayer.position().z);

        this.teleportTo(behindPlayer.x, correctY, behindPlayer.z);
        lastPlayerPosition = targetPlayer.position();

        hasTeleported = true;
    }

    private double findCorrectYPosition(Vector3d position) {
        BlockPos pos = new BlockPos(position.x, position.y, position.z);
        BlockPos groundPos = this.level.getHeightmapPos(net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING, pos);

        return groundPos.getY() + 1;
    }

    private void mimicPlayerMovement() {
        if (targetPlayer == null || lastPlayerPosition == null) return;

        Vector3d currentPlayerPos = targetPlayer.position();
        Vector3d playerMovement = currentPlayerPos.subtract(lastPlayerPosition);
        Vector3d newPosition = this.position().add(playerMovement);
        Vector3d desiredPosition = currentPlayerPos.add(positionOffset);

        this.setPos(desiredPosition.x, desiredPosition.y - 1, desiredPosition.z);

        lastPlayerPosition = currentPlayerPos;

        this.yRot = targetPlayer.yRot;
        this.xRot = targetPlayer.xRot;
        this.yBodyRot = targetPlayer.yBodyRot;
        this.yHeadRot = targetPlayer.yHeadRot;

        this.setDeltaMovement(targetPlayer.getDeltaMovement());

        if (targetPlayer.isOnGround()) {
            this.setOnGround(true);
        }
    }

    private void checkCollision() {
        if (targetPlayer != null && targetPlayer.isAlive()) {
            double distance = this.distanceTo(targetPlayer);

            if (distance < 1.5) {
                boolean attacked = targetPlayer.hurt(DamageSource.mobAttack(this),
                        (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));

                if (attacked) {
                    this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.0F, 1.0F);

                    Vector3d knockbackDir = targetPlayer.position().subtract(this.position()).normalize();
                    targetPlayer.setDeltaMovement(knockbackDir.x * 0.5, 0.3, knockbackDir.z * 0.5);
                }
            }
        }
    }

    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PLAYER_BREATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.AMBIENT_CAVE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ANVIL_DESTROY;
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
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }
}