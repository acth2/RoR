package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BlockGetter;

import javax.annotation.Nullable;
import java.util.List;

public class PowerContainerBlock extends Block {

    public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 5);

    public PowerContainerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FRAME, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FRAME);
    }

    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable BlockGetter p_190948_2_, List<ITextComponent> tooltip, ITooltipFlag p_190948_4_) {
        tooltip.add(new StringTextComponent("This block could help in a larger spectrum").withStyle(TextFormatting.GRAY));
        super.appendHoverText(p_190948_1_, p_190948_2_, tooltip, p_190948_4_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, BlockGetter world) {
        return new PowerContainerTileEntity();
    }
}