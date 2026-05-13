package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.gui.RealmVesselGui;
import fr.acth2.ror.utils.DimensionAccessManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RealmsVessel extends Item {
    public RealmsVessel(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(ChatFormatting.GRAY + "This vessel needs to be synced with another realm"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, Player player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (level.isClientSide) {
            openGui(player, itemStack, hand);
        } else {
            if (checkSkyriaConditions(player) && !hasSkyriaAccess(player)) {
                grantSkyriaAccess(player);
                player.sendSystemMessage(new TextComponent("Your essence synced with a bright realm!").withStyle(ChatFormatting.AQUA), player.getUUID());
            }
        }

        return ActionResult.success(itemStack);
    }

    private boolean hasSkyriaAccess(Player player) {
        return DimensionAccessManager.hasSkyriaAccess(player);
    }

    private boolean checkSkyriaConditions(Player player) {
        return DimensionAccessManager.checkSkyriaConditions(player);
    }

    private void grantSkyriaAccess(Player player) {
        DimensionAccessManager.grantSkyriaAccess(player);
    }


    @OnlyIn(Dist.CLIENT)
    private void openGui(Player player, ItemStack itemStack, Hand hand) {
        Minecraft.getInstance().setScreen(new RealmVesselGui(player, itemStack, hand));
    }

    @Override
    public InteractionResult useOn(ItemUseContext context) {
        return InteractionResult.PASS;
    }
}
