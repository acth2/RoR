package fr.acth2.ror.init.constructors.armor.oronium;

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

public class OroniumArmor extends ArmorItem {

    public OroniumArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.BLUE + "This armor works perfectly in floating realms!"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Despite offering a low defense the more you explore down"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}