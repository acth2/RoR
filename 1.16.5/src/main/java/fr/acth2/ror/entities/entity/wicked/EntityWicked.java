package fr.acth2.ror.entities.entity.wicked;

import fr.acth2.ror.entities.constructors.wicked.WickedEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
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

public class EntityWicked extends WickedEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityWicked(EntityType<? extends WickedEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityWicked> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldBlockExplode(Explosion p_174816_1_, IBlockReader p_174816_2_, BlockPos p_174816_3_, BlockState p_174816_4_, float p_174816_5_) {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.wicked.walk", true)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.wicked.idle", true)
            );
        }
        return PlayState.CONTINUE;
    }

    private boolean isAttacking() {
        return this.attackAnim > 0;
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
