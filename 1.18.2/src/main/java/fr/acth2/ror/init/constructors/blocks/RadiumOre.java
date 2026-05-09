package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.level.Level;

public class RadiumOre extends Block {
    public RadiumOre() {
        super((((AbstractBlock.Properties) Props.stone()))
                .strength(4.25F, 2.25F)
                .harvestLevel(4)
                .harvestTool(net.minecraftforge.common.ToolAction.PICKAXE));
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
