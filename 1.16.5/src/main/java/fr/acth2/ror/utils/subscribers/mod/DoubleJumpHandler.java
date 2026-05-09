package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.block.AirBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DoubleJumpHandler {

    private static final int MAX_JUMPS = 2;
    private static final String JUMPS_KEY = "jumps";

    private static final HashMap<UUID, Boolean> jumpButtonPressed = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (player.isOnGround()) {
                player.getPersistentData().putInt(JUMPS_KEY, 0);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerInput(InputUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            PlayerStats playerStats = PlayerStats.get(player);
            UUID playerId = player.getUUID();

            boolean isJumping = event.getMovementInput().jumping;

            if (isJumping && !jumpButtonPressed.getOrDefault(playerId, false) && MainMenuGui.calculateDexterityFromModifiers(player) >= 15) {
                jumpButtonPressed.put(playerId, true);

                if (!player.isOnGround() && player.getPersistentData().getInt(JUMPS_KEY) < MAX_JUMPS) {
                    Vector3d motion = player.getDeltaMovement();
                    player.setDeltaMovement(motion.x, 0.4, motion.z);
                    player.getPersistentData().putInt(JUMPS_KEY, player.getPersistentData().getInt(JUMPS_KEY) + 1);
                }
            }

            if (!isJumping) {
                jumpButtonPressed.put(playerId, false);
            }
        }
    }
}