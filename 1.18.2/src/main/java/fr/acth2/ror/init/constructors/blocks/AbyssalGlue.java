package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.constructors.blocks.tile.AbyssalGlueTileEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class AbyssalGlue extends Block {
    public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 9);
    private static final int ANIMATION_SPEED = 10;
    private static final int TOTAL_FRAMES = 10;
    public static final int[] FRAME_LIGHT = {0, 1, 2, 3, 4, 4, 3, 2, 1, 0};

    public static int getCurrentFrame(long gameTime) {
        return (int) ((gameTime / ANIMATION_SPEED) % TOTAL_FRAMES);
    }

    public static BlockBehaviour.Properties defaultProperties() {
        return ((BlockBehaviour.Properties) Props.stone())
                .noCollission()
                .strength(-1.0F)
                .sound(SoundType.ROOTS);
    }

    public AbyssalGlue() {
        super(defaultProperties());
        this.registerDefaultState(this.stateDefinition.any().setValue(FRAME, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FRAME);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return new AbyssalGlueTileEntity();
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 75, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 75, 2));
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 75, 2));
            player.hurt(DamageSource.MAGIC, 3.5F);
        }
    }
}