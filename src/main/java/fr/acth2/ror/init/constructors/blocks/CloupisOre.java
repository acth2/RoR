package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class CloupisOre extends Block {
    public CloupisOre() {
        super(Properties.of(Material.STONE, MaterialColor.STONE)
                .strength(3.25F, 1.25F)
                .harvestLevel(3)
                .harvestTool(net.minecraftforge.common.ToolType.PICKAXE));
    }
}