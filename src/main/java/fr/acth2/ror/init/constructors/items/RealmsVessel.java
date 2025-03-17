package fr.acth2.ror.init.constructors.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RealmsVessel extends Item {
    public RealmsVessel(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().getBoolean("isActivated")) {
            tooltip.add(new StringTextComponent(TextFormatting.GREEN + "This item can be used for traveling between Realms"));
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + "..."));
        } else {
            tooltip.add(new StringTextComponent(TextFormatting.AQUA + "This item can be used for traveling between Realms"));
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + "However, something is wrong.."));
        }
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!itemStack.hasTag()) {
            itemStack.setTag(new CompoundNBT());
        }

        CompoundNBT nbt = itemStack.getTag();
        boolean isActivated = nbt.getBoolean("isActivated");
        nbt.putBoolean("isActivated", !isActivated);

        return ActionResult.success(itemStack);
    }
}
