package fr.acth2.mod.entities.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.PlayState;

public class EntityExample extends IronGolemEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityExample(EntityType<? extends IronGolemEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityExample> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!isAttacking()) {
            if (event.isMoving()) {
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.example_entity.move", true)
                );
            } else {
                event.getController().setAnimation(
                        new AnimationBuilder().addAnimation("animation.example_entity.idle", true)
                );
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.example_entity.attack", true)
            );
        }

        return PlayState.CONTINUE;
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }
}
