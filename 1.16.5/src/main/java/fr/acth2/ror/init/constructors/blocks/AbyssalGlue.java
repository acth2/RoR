package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.constructors.blocks.tile.AbyssalGlueTileEntity;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockGetter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.AXIS;

public class AbyssalGlue extends Block {
    public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 9);
    private static final int ANIMATION_SPEED = 10;
    private static final int TOTAL_FRAMES = 10;
    public static final int[] FRAME_LIGHT = {0, 1, 2, 3, 4, 4, 3, 2, 1, 0};

    public static int getCurrentFrame(long gameTime) {
        return (int) ((gameTime / ANIMATION_SPEED) % TOTAL_FRAMES);
    }

    public static AbstractBlock.Properties defaultProperties() {
        return ((AbstractBlock.Properties) Props.stone())
                .noCollission()
                .strength(-1.0F)
                .sound(SoundType.ROOTS);
    }

    public AbyssalGlue() {
        super(defaultProperties());
        this.registerDefaultState(this.stateDefinition.any().setValue(FRAME, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FRAME);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, BlockGetter world) {
        return new AbyssalGlueTileEntity();
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(Effects.BLINDNESS, 75, 0));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 75, 2));
            player.addEffect(new EffectInstance(Effects.POISON, 75, 2));
            player.hurt(DamageSource.MAGIC, 3.5F);
        }
    }
}