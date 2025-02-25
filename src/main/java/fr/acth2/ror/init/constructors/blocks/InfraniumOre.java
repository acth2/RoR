package fr.acth2.ror.init.constructors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class InfraniumOre  extends Block {

    public InfraniumOre() {
        super(Block.Properties.of(Material.STONE)
                .strength(3.0F, 6.0F)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.playerWillDestroy(world, pos, state, player);

        if (!world.isClientSide) {
            player.sendMessage(new StringTextComponent("Dang it you got me!"), player.getUUID());
            world.levelEvent(2001, pos, getId(state));
        }
    }
}