package fr.acth2.ror.network.realmvessel;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DimensionSyncPacket {
    private final String dimensionId;
    private final InteractionHand InteractionHand;

    public DimensionSyncPacket(String dimensionId, InteractionHand InteractionHand) {
        this.dimensionId = dimensionId;
        this.InteractionHand = InteractionHand;
    }

    public static void encode(DimensionSyncPacket msg, PacketBuffer buffer) {
        buffer.writeUtf(msg.dimensionId);
        buffer.writeEnum(msg.InteractionHand);
    }

    public static DimensionSyncPacket decode(PacketBuffer buffer) {
        return new DimensionSyncPacket(buffer.readUtf(), buffer.readEnum(InteractionHand.class));
    }

    public static void handle(DimensionSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = player.getItemInHand(msg.InteractionHand);
                if (stack.getItem() == fr.acth2.ror.init.ModItems.REALMS_VESSEL.get()) {
                    CompoundNBT nbt = stack.getOrCreateTag();
                    nbt.putString("SelectedDimension", msg.dimensionId);
                    stack.setTag(nbt);

                    String dimensionName = getDimensionName(msg.dimensionId);
                    ChatFormatting color = dimensionName.equals("Skyria") ? ChatFormatting.AQUA : ChatFormatting.GREEN;
                    stack.setHoverName(new TextComponent("Realm Vessel: " + dimensionName).withStyle(color));

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