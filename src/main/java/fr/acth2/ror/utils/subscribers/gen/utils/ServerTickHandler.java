package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.world.ISeedReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerTickHandler {
    private static ISeedReader lastWorld = null;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.world.isClientSide) {
            if (event.world instanceof ISeedReader) {
                lastWorld = (ISeedReader) event.world;
            }
        }
    }

    public static ISeedReader getLastWorld() {
        return lastWorld;
    }
}
