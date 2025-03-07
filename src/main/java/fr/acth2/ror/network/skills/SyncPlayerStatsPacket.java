package fr.acth2.ror.network.skills;

import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerStatsPacket {
    private final int level;
    private final int health;
    private final int stamina;
    private final int strength;

    public SyncPlayerStatsPacket(int level, int health, int stamina, int strength) {
        this.level = level;
        this.health = health;
        this.stamina = stamina;
        this.strength = strength;
    }

    public SyncPlayerStatsPacket(PlayerStats stats) {
        this(stats.getLevel(), stats.getHealth(), stats.getStamina(), stats.getStrength());
    }

    public SyncPlayerStatsPacket(PacketBuffer buffer) {
        this.level = buffer.readInt();
        this.health = buffer.readInt();
        this.stamina = buffer.readInt();
        this.strength = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(level);
        buffer.writeInt(health);
        buffer.writeInt(stamina);
        buffer.writeInt(strength);
    }

    public static void handle(SyncPlayerStatsPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof MainMenuGui) {
                MainMenuGui gui = (MainMenuGui) minecraft.screen;
                gui.updateStats(packet.level, packet.health, packet.stamina, packet.strength);
            }
        });
        context.get().setPacketHandled(true);
    }
}