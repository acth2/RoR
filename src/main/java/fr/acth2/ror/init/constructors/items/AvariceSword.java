package fr.acth2.ror.init.constructors.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AvariceSword extends Item {
    private static final Random RANDOM = new Random();
    private static final float MIN_DAMAGE = 10.0f;
    private static final float MAX_DAMAGE = 26.0f;
    private static final DamageSource AVARICE_DAMAGE = new DamageSource("avarice").bypassArmor();

    public AvariceSword(Properties properties) {
        super(properties.stacksTo(1).durability(1561));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float damage = calculateScaledDamage(attacker);
        target.setSecondsOnFire(3);

        if (!attacker.level.isClientSide) {
            ServerWorld world = (ServerWorld) attacker.level;

            spawnCircularFireParticles(target.position(), target.getBbHeight(), world, 12, 1.5f);
            spawnCircularFireParticles(attacker.position(), attacker.getBbHeight(), world, 8, 1.0f);

            target.hurt(AVARICE_DAMAGE, damage);

            if (attacker instanceof PlayerEntity) {
                stack.getOrCreateTag().putUUID("Owner", attacker.getUUID());
            }
        }

        if (attacker instanceof PlayerEntity && RANDOM.nextFloat() < 0.25f) {
            attacker.hurt(AVARICE_DAMAGE, 1.0f);
        }

        stack.hurtAndBreak(1, attacker, (entity) -> {
            entity.broadcastBreakEvent(Hand.MAIN_HAND);
        });

        return true;
    }

    private float calculateScaledDamage(LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            float healthRatio = player.getHealth() / player.getMaxHealth();
            return MAX_DAMAGE - (healthRatio * (MAX_DAMAGE - MIN_DAMAGE));
        }
        return MAX_DAMAGE;
    }

    private void spawnCircularFireParticles(Vector3d centerPos, float height, ServerWorld world, int count, float radius) {
        for (int i = 0; i < count; i++) {
            float angle = (float) (2 * Math.PI * i / count);
            float xOffset = radius * (float) Math.cos(angle);
            float zOffset = radius * (float) Math.sin(angle);
            float yOffset = RANDOM.nextFloat() * height;

            Vector3d pos = centerPos.add(xOffset, yOffset, zOffset);
            world.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0.1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("This sword set enemies on fire").withStyle(TextFormatting.YELLOW));
        tooltip.add(new StringTextComponent("The weaker you get. The stronger the sword get").withStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent("It can remove a half a hearth").withStyle(TextFormatting.DARK_PURPLE));

        if (stack.hasTag() && stack.getTag().hasUUID("Owner") && world != null) {
            PlayerEntity owner = world.getPlayerByUUID(stack.getTag().getUUID("Owner"));
            if (owner != null) {
                float currentDamage = calculateScaledDamage(owner);
                tooltip.add(new StringTextComponent("Damage: ")
                        .append(new StringTextComponent(String.format("%.1f", currentDamage)).withStyle(TextFormatting.GOLD))
                        .withStyle(TextFormatting.GRAY));
            }
        }


        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}