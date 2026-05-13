package fr.acth2.ror.init.constructors.armor.unstable;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class UnstableChestplate extends ArmorItem {
    private static final int FIREBALL_COOLDOWN = 100;
    private int cooldown = 0;
    private float previousHealth = 0;


    public UnstableChestplate(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<Component> txt, TooltipFlag p_77624_4_) {
        txt.add(new TextComponent(ChatFormatting.RED + "The unstability of the armor make it release fireball charge in every directions when damaged"));
        super.appendHoverText(p_77624_1_, p_77624_2_, txt, p_77624_4_);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, Player player) {
        if (!level.isClientSide && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == this) {
            if (cooldown > 0) {
                cooldown--;
                previousHealth = player.getHealth();
                return;
            }

            if (previousHealth > player.getHealth()) {
                shootFireballs(stack, world, player);
            }

            previousHealth = player.getHealth();
        }
    }

    private void shootFireballs(ItemStack stack, World world, Player player) {
        Vector3d[] directions = {
                new Vector3d(0, 0, -1),
                new Vector3d(1, 0, -1).normalize(),
                new Vector3d(1, 0, 0),
                new Vector3d(1, 0, 1).normalize(),
                new Vector3d(0, 0, 1),
                new Vector3d(-1, 0, 1).normalize(),
                new Vector3d(-1, 0, 0),
                new Vector3d(-1, 0, -1).normalize()
        };

        for (Vector3d direction : directions) {
            SmallFireballEntity fireball = new SmallFireballEntity(world, player,
                    direction.x, direction.y, direction.z);

            fireball.setPos(player.getX() + direction.x - 1,
                    player.getY(),
                    player.getZ() + direction.z - 1);

            fireball.setDeltaMovement(direction.x * 1.5, 0, direction.z * 1.5);
            world.addFreshEntity(fireball);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);

        cooldown = FIREBALL_COOLDOWN;
        if (stack.hurt(1, world.random, null)) {
            stack.shrink(1);
        }
    }
}