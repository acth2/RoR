package fr.acth2.ror.impl;

import fr.acth2.ror.api.platform.IPlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class PlatformHelper116 implements IPlatformHelper {

    @Override
    public String getMinecraftVersion() {
        return "1.16.5";
    }

    @Override
    public boolean isServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    @Override
    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }
}