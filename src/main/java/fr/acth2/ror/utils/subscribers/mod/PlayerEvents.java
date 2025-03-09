package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.PlayerStatsCapability;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            System.out.println("Attaching PlayerStats capability to player");
            event.addCapability(
                    new ResourceLocation(References.MODID, "player_stats"),
                    new PlayerStatsCapability()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            PlayerStats stats = PlayerStats.get(player);
            ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerStatsPacket(stats));
        }
    }
}