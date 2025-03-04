package fr.acth2.ror.init;

import fr.acth2.ror.network.coins.RequestCoinSyncPacket;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import fr.acth2.ror.network.traveler.PurchaseItemPacket;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraft.util.ResourceLocation;

public class ModNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("ror", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, PurchaseItemPacket.class, PurchaseItemPacket::encode,
                PurchaseItemPacket::decode, PurchaseItemPacket::handle);

        INSTANCE.registerMessage(id++, SyncCoinsPacket.class, SyncCoinsPacket::encode,
                SyncCoinsPacket::decode, SyncCoinsPacket::handle);

        INSTANCE.registerMessage(id++, RequestCoinSyncPacket.class,
                (msg, buf) -> {},  buf -> new RequestCoinSyncPacket(),
                RequestCoinSyncPacket::handle
        );
    }
}