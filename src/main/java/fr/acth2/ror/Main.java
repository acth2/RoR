package fr.acth2.ror;

import fr.acth2.ror.gui.diary.DiaryManager;
import fr.acth2.ror.init.*;
import fr.acth2.ror.proxy.ClientProxy;
import fr.acth2.ror.proxy.CommonProxy;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import java.util.UUID;

@Mod(References.MODID)
public class Main {

    static {
        References.HEALTH_MODIFIER_UUID = UUID.randomUUID();
    }

    public static final CommonProxy proxy = DistExecutor.safeRunForDist(
            () -> ClientProxy::new,
            () -> CommonProxy::new
    );
    public Main() {
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSoundEvents.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        DiaryManager.registerAutosave();
        modEventBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        ModNetworkHandler.registerPackets();
        ModDimensions.register(event);

        proxy.setup();
    }

    private void onServerStarting(FMLServerStartingEvent event) {
        DiaryManager.initialize(event.getServer().getServerDirectory());
    }
}
