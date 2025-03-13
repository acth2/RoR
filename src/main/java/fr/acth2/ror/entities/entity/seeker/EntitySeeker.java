package fr.acth2.ror.entities.entity.seeker;

import fr.acth2.ror.entities.constructors.seeker.SeekerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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

public class EntitySeeker extends SeekerEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntitySeeker(EntityType<? extends SeekerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntitySeeker> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldBlockExplode(Explosion p_174816_1_, IBlockReader p_174816_2_, BlockPos p_174816_3_, BlockState p_174816_4_, float p_174816_5_) {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.seeker.walk", true)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.seeker.idle", true)
            );
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = this.level.getNearestPlayer(this, 20.0D);
        if (player != null) {
            System.out.println("Looking at player: " + player.getName().getString());
            this.getLookControl().setLookAt(player, 30.0F, 30.0F);
        }
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