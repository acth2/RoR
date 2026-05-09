package fr.acth2.ror.init.constructors.armor.ghost;

import net.minecraft.world.item.TooltipFlag;
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

public class GhostBoots extends ArmorItem {

    public GhostBoots(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> txt, ITooltipFlag p_77624_4_) {
        txt.add(new StringTextComponent(TextFormatting.GRAY + "Despite not defending the player"));
        txt.add(new StringTextComponent(TextFormatting.BLUE + "Theses boots negate falls damage and make the carrier walk faster"));
        super.appendHoverText(p_77624_1_, p_77624_2_, txt, p_77624_4_);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide && player.getItemBySlot(EquipmentSlotType.FEET).getItem() == this) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 0, false, false, true));
            player.fallDistance = 0.0F;
        }
    }
}