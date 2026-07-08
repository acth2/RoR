package fr.acth2.ror.init.constructors.armor.ghost;

import net.minecraft.client.util.ITooltipFlag;
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