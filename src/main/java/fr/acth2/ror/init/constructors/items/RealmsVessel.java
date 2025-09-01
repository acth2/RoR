package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.gui.RealmVesselGui;
import net.minecraft.client.Minecraft;
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
        tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "gotta wait for it to be finished :)"));
        tooltip.add(new StringTextComponent(TextFormatting.RED + "NOT" + TextFormatting.GRAY + " Ready for interdimensional travel"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Minecraft.getInstance().setScreen(new RealmVesselGui(player));

        return ActionResult.success(itemStack);
    }
}
