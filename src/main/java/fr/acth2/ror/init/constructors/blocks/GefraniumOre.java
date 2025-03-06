package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class GefraniumOre extends Block {
    public GefraniumOre() {
        super(Properties.of(Material.STONE, MaterialColor.STONE)
                .strength(3.25F, 1.25F)
                .harvestLevel(3)
                .harvestTool(net.minecraftforge.common.ToolType.PICKAXE));
    }
}