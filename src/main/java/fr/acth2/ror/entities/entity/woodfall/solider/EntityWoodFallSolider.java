package fr.acth2.ror.entities.entity.woodfall.solider;

import fr.acth2.ror.entities.constructors.woodfall.solider.WoodFallSolidierEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWoodFallSolider extends WoodFallSolidierEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;
    private boolean block = false;
    private double originalResistance;

    private static double storedX = 0;
    private static double storedY = 0;
    private static double storedZ = 0;

    public EntityWoodFallSolider(EntityType<? extends WoodFallSolidierEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityWoodFallSolider> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldBlockExplode(Explosion p_174816_1_, IBlockReader p_174816_2_, BlockPos p_174816_3_, BlockState p_174816_4_, float p_174816_5_) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (1 + (int)(Math.random() * ((50 - 1) + 1)) == 19) {
            block = true;
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!block) {
            if (!isAttacking()) {
                if (!stopEveryAnimations) {
                    if (event.isMoving()) {
                        event.getController().setAnimation(
                                new AnimationBuilder().addAnimation("animation.woodfall_solider.walk", true)
                        );
                    } else {
                        event.getController().setAnimation(
                                new AnimationBuilder().addAnimation("animation.woodfall_solider.idle", true)
                        );
                    }
                }
            } else {
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.woodfall_solider.attack", false)
                );
                new Thread(() -> {
                    stopEveryAnimations = true;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stopEveryAnimations = false;
                }).start();
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.woodfall_solider.block", false)
            );
            new Thread(() -> {
                stopEveryAnimations = true;
                increaseResistance(0.7);
                disableMovement();

                try {
                    Thread.sleep(380);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                restoreResistance();
                restoreMovement();
                stopEveryAnimations = false;
                block = false;
            }).start();
        }
        return PlayState.CONTINUE;
    }

    private void increaseResistance(double percent) {
        ModifiableAttributeInstance resistanceAttribute = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (resistanceAttribute != null) {
            originalResistance = resistanceAttribute.getBaseValue();
            resistanceAttribute.setBaseValue(originalResistance * (1 + percent));
        }
    }

    private void restoreResistance() {
        ModifiableAttributeInstance resistanceAttribute = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (resistanceAttribute != null) {
            resistanceAttribute.setBaseValue(originalResistance);
        }
    }

    private void disableMovement() {
        storedX = this.getDeltaMovement().x();
        storedY = this.getDeltaMovement().y();
        storedZ = this.getDeltaMovement().z();
        this.setDeltaMovement(0, 0, 0);
    }

    private void restoreMovement() {
        this.setDeltaMovement(storedX, storedY, storedZ);
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_) || false;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}