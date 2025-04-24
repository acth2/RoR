package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class SkyriaTeleporter extends Block {
    public SkyriaTeleporter() {
        super(Properties.of(Material.BARRIER, MaterialColor.NONE)
                .noCollission()
                .noOcclusion()
                .air()
                .instabreak()
                .noDrops());
    }


    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (!world.isClientSide && entity instanceof PlayerEntity player) {
            ServerWorld overworld = world.getServer().getLevel(World.OVERWORLD);
            if (overworld == null) return;

            BlockPos skyPos = new BlockPos(
                    player.getX(),
                    overworld.getMaxBuildHeight(),
                    player.getZ()
            );

            player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 100, 0, false, false));
        }
    }
}

class SimpleTeleporter implements ITeleporter {
    private final double x, y, z;

    public SimpleTeleporter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        entity.teleportTo(x, y, z);
        return entity;
    }
}