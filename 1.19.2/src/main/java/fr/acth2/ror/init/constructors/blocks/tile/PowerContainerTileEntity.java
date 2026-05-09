package fr.acth2.ror.init.constructors.blocks.tile;

import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.PowerContainerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class PowerContainerTileEntity extends TileEntity implements ITickableTileEntity {

    private static final int ANIMATION_TICK_SPEED = 4;
    private static final int TOTAL_FRAMES = 6;
    private static final int DELAY_TICKS = 30;
    private static final int ANIMATION_DURATION = TOTAL_FRAMES * ANIMATION_TICK_SPEED;
    private static final int TOTAL_CYCLE_DURATION = ANIMATION_DURATION + DELAY_TICKS;

    public PowerContainerTileEntity() {
        super(ModTileEntities.POWER_CONTAINER_TILE_ENTITY.get());
    }

    @SuppressWarnings("unchecked")
    public PowerContainerTileEntity(Object pos, Object state) {
        super(ModTileEntities.POWER_CONTAINER_TILE_ENTITY.get());
    }

    public static void doTick(Object teObj) {
        TileEntity te = (TileEntity) teObj;
        if (te.getLevel() == null || te.getLevel().isClientSide()) return;

        long gameTime = te.getLevel().getGameTime();
        long timeInCycle = gameTime % TOTAL_CYCLE_DURATION;

        int newFrame = (timeInCycle < ANIMATION_DURATION)
                ? (int) (timeInCycle / ANIMATION_TICK_SPEED)
                : 0;

        BlockState currentState = te.getBlockState();
        if (currentState.getValue(PowerContainerBlock.FRAME) != newFrame) {
            te.getLevel().setBlock(te.getBlockPos(), currentState.setValue(PowerContainerBlock.FRAME, newFrame), 3);
        }
    }

    @Override
    public void tick() {
        doTick(this);
    }
}