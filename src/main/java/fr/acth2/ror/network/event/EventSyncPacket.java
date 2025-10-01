package fr.acth2.ror.network.event;

import fr.acth2.ror.events.data.ClientEventData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EventSyncPacket {
    private final String eventName;
    private final boolean active;

    public EventSyncPacket(String eventName, boolean active) {
        this.eventName = eventName;
        this.active = active;
    }

    public static void encode(EventSyncPacket packet, PacketBuffer buffer) {
        buffer.writeUtf(packet.eventName);
        buffer.writeBoolean(packet.active);
    }

    public static EventSyncPacket decode(PacketBuffer buffer) {
        return new EventSyncPacket(buffer.readUtf(32767), buffer.readBoolean());
    }

    public static void handle(EventSyncPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get().getDirection().getReceptionSide().isClient()) {
                if ("broken_moon".equals(packet.eventName)) {
                    ClientEventData.setBrokenMoonActive(packet.active);
                } else if ("blood_sun".equals(packet.eventName)) {
                    ClientEventData.setBloodSunActive(packet.active);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}