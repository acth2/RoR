package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WickedStaff extends Item {

    private static final int COOLDOWN_TICKS = 10;

    public WickedStaff(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "This Staff can shoot magic projectiles"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "At cost of 15 Coins each shots"));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent(TextFormatting.GREEN + "12 Magic Damage"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return ActionResult.fail(itemStack);
        }

        int currentCoins = player.level.isClientSide ?
                CoinsManager.getClientCoins() :
                CoinsManager.getCoins((ServerPlayerEntity) player);

        if (currentCoins >= 15) {
            if (!world.isClientSide) {
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