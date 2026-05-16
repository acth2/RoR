package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public class SkyriaAir extends Block {
    public SkyriaAir() {
        super((((BlockBehaviour.Properties) Props.stone()))
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
