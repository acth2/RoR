package fr.acth2.ror.network.coins;

import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

public class SyncCoinsPacket {
    private final int coins;

    public SyncCoinsPacket(int coins) {
        this.coins = coins;
    }

    public static void encode(SyncCoinsPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.coins);
    }

    public static SyncCoinsPacket decode(PacketBuffer buf) {
        return new SyncCoinsPacket(buf.readInt());
    }

    public static void handle(SyncCoinsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            CoinsManager.setClientCoins(msg.coins);
        });
        ctx.get().setPacketHandled(true);
    }
}
