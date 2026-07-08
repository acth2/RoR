package fr.acth2.ror.init.constructors.armor.seeker;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

public class SeekerHelmet extends ArmorItem {

    public SeekerHelmet(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Despite offering a terrible vision"));
        tooltip.add(new StringTextComponent(TextFormatting.RED + "You can now see every living entities through walls"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide) {
            for (Entity entity : world.getEntities(player, player.getBoundingBox().inflate(50))) {
                if (entity instanceof LivingEntity && entity != player) {
                    ((LivingEntity) entity).addEffect(new EffectInstance(Effects.GLOWING, 40, 0, false, false));
                }
            }
            player.addEffect(new EffectInstance(Effects.BLINDNESS, 40, 0, false, false));
        }
    }
}