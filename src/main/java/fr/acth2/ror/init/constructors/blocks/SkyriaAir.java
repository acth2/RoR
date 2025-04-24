package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SkyriaAir extends Block {
    public SkyriaAir() {
        super(Properties.of(Material.AIR, MaterialColor.WOOL)
                .strength(-1F, -1F)
                .harvestLevel(0)
                .noOcclusion()
                .noCollission()
                .isViewBlocking((state, world, pos) -> false)
                .lightLevel(state -> 15)
        );
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}
