package fr.acth2.ror.network.skills.dexterity;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

public class DodgePacket {

    public DodgePacket() {}

    public static void encode(DodgePacket msg, PacketBuffer buffer) {

    }

    public static DodgePacket decode(PacketBuffer buffer) {
        return new DodgePacket();
    }

    public static void handle(DodgePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.hurtTime = 0;
                Minecraft.getInstance().player.hurtDuration = 0;

                System.out.println("DodgePacket handled: Attack was dodged on the client side!");
            }
        });

        ctx.get().setPacketHandled(true);
    }
}