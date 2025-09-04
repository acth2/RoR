package fr.acth2.ror.network.realmvessel;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DimensionSyncPacket {
    private final String dimensionId;
    private final Hand hand;

    public DimensionSyncPacket(String dimensionId, Hand hand) {
        this.dimensionId = dimensionId;
        this.hand = hand;
    }

    public static void encode(DimensionSyncPacket msg, PacketBuffer buffer) {
        buffer.writeUtf(msg.dimensionId);
        buffer.writeEnum(msg.hand);
    }

    public static DimensionSyncPacket decode(PacketBuffer buffer) {
        return new DimensionSyncPacket(buffer.readUtf(), buffer.readEnum(Hand.class));
    }

    public static void handle(DimensionSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = player.getItemInHand(msg.hand);
                if (stack.getItem() == fr.acth2.ror.init.ModItems.REALMS_VESSEL.get()) {
                    CompoundNBT nbt = stack.getOrCreateTag();
                    nbt.putString("SelectedDimension", msg.dimensionId);
                    stack.setTag(nbt);

                    String dimensionName = getDimensionName(msg.dimensionId);
                    TextFormatting color = dimensionName.equals("Skyria") ? TextFormatting.AQUA : TextFormatting.GREEN;
                    stack.setHoverName(new StringTextComponent("Realm Vessel: " + dimensionName).withStyle(color));

                    System.out.println("Server updated Realm Vessel to: " + dimensionName);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static String getDimensionName(String dimensionId) {
        if (dimensionId.equals("minecraft:overworld")) {
            return "Overworld";
        } else if (dimensionId.equals("ror:skyria")) {
            return "Skyria";
        } else {
            return "Unknown";
        }
    }
}