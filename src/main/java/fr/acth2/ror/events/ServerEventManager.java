package fr.acth2.ror.events;

import fr.acth2.ror.events.data.WorldEventData;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Random;

@Mod.EventBusSubscriber
public class ServerEventManager {
    private static final Random random = new Random();
    private static int brokenMoonPicked = 1;
    private static int event1Picked = 1;
    private static boolean brokenMoonActive = false;
    private static boolean event1Active = false;
    private static long lastProcessedTime = -1;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        ServerLifecycleHooks.getCurrentServer().getAllLevels().forEach(world -> {
            if (!(world instanceof ServerWorld)) return;

            ServerWorld serverWorld = (ServerWorld) world;
            long worldTime = serverWorld.getDayTime() % 24000;

            if (worldTime == lastProcessedTime) return;
            lastProcessedTime = worldTime;

            boolean isNight = worldTime >= 13000 && worldTime < 23000;

            if (isNight && !brokenMoonActive) {
                if (brokenMoonPicked == 0) {
                    brokenMoonActive = true;
                    syncEventToClients(serverWorld, "broken_moon", true);
                }
            } else if (!isNight && brokenMoonActive) {
                brokenMoonActive = false;
                syncEventToClients(serverWorld, "broken_moon", false);
                brokenMoonPicked = random.nextInt(50);
            }

            if (!isNight && !event1Active) {
                if (event1Picked == 0) {
                    event1Active = true;
                    syncEventToClients(serverWorld, "blood_sun", true);
                }
            } else if (isNight && event1Active) {
                event1Active = false;
                syncEventToClients(serverWorld, "blood_sun", false);
                event1Picked = random.nextInt(45);
            }

            if (worldTime == 12000) {
                event1Picked = random.nextInt(45);
            }
            if (worldTime == 0) {
                brokenMoonPicked = random.nextInt(50);
            }
        });
    }

    private static void syncEventToClients(ServerWorld world, String event, boolean active) {
        WorldEventData.get(world).setEventActive(event, active);
        // TODO: Add network packet sync here
        System.out.println("Event " + event + " is now " + (active ? "active" : "inactive"));
    }

    public static boolean isBrokenMoonActive(ServerWorld world) {
        return WorldEventData.get(world).isEventActive("broken_moon");
    }

    public static boolean isBloodSunActive(ServerWorld world) {
        return WorldEventData.get(world).isEventActive("blood_sun");
    }

    public static String getEventStatus() {
        return String.format("Broken Moon: %d, Blood Sun: %d", brokenMoonPicked, event1Picked);
    }
}