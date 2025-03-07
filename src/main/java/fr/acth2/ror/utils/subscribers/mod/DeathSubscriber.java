package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.OpenDeathScreenPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class DeathSubscriber {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();

            event.setCanceled(true);
            ModNetworkHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new OpenDeathScreenPacket()
            );
        }
    }
}