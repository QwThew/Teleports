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

import java.util.Collections;
import java.util.List;

public class Commandtpaccept implements TabExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        if (!(commandSender instanceof Player player)) return false;
        UserHandler userHandler = HandlerService.getHandler(UserService.class);
        User user = userHandler.getUser(player);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        return Collections.emptyList();
    }
}
