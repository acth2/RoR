package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class InfraniumOre extends Block {
    public InfraniumOre() {
        super((((BlockBehaviour.Properties) Props.stone()))
                .strength(3.0F, 1.F)
                .harvestLevel(2)
                .harvestTool(net.minecraftforge.common.ToolAction.PICKAXE));
    }
}