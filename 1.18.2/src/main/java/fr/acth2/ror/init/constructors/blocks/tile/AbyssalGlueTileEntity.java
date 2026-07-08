package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.AbyssalGlue;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Random;

public class AbyssalGlueTileEntity extends TileEntity implements ITickableTileEntity {

    private static final int ANIMATION_TICK_SPEED = 10;
    private static final int TOTAL_FRAMES = 9;
    private Random random;

    public AbyssalGlueTileEntity() {
        super(ModTileEntities.ABYSSAL_GLUE_TILE_ENTITY.get());
    }

    @SuppressWarnings("unchecked")
    public AbyssalGlueTileEntity(Object pos, Object state) {
        super(ModTileEntities.ABYSSAL_GLUE_TILE_ENTITY.get());
    }

    public static void doTick(Object teObj) {
        AbyssalGlueTileEntity te = (AbyssalGlueTileEntity) teObj;
        if (te.getLevel() == null || te.getLevel().isClientSide()) return;
        if (te.random == null) te.random = new Random();

        long gameTime = te.getLevel().getGameTime();
        int newFrame = (int) ((gameTime / ANIMATION_TICK_SPEED) % TOTAL_FRAMES);

        BlockState currentState = te.getBlockState();
        if (currentState.getValue(AbyssalGlue.FRAME) != newFrame) {
            te.getLevel().setBlock(te.getBlockPos(),
                    currentState.setValue(AbyssalGlue.FRAME, newFrame), 3);
        }
    }

    @Override
    public void tick() {
        doTick(this);
    }
}