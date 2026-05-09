package fr.acth2.ror.init.constructors.armor.gefranium;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GefraniumArmor extends ArmorItem {

    public GefraniumArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.GREEN + "This armor have an incredible knockback resistance"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Despite offering a low defense"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}