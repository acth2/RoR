package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class SkyriaPortalBlock extends NetherPortalBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 4);
    private static final int ANIMATION_SPEED = 5;

    public SkyriaPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(FRAME, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, FRAME);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void sendColoredParticleClient(World world, double x, double y, double z,
                                          float red, float green, float blue, float size) {
        world.addParticle(
                new RedstoneParticleData(red, green, blue, size),
                x, y, z,
                0, 0, 0
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    ModSoundEvents.PORTAL_SOUND.get(), SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        long gameTime = world.getGameTime();
        int frame = (int) ((gameTime / ANIMATION_SPEED) % 5);

        if (state.getValue(FRAME) != frame) {
            world.setBlock(pos, state.setValue(FRAME, frame), 2);
        }

        for (int i = 0; i < 4; ++i) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            double xSpeed = (random.nextDouble() - 0.5D) * 2.0D;
            double ySpeed = (random.nextDouble() - 0.5D) * 2.0D;
            double zSpeed = (random.nextDouble() - 0.5D) * 2.0D;

            sendColoredParticleClient(world, x + 0.1F, y, z + 0.1F, 0, 0, 255, 2);
        }
    }
}