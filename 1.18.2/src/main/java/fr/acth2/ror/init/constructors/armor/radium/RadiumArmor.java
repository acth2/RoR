package fr.acth2.ror.init.constructors.armor.radium;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class RadiumArmor extends ArmorItem {
    private int poisonTick = 0;

    public RadiumArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            poisonTick++;
            if (poisonTick >= 650) {
                if (player.getHealth() > 1) {
                    player.setHealth(player.getHealth() - 0.5F);
                }
                poisonTick = 0;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(ChatFormatting.RED + "When you hit someone they get poisoned"));
        tooltip.add(new TextComponent(ChatFormatting.GRAY + "Despite you being no exception"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}