package fr.acth2.ror.entities.constructors.ookla;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class OoklaEntity extends MonsterEntity {

    protected OoklaEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));

        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

    }

    @Override
    public void tick() {
        super.tick();
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
        return ModSoundEvents.OOKLA_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.OOKLA_DIE.get();
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
                .add(Attributes.MOVEMENT_SPEED, 0.00D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    private static final AtomicBoolean isHelpingOnce = new AtomicBoolean(true);

    public boolean isEntitiesWithin10Blocks() {
        List<MonsterEntity> nearbyMonsters = this.level.getEntitiesOfClass(
                MonsterEntity.class,
                this.getBoundingBox().inflate(10.0D),
                entity -> entity != this
        );

        boolean foundEntity = !nearbyMonsters.isEmpty();

        this.setGlowing(foundEntity);

        if (foundEntity) {
            if (isHelpingOnce.getAndSet(false)) {
                nearbyMonsters.forEach(entity ->
                        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 75, 1))
                );
            }
        } else {
            isHelpingOnce.set(true);
            nearbyMonsters.forEach(entity ->
                    entity.removeEffect(Effects.MOVEMENT_SPEED)
            );
        }

        return foundEntity;
    }
}
