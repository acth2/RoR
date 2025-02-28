package fr.acth2.ror.init.constructors.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        tooltip.add(new StringTextComponent(TextFormatting.AQUA + "This item can be used for traveling between Realms"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "However, something is wrong.."));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
