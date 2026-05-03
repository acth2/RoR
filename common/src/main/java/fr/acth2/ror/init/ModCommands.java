package fr.acth2.ror.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.acth2.ror.events.ServerEventManager;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.utils.subscribers.gen.utils.data.GeneratedStructuresData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;

import java.util.Set;

public class ModCommands  {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("coins")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> showUsage(context.getSource()))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> showUsage(context.getSource()))
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> modifyCoins(
                                                context.getSource(),
                                                EntityArgument.getPlayer(context, "player"),
                                                IntegerArgumentType.getInteger(context, "amount")
                                        )))));

        dispatcher.register(
                Commands.literal("rorstruc")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> showStructuresUsage(context.getSource()))
                        .then(Commands.argument("structure_id", StringArgumentType.string())
                                .suggests((context, builder) -> {
                                    builder.suggest("sealed_treasure_t1");
                                    builder.suggest("sealed_treasure_t2");
                                    builder.suggest("sealed_treasure_t3");
                                    builder.suggest("skyria_tower");
                                    builder.suggest("skyria_house");
                                    return builder.buildFuture();
                                })
                                .executes(context -> listStructures(
                                        context.getSource(),
                                        StringArgumentType.getString(context, "structure_id")
                                ))));


        dispatcher.register(
                Commands.literal("triggerevent")
                        .then(Commands.argument("event", StringArgumentType.string())
                                .then(Commands.argument("active", BoolArgumentType.bool())
                                        .executes(context -> {
                                            String event = StringArgumentType.getString(context, "event");
                                            boolean active = BoolArgumentType.getBool(context, "active");
                                            ServerEventManager.forceEvent(event, active);
                                            context.getSource().sendSuccess(
                                                    new StringTextComponent(
                                                            "Event " + event + " set to " + active
                                                    ), true
                                            );
                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("status")
                                .executes(context -> {
                                    String status = ServerEventManager.getEventStatus();
                                    context.getSource().sendSuccess(
                                            new StringTextComponent("Event Status: " + status),
                                            true
                                    );
                                    return 1;
                                })
                        )
        );
    }

    private static int listStructures(CommandSource source, String structureId) {
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            source.sendFailure(new StringTextComponent("This command can only be used by players"));
            return 0;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
        ServerWorld world = player.getLevel();
        GeneratedStructuresData data = GeneratedStructuresData.get(world);

        Set<BlockPos> structures = data.getStructures(structureId);

        if (structures.isEmpty()) {
            source.sendSuccess(new StringTextComponent(TextFormatting.YELLOW + "No " + structureId + " structures have been generated yet."), false);
            return 1;
        }

        source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Found " + structures.size() + " " + structureId + " structures:"), false);

        for (BlockPos pos : structures) {
            String coords = String.format("X: %d, Y: %d, Z: %d", pos.getX(), pos.getY(), pos.getZ());
            source.sendSuccess(new StringTextComponent(TextFormatting.WHITE + "- " + coords), false);

            StringTextComponent teleport = new StringTextComponent(TextFormatting.BLUE + "[Teleport]" + TextFormatting.GRAY + " (You wont be telported to the structure dimension)");
            teleport.withStyle(style -> style.withClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
            )));
            source.sendSuccess(teleport, false);
        }

        return 1;
    }

    private static int showStructuresUsage(CommandSource source) {
        source.sendFailure(new StringTextComponent(TextFormatting.RED + "Usage: /rorstruc <structure_id>"));
        source.sendFailure(new StringTextComponent(TextFormatting.RED + "Available structures: sealed_treasure_t1, sealed_treasure_t2, sealed_treasure_t3, skyria_house, skyria_tower"));
        return 1;
    }

    private static int modifyCoins(CommandSource source, ServerPlayerEntity player, int amount) {
        CoinsManager.setCoins(player, amount);
        source.sendSuccess(new StringTextComponent("Changed " + player.getName().getString() + "'s coins to " + amount), true);

        player.sendMessage(new StringTextComponent("Your coins have been put to " + amount + " by an administrator"), player.getUUID());
        return 1;
    }

    private static int showUsage(CommandSource source) {
        source.sendFailure(new StringTextComponent(TextFormatting.RED + "/coins <player> <coins>"));
        return 1;
    }
}