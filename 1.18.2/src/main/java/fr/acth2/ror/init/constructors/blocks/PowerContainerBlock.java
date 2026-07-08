package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;
import java.util.List;

public class PowerContainerBlock extends Block {

    public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 5);

    public static BlockBehaviour.Properties defaultProperties() {
        return ((BlockBehaviour.Properties) Props.stone())
                .strength(2.0f, 8.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .requiresCorrectToolForDrops();
    }

    public PowerContainerBlock() {
        super(defaultProperties());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FRAME);
    }

    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable BlockGetter p_190948_2_, List<Component> tooltip, TooltipFlag p_190948_4_) {
        tooltip.add(new TextComponent("This block could help in a larger spectrum").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(p_190948_1_, p_190948_2_, tooltip, p_190948_4_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return new PowerContainerTileEntity();
    }
}