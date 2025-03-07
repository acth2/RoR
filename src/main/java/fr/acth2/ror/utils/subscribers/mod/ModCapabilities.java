package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.utils.subscribers.client.PlayerStatsStorage;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(
                PlayerStats.class,
                new PlayerStatsStorage(),
                () -> new PlayerStats(1, 20, 10, 1)
        );
    }
}