package dev.thew.teleports.commands;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.user.UserHandler;
import dev.thew.teleports.handler.user.UserService;
import dev.thew.teleports.model.User;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class Commandtpa implements TabExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String @NonNull [] args) {
        if (args.length < 1) return false;
        if (!(commandSender instanceof Player senderPlayer)) return false;

        UserHandler userHandler = HandlerService.getHandler(UserService.class);
        final User sender = userHandler.getUser(senderPlayer);
        final User receiver = userHandler.getUser(args[0]);
//        if (receiver == null) return false;
//
//        if (sender.getName().equalsIgnoreCase(receiver.getName())) {
//            return false; // TODO message
//        }
//
//        if (!receiver.isTeleportEnabled()) {
//            return false; // TODO message
//        }
//
//        if (receiver.hasOutstandingRequest(sender.getName(), false)) {
//            return false; // TODO message
//        }
//
//        if (!receiver.isIgnoredPlayer(sender.getName())) {
//            receiver.requestTeleport(sender, false);
//        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String s, String @NonNull [] args) {
        return List.of();
    }
}
