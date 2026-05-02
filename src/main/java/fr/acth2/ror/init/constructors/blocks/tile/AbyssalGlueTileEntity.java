package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.AbyssalGlue;
import net.minecraft.block.BlockState;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class AbyssalGlueTileEntity extends TileEntity implements ITickableTileEntity {
    private static final int ANIMATION_TICK_SPEED = 10;
    private static final int TOTAL_FRAMES = 9;
    private Random random;

    public AbyssalGlueTileEntity() {
        super(ModTileEntities.ABYSSAL_GLUE_TILE_ENTITY.get());
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

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        if (random == null) random = new Random();

        long gameTime = level.getGameTime();
        int newFrame = (int) ((gameTime / ANIMATION_TICK_SPEED) % TOTAL_FRAMES);

        BlockState currentState = getBlockState();
        if (currentState.getValue(AbyssalGlue.FRAME) != newFrame) {
            level.setBlock(worldPosition, currentState.setValue(AbyssalGlue.FRAME, newFrame), 3);
        }
    }
}