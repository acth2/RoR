package fr.acth2.ror.init.constructors.armor.radium;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class RadiumArmor extends ArmorItem {
    private int poisonTick = 0;

    public RadiumArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
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
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.RED + "When you hit someone they get poisoned"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Despite you being no exception"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}