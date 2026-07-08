package fr.acth2.ror.init.constructors.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

public class Glider extends Item {

    public Glider(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Holding this item while falling"));
        tooltip.add(new StringTextComponent(TextFormatting.BLUE + "Make you glide and cancel your fall damages!"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    public static void handleGliding(PlayerEntity player) {
        if (player == null || !player.isAlive() || player.isOnGround() || player.isInWater() || player.isSpectator()) {
            return;
        }

        if (isHoldingGlider(player)) {
            Vector3d motion = player.getDeltaMovement();
            if (motion.y < 0) {
                double newY = motion.y * 0.6;

                Vector3d lookAngle = player.getLookAngle();
                double horizontalSpeed = 0.05;

                Vector3d newMotion = new Vector3d(
                        motion.x + lookAngle.x * horizontalSpeed,
                        newY,
                        motion.z + lookAngle.z * horizontalSpeed
                );

                double horizontalVelocity = Math.sqrt(newMotion.x * newMotion.x + newMotion.z * newMotion.z);
                double maxHorizontalSpeed = 0.25;

                if (horizontalVelocity > maxHorizontalSpeed) {
                    newMotion = new Vector3d(
                            newMotion.x / horizontalVelocity * maxHorizontalSpeed,
                            newMotion.y,
                            newMotion.z / horizontalVelocity * maxHorizontalSpeed
                    );
                }

                player.setDeltaMovement(newMotion);
                player.fallDistance = 0.0F;

                if (player.level.isClientSide) {
                    spawnGlideParticles(player);
                }
            }
        }
    }

    public static boolean isHoldingGlider(PlayerEntity player) {
        ItemStack mainHand = player.getMainHandItem();
        return mainHand.getItem() instanceof Glider;
    }

    private static void spawnGlideParticles(PlayerEntity player) {
        World world = player.level;
        if (world.isClientSide) {
            for (int i = 0; i < 3; i++) {
                world.addParticle(
                        net.minecraft.particles.ParticleTypes.CLOUD,
                        player.getX() + (world.random.nextDouble() - 0.5),
                        player.getY() - 0.5,
                        player.getZ() + (world.random.nextDouble() - 0.5),
                        0, -0.1, 0
                );
            }
        }
    }

    @Mod.EventBusSubscriber(modid = "ror")
    public static class GliderEvents {
        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            if (event.getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                if (isHoldingGlider(player)) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                handleGliding(event.player);
            }
        }
    }
}