package fr.acth2.ror.compat;

import fr.acth2.ror.init.ModTileEntities;
import fr.acth2.ror.init.constructors.blocks.PowerContainerBlock;
import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PowerContainerBlock119 extends PowerContainerBlock {

    public PowerContainerBlock119() {
        super();
    }

    @Nullable
    public <T extends net.minecraft.world.level.block.entity.BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        Object registryObject = ModTileEntities.POWER_CONTAINER_TILE_ENTITY;
        Object raw = ((java.util.function.Supplier) registryObject).get();
        BlockEntityType expectedType = (BlockEntityType) raw;
        return BlockEntityTickerCompat.powerContainerTicker(type, expectedType);
    }

    @Nullable
    public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(
            net.minecraft.core.BlockPos pos,
            net.minecraft.world.level.block.state.BlockState state) {
        return (net.minecraft.world.level.block.entity.BlockEntity)(Object)
                new PowerContainerTileEntity(pos, state);
    }
}