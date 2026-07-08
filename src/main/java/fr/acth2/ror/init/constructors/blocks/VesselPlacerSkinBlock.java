package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class VesselPlacerSkinBlock extends Block {

    public VesselPlacerSkinBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new VesselPlacerTileEntity();
    }
}