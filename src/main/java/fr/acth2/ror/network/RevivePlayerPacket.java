package fr.acth2.ror.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RevivePlayerPacket {

    public RevivePlayerPacket() {}

    public static void encode(RevivePlayerPacket packet, PacketBuffer buffer) {

    }

    public static RevivePlayerPacket decode(PacketBuffer buffer) {
        return new RevivePlayerPacket();
    }


    public static void handle(RevivePlayerPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                player.setHealth(player.getMaxHealth());
                player.getFoodData().setFoodLevel(20);
                player.clearFire();
                player.setInvulnerable(false);

                player.connection.send(new SPlayerAbilitiesPacket(player.abilities));
                player.connection.send(new SUpdateHealthPacket(player.getHealth(), player.getFoodData().getFoodLevel(), player.getFoodData().getSaturationLevel()));
            }
        });
        context.get().setPacketHandled(true);
    }
}