package fr.acth2.ror.network;

import fr.acth2.ror.gui.DeathScreenGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenDeathScreenPacket {

    public OpenDeathScreenPacket() {}

    public static void encode(OpenDeathScreenPacket packet, PacketBuffer buffer) {

    }

    public static OpenDeathScreenPacket decode(PacketBuffer buffer) {
        return new OpenDeathScreenPacket();
    }

    public static void handle(OpenDeathScreenPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new DeathScreenGui());
        });
        context.get().setPacketHandled(true);
    }
}