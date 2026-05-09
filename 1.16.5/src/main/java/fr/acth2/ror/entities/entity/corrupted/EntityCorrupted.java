package fr.acth2.ror.entities.entity.corrupted;

import fr.acth2.ror.entities.constructors.corrupted.CorruptedEntity;
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

public class EntityCorrupted extends CorruptedEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityCorrupted(EntityType<? extends CorruptedEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.corrupted.move", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.corrupted.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}