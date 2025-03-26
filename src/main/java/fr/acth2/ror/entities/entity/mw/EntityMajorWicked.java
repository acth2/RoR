package fr.acth2.ror.entities.entity.mw;

import fr.acth2.ror.entities.constructors.mw.MajorWickedEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMajorWicked extends MajorWickedEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityMajorWicked(EntityType<? extends MajorWickedEntity> type, World worldIn) {
        super(type, worldIn);
        this.setGlowing(true);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mw.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mw.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}