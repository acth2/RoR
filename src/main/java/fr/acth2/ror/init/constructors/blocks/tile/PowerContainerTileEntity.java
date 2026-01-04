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

    @Override
    public void tick() {
        if (level == null || level.isClientSide) {
            return;
        }

        long gameTime = level.getGameTime();
        long timeInCycle = gameTime % TOTAL_CYCLE_DURATION;

        int newFrame;

        if (timeInCycle < ANIMATION_DURATION) {
            newFrame = (int) (timeInCycle / ANIMATION_TICK_SPEED);
        } else {
            newFrame = 0;
        }

        BlockState currentState = getBlockState();
        if (currentState.getValue(PowerContainerBlock.FRAME) != newFrame) {
            level.setBlock(worldPosition, currentState.setValue(PowerContainerBlock.FRAME, newFrame), 3);
        }
    }
}