package fr.acth2.ror.network.traveler;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import fr.acth2.ror.gui.coins.CoinsManager;

import java.util.function.Supplier;

public class PurchaseItemPacket {
    private final ItemStack item;
    private final int cost;

    public PurchaseItemPacket(ItemStack item, int cost) {
        this.item = item;
        this.cost = cost;
    }

    public static void encode(PurchaseItemPacket msg, PacketBuffer buf) {
        buf.writeItemStack(msg.item, false);
        buf.writeInt(msg.cost);
    }

    public static PurchaseItemPacket decode(PacketBuffer buf) {
        return new PurchaseItemPacket(buf.readItem(), buf.readInt());
    }
    public static void handle(PurchaseItemPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) return;

            int currentCoins = CoinsManager.getCoins(player);
            if (currentCoins >= msg.cost) {
                CoinsManager.removeCoins(player, msg.cost);

                if (!player.inventory.add(msg.item.copy())) {
                    player.drop(msg.item.copy(), false);
                }

                player.sendMessage(new StringTextComponent("Purchased " +
                        msg.item.getHoverName().getString() +
                        " for " + msg.cost + " coins!"), player.getUUID());

                ModNetworkHandler.INSTANCE.sendTo(
                        new SyncCoinsPacket(CoinsManager.getCoins(player)),
                        player.connection.getConnection(),
                        NetworkDirection.PLAY_TO_CLIENT
                );
            } else {
                player.sendMessage(new StringTextComponent("Not enough coins!"), player.getUUID());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
