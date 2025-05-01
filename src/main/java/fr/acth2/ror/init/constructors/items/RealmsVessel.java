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
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }

        CompoundNBT nbt = stack.getTag();

        if (nbt.getBoolean("isSkyria")) {
            tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "This vessel is attuned to Skyria"));
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Ready for interdimensional travel"));
        } else if (nbt.getBoolean("isActivated")) {
            tooltip.add(new StringTextComponent(TextFormatting.GREEN + "This item can be used for traveling between Realms"));
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Reach the highest point to attune it..."));
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

        if (player.getY() >= 199 && world.dimension().equals(World.OVERWORLD)) {
            nbt.putBoolean("isActivated", false);
            nbt.putBoolean("isSkyria", true);
            player.displayClientMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "The vessel resonates with the sky!"), true);
            return ActionResult.success(itemStack);
        }

        if (!nbt.getBoolean("isSkyria")) {
            boolean isActivated = nbt.getBoolean("isActivated");
            nbt.putBoolean("isActivated", !isActivated);

            if (!isActivated) {
                player.displayClientMessage(new StringTextComponent(TextFormatting.GREEN + "The vessel hums with energy"), true);
            } else {
                player.displayClientMessage(new StringTextComponent(TextFormatting.RED + "The vessel goes silent"), true);
            }
        }

        return ActionResult.success(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (stack.hasTag()) {
                CompoundNBT nbt = stack.getTag();

                if (player.getY() >= 255 && world.dimension().equals(World.OVERWORLD) && nbt.getBoolean("isActivated")) {
                    nbt.putBoolean("isActivated", false);
                    nbt.putBoolean("isSkyria", true);
                    player.displayClientMessage(new StringTextComponent(TextFormatting.AQUA + "The vessel resonates with an ascended realm!"), true);
                }

                if (!world.dimension().equals(World.OVERWORLD) && nbt.getBoolean("isSkyria")) {
                    nbt.putBoolean("isSkyria", false);
                    nbt.putBoolean("isActivated", true);
                    player.displayClientMessage(new StringTextComponent(TextFormatting.AQUA + "The vessel's connection weakens"), true);
                    player.displayClientMessage(new StringTextComponent(TextFormatting.GRAY + "Teleport you back to the overworld"), true);
                }
            }
        }
    }
}
