package fr.acth2.ror.init.constructors.armor.infranium;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class InfraniumArmor extends ArmorItem {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.randomUUID();
    private static final String MODIFIER_APPLIED_KEY = "ModifierApplied";

    public InfraniumArmor(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide) {
            boolean isInOverworld = world.dimension().location().getPath().equals("overworld");
            boolean isModifierApplied = stack.getOrCreateTag().getBoolean(MODIFIER_APPLIED_KEY);

            if (!isInOverworld && !isModifierApplied) {
                if (player.getAttribute(Attributes.ARMOR).getModifier(ARMOR_MODIFIER_UUID) == null) {
                    player.getAttribute(Attributes.ARMOR).addTransientModifier(
                            new AttributeModifier(ARMOR_MODIFIER_UUID, "Dimension Armor Penalty", -6, AttributeModifier.Operation.ADDITION)
                    );
                }
                stack.getOrCreateTag().putBoolean(MODIFIER_APPLIED_KEY, true);
            } else if (isInOverworld && isModifierApplied) {
                if (player.getAttribute(Attributes.ARMOR).getModifier(ARMOR_MODIFIER_UUID) != null) {
                    player.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
                    stack.getOrCreateTag().putBoolean(MODIFIER_APPLIED_KEY, false);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.AQUA + "Powerful against Overworld creatures"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Despite being pretty weak against other realms creatures"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}