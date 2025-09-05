package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.gui.RealmVesselGui;
import fr.acth2.ror.utils.DimensionAccessManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RealmsVessel extends Item {
    public RealmsVessel(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "gotta wait for it to be finished :)"));
        tooltip.add(new StringTextComponent(TextFormatting.RED + "NOT" + TextFormatting.GRAY + " Ready for interdimensional travel"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (world.isClientSide) {
            openGui(player, itemStack, hand);
        } else {
            if (checkSkyriaConditions(player) && !hasSkyriaAccess(player)) {
                grantSkyriaAccess(player);
                player.sendMessage(new StringTextComponent("Your essence synced with another realm!").withStyle(TextFormatting.AQUA), player.getUUID());
            }
        }

        return ActionResult.success(itemStack);
    }

    private boolean hasSkyriaAccess(PlayerEntity player) {
        return DimensionAccessManager.hasSkyriaAccess(player);
    }

    private boolean checkSkyriaConditions(PlayerEntity player) {
        return DimensionAccessManager.checkSkyriaConditions(player);
    }

    private void grantSkyriaAccess(PlayerEntity player) {
        DimensionAccessManager.grantSkyriaAccess(player);
    }


    @OnlyIn(Dist.CLIENT)
    private void openGui(PlayerEntity player, ItemStack itemStack, Hand hand) {
        Minecraft.getInstance().setScreen(new RealmVesselGui(player, itemStack, hand));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        return ActionResultType.PASS;
    }
}
