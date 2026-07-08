package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockGetter;

public class SkyriaAir extends Block {
    public SkyriaAir() {
        super((((AbstractBlock.Properties) Props.stone()))
                .strength(-1F, -1F)
                .harvestLevel(0)
                .noOcclusion()
                .noCollission()
                .isViewBlocking((state, world, pos) -> false)
                .lightLevel(state -> 15)
        );
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, BlockGetter p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}
