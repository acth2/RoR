package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class InfraniumOre extends Block {
    public InfraniumOre() {
        super(Properties.of(Material.STONE, MaterialColor.STONE)
                .strength(3.0F, 1.F)
                .harvestLevel(2)
                .harvestTool(net.minecraftforge.common.ToolType.PICKAXE));
    }
}