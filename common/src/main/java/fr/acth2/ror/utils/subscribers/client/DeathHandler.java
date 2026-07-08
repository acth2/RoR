package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = References.MODID)
public class DeathHandler {
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            PlayerStats stats = PlayerStats.get(serverPlayer);
            int coins = CoinsManager.getCoins(serverPlayer);

            ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SyncPlayerStatsPacket(stats));
            ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SyncCoinsPacket(coins));
        }
    }
}
