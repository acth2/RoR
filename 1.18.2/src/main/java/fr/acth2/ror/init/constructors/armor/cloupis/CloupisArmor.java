package fr.acth2.ror.init.constructors.armor.cloupis;

import fr.acth2.ror.utils.References;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.entity.player.Player;
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
import java.util.Random;

public class CloupisArmor extends ArmorItem {

    public CloupisArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    public static boolean shouldStealth(LivingEntity player) {
        if (checkArmor(player)) return false;

        return player.isCrouching();
    }

    private static boolean checkArmor(LivingEntity player) {
        if (!(player instanceof PlayerEntity)) return true;

        for (EquipmentSlotType slot : new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET
        }) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!(stack.getItem() instanceof CloupisArmor)) return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "This armor give you a steath advantage when you are sneaking"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "But if you get hurt every enemies at a 32 block radius see you"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}