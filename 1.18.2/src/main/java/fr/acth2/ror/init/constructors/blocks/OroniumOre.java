package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class OroniumOre extends Block {
    public OroniumOre() {
        super((((BlockBehaviour.Properties) Props.stone()))
                .strength(3.25F, 1.25F)
                .harvestLevel(4)
                .harvestTool(net.minecraftforge.common.ToolAction.PICKAXE));
    }
}