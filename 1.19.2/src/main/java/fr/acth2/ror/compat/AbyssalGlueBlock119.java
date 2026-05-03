package fr.acth2.ror.compat;

import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.AbyssalGlue;
import fr.acth2.ror.init.constructors.blocks.tile.AbyssalGlueTileEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AbyssalGlueBlock119 extends AbyssalGlue {

    public AbyssalGlueBlock119() {
        super();
    }

    @Nullable
    public <T extends net.minecraft.world.level.block.entity.BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        Object registryObject = ModTileEntities.ABYSSAL_GLUE_TILE_ENTITY;
        Object raw = ((java.util.function.Supplier) registryObject).get();
        BlockEntityType expectedType = (BlockEntityType) raw;
        return type == expectedType
                ? (lvl, pos, st, te) -> AbyssalGlueTileEntity.doTick(te)
                : null;
    }
}