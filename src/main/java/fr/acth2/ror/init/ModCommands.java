package fr.acth2.ror.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import fr.acth2.ror.gui.coins.CoinsManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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
    }

    private static int modifyCoins(CommandSource source, ServerPlayerEntity player, int amount) {
        source.sendSuccess(new StringTextComponent("Changed " + player.getName().getString() + "'s coins to " + amount), true);
        CoinsManager.setCoins(player, amount);

        player.sendMessage(new StringTextComponent("Your coins have been put to " + amount + " by an administrator"), player.getUUID());
        return 1;
    }

    private static int showUsage(CommandSource source) {
        source.sendFailure(new StringTextComponent(TextFormatting.RED + "/coins <player> <coins>"));
        return 1;
    }
}