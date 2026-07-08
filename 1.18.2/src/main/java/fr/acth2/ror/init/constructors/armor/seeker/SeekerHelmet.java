package fr.acth2.ror.init.constructors.armor.seeker;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public class SeekerHelmet extends ArmorItem {

    public SeekerHelmet(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(ChatFormatting.GRAY + "Despite offering a terrible vision"));
        tooltip.add(new TextComponent(ChatFormatting.RED + "You can now see every living entities through walls"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, Player player) {
        if (!level.isClientSide) {
            for (Entity entity : world.getEntities(player, player.getBoundingBox().inflate(50))) {
                if (entity instanceof LivingEntity && entity != player) {
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));
                }
            }
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false));
        }
    }
}