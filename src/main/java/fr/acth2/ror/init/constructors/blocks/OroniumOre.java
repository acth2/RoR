package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class OroniumOre extends Block {
    public OroniumOre() {
        super(Properties.of(Material.STONE, MaterialColor.STONE)
                .strength(3.25F, 1.25F)
                .harvestLevel(4)
                .harvestTool(net.minecraftforge.common.ToolType.PICKAXE));
    }
}