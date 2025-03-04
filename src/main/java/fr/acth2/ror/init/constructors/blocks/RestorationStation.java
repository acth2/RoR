package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.constructors.items.RustedItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RestorationStation  extends Block {
    public RestorationStation() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD)
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

                        heldStack.shrink(1);

                        if (heldStack.getItem() == ModItems.RUSTED_INFRANIUM_CORE.get()) {
                            player.addItem(new ItemStack(ModItems.INFRANIUM_CORE.get()));
                        }
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