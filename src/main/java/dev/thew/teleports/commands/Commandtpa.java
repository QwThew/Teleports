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
        if (receiver == null) return true; // TODO sender message User not found

        if (sender.getName().equalsIgnoreCase(receiver.getName())) {
            return true; // TODO sender message cant teleport urself
        }

        if (!receiver.isTeleportEnabled()) {
            return true; // TODO sender message receiver disable teleport
        }

        if (receiver.hasOutstandingRequest(sender.getName())) {
            return true; // TODO sender message receiver already has teleport request
        }

        if (receiver.isIgnoredPlayer(sender.getName())) {
            return true; // TODO sender message receiver ignored you
        }

        receiver.requestTeleport(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String s, String @NonNull [] args) {
        return List.of();
    }
}
