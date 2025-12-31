package fr.acth2.ror.events;

import fr.acth2.ror.events.data.WorldEventData;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.event.EventSyncPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Random;

@Mod.EventBusSubscriber
public class ServerEventManager {
    private static final Random random = new Random();
    private static int brokenMoonPicked = -1;
    private static int bloodSunPicked = -1;
    private static boolean brokenMoonActive = false;
    private static boolean bloodSunActive = false;
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

            if (worldTime % 200 == 0) {
                System.out.println("DEBUG - Time: " + worldTime +
                        ", IsNight: " + isNight +
                        ", BrokenMoon: " + brokenMoonPicked +
                        ", BloodSun: " + bloodSunPicked);
            }

            if (worldTime >= 0 && worldTime <= 100 && bloodSunPicked == -1) {
                bloodSunPicked = random.nextInt(50);
                System.out.println("Decided Blood Sun event: " + (bloodSunPicked == 0 ? "ACTIVE" : "inactive"));
            }

            if (worldTime >= 13000 && worldTime <= 13100 && brokenMoonPicked == -1) {
                brokenMoonPicked = random.nextInt(85);
                System.out.println("Decided Broken Moon event: " + (brokenMoonPicked == 0 ? "ACTIVE" : "inactive"));
            }

            if (isNight && brokenMoonPicked == 0 && !brokenMoonActive) {
                brokenMoonActive = true;
                syncEventToClients(serverWorld, "broken_moon", true);
                System.out.println("ACTIVATING BROKEN MOON EVENT!");
            } else if (!isNight && brokenMoonActive) {
                brokenMoonActive = false;
                syncEventToClients(serverWorld, "broken_moon", false);
                System.out.println("Deactivating Broken Moon event");
                brokenMoonPicked = -1;
            }

            if (!isNight && bloodSunPicked == 0 && !bloodSunActive) {
                bloodSunActive = true;
                syncEventToClients(serverWorld, "blood_sun", true);
                System.out.println("ACTIVATING BLOOD SUN EVENT!");
            } else if (isNight && bloodSunActive) {
                bloodSunActive = false;
                syncEventToClients(serverWorld, "blood_sun", false);
                System.out.println("Deactivating Blood Sun event");
                bloodSunPicked = -1;
            }
        });
    }

    private static void syncEventToClients(ServerWorld world, String event, boolean active) {
        WorldEventData.get(world).setEventActive(event, active);

        EventSyncPacket packet = new EventSyncPacket(event, active);
        ModNetworkHandler.sendToAllInWorld(packet, world);

        System.out.println("Event " + event + " is now " + (active ? "active" : "inactive") +
                " in dimension " + world.dimension().getRegistryName());
    }

    public static boolean isBrokenMoonActive(ServerWorld world) {
        return WorldEventData.get(world).isEventActive("broken_moon");
    }

    public static boolean isBloodSunActive(ServerWorld world) {
        return WorldEventData.get(world).isEventActive("blood_sun");
    }

    public static String getEventStatus() {
        return String.format("broken_moon: %d, blood_sun: %d", brokenMoonPicked, bloodSunPicked);
    }

    public static void syncEventsToPlayer(ServerPlayerEntity player) {
        ServerWorld world = player.getLevel();

        boolean brokenMoonActive = WorldEventData.get(world).isEventActive("broken_moon");
        EventSyncPacket brokenMoonPacket = new EventSyncPacket("broken_moon", brokenMoonActive);
        ModNetworkHandler.sendToClient(brokenMoonPacket, player);

        boolean bloodSunActive = WorldEventData.get(world).isEventActive("blood_sun");
        EventSyncPacket bloodSunPacket = new EventSyncPacket("blood_sun", bloodSunActive);
        ModNetworkHandler.sendToClient(bloodSunPacket, player);

        System.out.println("Synced events to player: " + player.getScoreboardName() +
                " - BrokenMoon: " + brokenMoonActive + ", BloodSun: " + bloodSunActive);
    }

    public static void forceEvent(String event, boolean active) {
        ServerLifecycleHooks.getCurrentServer().getAllLevels().forEach(world -> {
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld) world;
                if ("broken_moon".equals(event)) {
                    brokenMoonPicked = active ? 0 : -1;
                    brokenMoonActive = active;
                } else if ("blood_sun".equals(event)) {
                    bloodSunPicked = active ? 0 : -1;
                    bloodSunActive = active;
                }
                syncEventToClients(serverWorld, event, active);
            }
        });
    }
}