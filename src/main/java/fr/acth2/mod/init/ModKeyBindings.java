package fr.acth2.mod.init;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static KeyBinding MM;
    public static void register() {
        MM = new KeyBinding("key.ror.mm", GLFW.GLFW_KEY_APOSTROPHE, "key.categories.ror");
    }

}