package fr.acth2.ror.impl;

import fr.acth2.ror.api.tileentity.ITileEntityHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public class TileEntityHelper119 implements ITileEntityHelper {

    @Override
    public Object create(Supplier<?> factory, Object... blocks) {
        net.minecraft.world.level.block.Block[] castBlocks =
                java.util.Arrays.stream(blocks)
                        .map(b -> (net.minecraft.world.level.block.Block) b)
                        .toArray(net.minecraft.world.level.block.Block[]::new);

        return BlockEntityType.Builder
                .of((pos, state) -> (BlockEntity) factory.get(), castBlocks)
                .build(null);
    }
}