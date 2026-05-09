package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public class BlockToPlace {
    public final BlockPos pos;
    public final BlockState state;

    public BlockToPlace(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
    }
}
