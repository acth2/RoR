package fr.acth2.ror.network.skills;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class RequestSyncPlayerStatsPacket {

    public RequestSyncPlayerStatsPacket() {
    }

    public static void encode(RequestSyncPlayerStatsPacket msg, PacketBuffer buf) {
    }

    public static RequestSyncPlayerStatsPacket decode(PacketBuffer buf) {
        return new RequestSyncPlayerStatsPacket();
    }

    public static void handle(RequestSyncPlayerStatsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender != null) {
                PlayerStats stats = PlayerStats.get(sender);
                ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SyncPlayerStatsPacket(stats));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
