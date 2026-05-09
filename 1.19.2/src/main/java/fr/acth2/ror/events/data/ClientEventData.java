package fr.acth2.ror.events.data;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientEventData {
    private static boolean brokenMoonActive = false;
    private static boolean bloodSunActive = false;

    public static void setBrokenMoonActive(boolean active) {
        brokenMoonActive = active;
    }

    public static void setBloodSunActive(boolean active) {
        bloodSunActive = active;
    }

    public static boolean isBrokenMoonActive() {
        return brokenMoonActive;
    }

    public static boolean isBloodSunActive() {
        return bloodSunActive;
    }
}