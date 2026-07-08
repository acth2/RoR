package fr.acth2.ror.entities.constructors.curser;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class CurserEntity extends Monster {

    protected CurserEntity(EntityType<? extends Monster> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));

        this.goalSelector.addGoal(6, new LookAtGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive()) {
            if (this.isSunBurnTick()) {
                boolean flag = this.isSunSensitive();
                if (flag) {
                    ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.HEAD);
                    if (!itemstack.isEmpty()) {
                        if (itemstack.isDamageableItem()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                            if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                                this.broadcastBreakEvent(EquipmentSlotType.HEAD);
                                this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                            }
                        }
                        flag = false;
                    }
                    if (flag && this.level.isDay() && this.level.canSeeSky(this.blockPosition())) {
                        this.setSecondsOnFire(8);
                    }
                }
            }
        }

        super.tick();
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.CURSER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.CURSER_HIT.get();
    }


    public int getAmbientSoundInterval() {
        return 60;
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
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.00D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    private static final AtomicBoolean isCursingOnce = new AtomicBoolean(true);

    public boolean isPlayerWithin10Blocks() {
        boolean playerInRange = false;

        for (Player player : this.level.players()) {
            double distance = this.distanceTo(player);

            if (distance <= 10.0D) {
                playerInRange = true;
                this.setGlowing(true);

                if (isCursingOnce.getAndSet(false)) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 75, 1, false, true));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 1, false, true));
                }
            } else {
                player.removeEffect(MobEffects.WEAKNESS);
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                this.setGlowing(false);
                isCursingOnce.set(true);
            }
        }

        return playerInRange;
    }
}
