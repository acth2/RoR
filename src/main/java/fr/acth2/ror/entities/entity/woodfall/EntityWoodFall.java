package fr.acth2.ror.entities.entity.woodfall;

import fr.acth2.ror.entities.constructors.woodfall.WoodFallEntity;
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

public class EntityWoodFall extends WoodFallEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean stopEveryAnimations = false;

    public EntityWoodFall(EntityType<? extends WoodFallEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityWoodFall> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldBlockExplode(Explosion p_174816_1_, IBlockReader p_174816_2_, BlockPos p_174816_3_, BlockState p_174816_4_, float p_174816_5_) {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!isAttacking()) {
            if (!stopEveryAnimations) {
                if (event.isMoving()) {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.woodfall.walk", true)
                    );
                } else {
                    event.getController().setAnimation(
                            new AnimationBuilder().addAnimation("animation.woodfall.idle", true)
                    );
                }
            }
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.woodfall.attack", false)
            );
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    stopEveryAnimations = true;
                    try { Thread.sleep(1250); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    stopEveryAnimations = false;
                }
            }.start();
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
