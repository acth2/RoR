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

public class RadiumOre extends Block {
    public RadiumOre() {
        super(Properties.of(Material.STONE, MaterialColor.STONE)
                .strength(3.25F, 1.25F)
                .harvestLevel(4)
                .harvestTool(net.minecraftforge.common.ToolType.PICKAXE));
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClientSide) {
            world.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5f, Explosion.Mode.NONE);
            player.addEffect(new EffectInstance(new EffectInstance(Effects.POISON, 100, 0)));
        }
        super.playerWillDestroy(world, pos, state, player);
    }
}
