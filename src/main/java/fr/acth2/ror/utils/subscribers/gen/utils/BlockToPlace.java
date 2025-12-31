package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockToPlace {
    public final BlockPos pos;
    public final BlockState state;

    public BlockToPlace(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
    }
}
