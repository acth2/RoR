package fr.acth2.ror.entities.constructors.traders;

import fr.acth2.ror.gui.npc.skyria.SkyriaTraderSpeech;
import fr.acth2.ror.gui.npc.traveler.TravelerSpeech;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SkyriaTraderEntity extends CreatureEntity {

    public SkyriaTraderEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(8, new PanicGoal(this, 1.75D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 2.0D));
    }

    @Override
    public void push(double p_70024_1_, double p_70024_3_, double p_70024_5_) { }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return super.causeFallDamage(fallDistance, damageMultiplier);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.PLAYER_HURT;
    }

    public int getAmbientSoundInterval() {
        return 60;
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d p_184199_2_, Hand hand) {
        if (!this.level.isClientSide) {
            return ActionResultType.SUCCESS;
        }

        Minecraft.getInstance().setScreen(new SkyriaTraderSpeech(player));
        return ActionResultType.SUCCESS;
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
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }
}