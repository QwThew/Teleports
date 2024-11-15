package dev.thew.teleports.commands;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.user.UserHandler;
import dev.thew.teleports.handler.user.UserService;
import dev.thew.teleports.model.User;
import dev.thew.teleports.request.TeleportRequest;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class Commandtpaccept implements TabExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        if (!(commandSender instanceof Player player)) return false;
        UserHandler userHandler = HandlerService.getHandler(UserService.class);
        User user = userHandler.getUser(player);

        if (args.length == 0){
            TeleportRequest request = user.getNextRequest(true, false, false);
            request.accept();
        } else {
            List<String> playerName = new ArrayList<>(Arrays.asList(args));

            boolean allAccepted = playerName.contains("*");

            if (!allAccepted) {
                Collection<String> keys = user.getPendingKeys();
                for (String key : keys){
                    TeleportRequest request = user.getOutstandingRequest(key, true);
                    if (!playerName.contains(key) || request == null) continue;

                    request.accept();
                }

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        return Collections.emptyList();
    }
}
