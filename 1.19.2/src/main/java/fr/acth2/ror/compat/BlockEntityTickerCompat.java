package fr.acth2.ror.compat;

import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTickerCompat {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BlockEntity> BlockEntityTicker<T> powerContainerTicker(
            BlockEntityType<T> type, BlockEntityType expectedType) {
        return type == expectedType
                ? (lvl, pos, state, te) -> PowerContainerTileEntity.doTick(te)
                : null;
    }
}