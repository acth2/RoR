package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WickedStaff extends Item {

    private static final int COOLDOWN_TICKS = 10;

    public WickedStaff(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(ChatFormatting.DARK_PURPLE + "This Staff can shoot magic projectiles"));
        tooltip.add(new TextComponent(ChatFormatting.GRAY + "At the cost of 15 Coins each shots"));
        tooltip.add(new TextComponent(""));
        tooltip.add(new TextComponent(ChatFormatting.GREEN + "12 Magic Damage"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, Player player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return ActionResult.fail(itemStack);
        }

        int currentCoins = player.level.isClientSide ?
                CoinsManager.getClientCoins() :
                CoinsManager.getCoins((ServerPlayerEntity) player);

        if (currentCoins >= 15) {
            if (!level.isClientSide) {
                WickedProjectile projectile = new WickedProjectile(world, player);
                projectile.setDamage(12.0F);
                projectile.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
                world.addFreshEntity(projectile);
                CoinsManager.removeCoins((ServerPlayerEntity) player, 15);

                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            }
        }

        return ActionResult.success(itemStack);
    }
}