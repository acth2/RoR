package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolAction;

public class CloudPiece extends Block {
    public CloudPiece() {
        super((((BlockBehaviour.Properties) Props.wool()))
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
