package fr.acth2.ror.utils;

import fr.acth2.ror.events.ServerEventManager;
import fr.acth2.ror.events.data.ClientEventData;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class References {

    public static final String MODID = "ror";

    public static UUID HEALTH_MODIFIER_UUID;
    public static UUID DEXTERITY_MODIFIER_UUID;
    public static UUID STRENGTH_MODIFIER_UUID;

    public static int DEXTERITY_REDUCER = 728;
    public static int STRENGTH_REDUCER = 728;
    public static int HEALTH_REDUCER = 20;

    public static int brokenMoonPicked = 1;
    public static boolean brokenMoonWarning = false;

    public static int event1Picked = 1;
    public static boolean event1Warning = false;

    public static boolean isBrokenMoonActive(World world) {
        if (world.isClientSide()) {
            return ClientEventData.isBrokenMoonActive();
        } else if (world instanceof ServerWorld) {
            return ServerEventManager.isBrokenMoonActive((ServerWorld) world);
        }
        return false;
    }

    public static boolean isBloodSunActive(World world) {
        if (world.isClientSide()) {
            return ClientEventData.isBloodSunActive();
        } else if (world instanceof ServerWorld) {
            return ServerEventManager.isBloodSunActive((ServerWorld) world);
        }
        return false;
    }
}
