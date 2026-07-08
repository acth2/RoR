package fr.acth2.ror.entities.constructors.ghost;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GhostEntity extends MonsterEntity {

    protected GhostEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.addBehaviourGoals();
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

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false) {
        });

        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D) {
        });

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true) {
        });

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_)
                || true;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.GHOST_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return ModSoundEvents.GHOST_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.GHOST_DIE.get();
    }
    public int getAmbientSoundInterval() {
        return 120;
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
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }
}
