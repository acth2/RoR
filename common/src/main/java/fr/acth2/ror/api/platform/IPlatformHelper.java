package fr.acth2.ror.api.platform;

import net.minecraftforge.eventbus.api.IEventBus;

public interface IPlatformHelper {
    String getMinecraftVersion();

    boolean isServer();
    boolean isClient();

    IEventBus getModEventBus();
}