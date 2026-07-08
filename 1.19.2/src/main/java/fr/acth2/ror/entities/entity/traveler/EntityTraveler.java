package fr.acth2.ror.entities.entity.traveler;

import fr.acth2.ror.entities.constructors.traveler.TravelerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityTraveler extends TravelerEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityTraveler(EntityType<? extends TravelerEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityTraveler> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);

    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isToTrack() != null) {
            PlayerEntity player = isToTrack();

            double entityX = this.getX();
            double playerX = player.getX();

            if (playerX < entityX) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.traveler.left", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.traveler.right", true));
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.traveler.idle", true));
        }

        return PlayState.CONTINUE;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_)
                || false;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
