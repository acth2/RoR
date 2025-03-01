package fr.acth2.ror.entities.entity.cg;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.cg.CoinGiverEntity;
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

public class EntityCoinGiver extends CoinGiverEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityCoinGiver(EntityType<? extends CoinGiverEntity
            > type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EntityCoinGiver> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(
                new AnimationBuilder().addAnimation("animation.coin_giver.idle", true)
        );

        return PlayState.CONTINUE;
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
