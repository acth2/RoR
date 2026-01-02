package fr.acth2.ror.network.skills;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class RequestLevelUpPacket {
    private final String stat;

    public RequestLevelUpPacket(String stat) {
        this.stat = stat;
    }

    public RequestLevelUpPacket(PacketBuffer buffer) {
        this.stat = buffer.readUtf();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeUtf(stat);
    }

    public static void handle(RequestLevelUpPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                PlayerStats stats = PlayerStats.get(player);
                int coins = CoinsManager.getCoins(player);

                if (stats.canLevelUp(packet.stat, coins)) {
                    if (coins >= stats.getLevelUpCost(packet.stat)) {
                        stats.levelUp(packet.stat, player);
                        CoinsManager.removeCoins(player, stats.getLevelUpCost(packet.stat));
                        ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerStatsPacket(stats));
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}