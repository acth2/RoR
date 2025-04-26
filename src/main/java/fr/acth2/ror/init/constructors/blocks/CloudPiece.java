package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class CloudPiece extends Block {
    public CloudPiece() {
        super(Properties.of(Material.WOOL, MaterialColor.WOOL)
                .strength(1.25F, 1.25F)
                .harvestLevel(0)
                .noOcclusion()
                .lightLevel((state) -> 15)
                .harvestTool(ToolType.SHOVEL)
        );
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}
