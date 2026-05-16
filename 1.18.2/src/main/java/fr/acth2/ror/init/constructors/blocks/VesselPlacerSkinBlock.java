package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

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
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return new VesselPlacerTileEntity();
    }
}