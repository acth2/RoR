package fr.acth2.ror.network.skills;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class RequestSyncPlayerStatsPacket {
    public RequestSyncPlayerStatsPacket() {}

    public RequestSyncPlayerStatsPacket(PacketBuffer buffer) {}

    public void encode(PacketBuffer buffer) {}

    public static void handle(RequestSyncPlayerStatsPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                PlayerStats stats = PlayerStats.get(player);
                ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerStatsPacket(stats));
            }
        });
        context.get().setPacketHandled(true);
    }
}