package fr.acth2.ror.network.coins;

import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestCoinSyncPacket {
    public static void handle(RequestCoinSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                CoinsManager.syncCoins(player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}