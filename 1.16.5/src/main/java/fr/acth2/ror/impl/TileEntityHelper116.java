package fr.acth2.ror.impl;

import fr.acth2.ror.api.tileentity.ITileEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public class TileEntityHelper116 implements ITileEntityHelper {

    @Override
    public Object create(Supplier<?> factory, Object... blocks) {
        net.minecraft.block.Block[] castBlocks =
                java.util.Arrays.stream(blocks)
                        .map(b -> (net.minecraft.block.Block) b)
                        .toArray(net.minecraft.block.Block[]::new);

        return TileEntityType.Builder
                .of((Supplier<? extends net.minecraft.tileentity.TileEntity>) factory, castBlocks)
                .build(null);
    }
}