package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.constructors.items.RustedItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RestorationStation  extends Block {
    public RestorationStation() {
        super(((AbstractBlock.Properties) Props.wood())
                .strength(0.5F, 0.25F)
                .harvestLevel(0));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (!heldStack.isEmpty()) {
                if (heldStack.getItem() instanceof RustedItem) {
                    if (player.experienceLevel >= 5) {
                        player.giveExperienceLevels(-5);

                        if (heldStack.getItem() == ModItems.RUSTED_INFRANIUM_CORE.get()) {
                            player.addItem(new ItemStack(ModItems.INFRANIUM_CORE.get()));
                        }

                        heldStack.shrink(1);
                        player.sendMessage(new StringTextComponent("Item restored! (-5 XP Levels)")
                                .withStyle(TextFormatting.GREEN), player.getUUID());
                    } else {
                        player.sendMessage(new StringTextComponent("You need at least 5 XP levels!")
                                .withStyle(TextFormatting.RED), player.getUUID());
                    }
                } else {
                    player.sendMessage(new StringTextComponent("This item isn't rusted!")
                            .withStyle(TextFormatting.YELLOW), player.getUUID());
                }
            } else {
                player.sendMessage(new StringTextComponent("You need to hold an rusted item!")
                        .withStyle(TextFormatting.RED), player.getUUID());
            }
        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        p_190948_3_.add(new StringTextComponent(TextFormatting.DARK_GREEN + "This station can restore rusted items at cost of XP"));
        super.appendHoverText(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
    }
}