package fr.acth2.ror.init.constructors.items;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GravitationalPulsar extends Item {

    public GravitationalPulsar(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.inventory.contains(stack)) {
                AxisAlignedBB area = new AxisAlignedBB(player.blockPosition()).inflate(16);
                List<Entity> nearbyEntities = world.getEntities(player, area, e -> e instanceof LivingEntity && e != player);

                for (Entity nearbyEntity : nearbyEntities) {
                    double motionY = Math.sin(world.getGameTime() * 0.1) * 0.05;
                    nearbyEntity.setDeltaMovement(nearbyEntity.getDeltaMovement().add(0, motionY, 0));
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            AxisAlignedBB area = new AxisAlignedBB(player.blockPosition()).inflate(32);
            List<Entity> nearbyEntities = world.getEntities(player, area, e -> e instanceof LivingEntity && e != player);

            for (Entity nearbyEntity : nearbyEntities) {
                Vector3d direction = nearbyEntity.position().subtract(player.position()).normalize();
                nearbyEntity.push(direction.x * 5, 2.0, direction.z * 5);
                player.getCooldowns().addCooldown(this, 10);
            }
        }

        if (!player.abilities.instabuild) {
            itemStack.shrink(1);
        }
        return ActionResult.success(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.RED + "This instable item change gravity around you"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Right-click to push every entity around you very far"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
