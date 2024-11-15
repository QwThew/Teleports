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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commandtpdeny implements TabExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String s, String @NonNull [] args) {
        if (!(sender instanceof Player receiverPlayer)) return false;
        UserHandler userHandler = HandlerService.getHandler(UserService.class);
        User user = userHandler.getUser(receiverPlayer);

        List<String> playerName = new ArrayList<>(Arrays.asList(args));
        boolean allDeny = playerName.contains("*") || playerName.contains("all");

        if (!user.hasPendingRequests(false, false)) {
            // TODO user message no pending request
            return false;
        }

        TeleportRequest denyRequest = null;
        if (args.length > 0) {
            if (allDeny) {
                denyAllRequests(user, userHandler);
                return true;
            }

            User senderRequest = userHandler.getUser(args[0]);
            if (senderRequest != null) denyRequest = user.getOutstandingRequest(senderRequest.getName(), false);
        } else denyRequest = user.getNextRequest(false, true, false);

        if (denyRequest == null) {
            // TODO message no pending request
            return false;
        }

        final User player = userHandler.getUser(denyRequest.getName());
        if (player == null || !player.getPlayer().isOnline()) {
            // TODO message no pending request
            return false;
        }

        // TODO user message request denied
        // TODO player message request denied from user
        user.removeRequest(denyRequest.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String s, String @NonNull [] args) {
        return Collections.emptyList();
    }

    private void denyAllRequests(User user, UserHandler userHandler) {
        TeleportRequest request;
        //int count = 0;
        while ((request = user.getNextRequest(false, true, false)) != null) {
            final User player = userHandler.getUser(request.getName());

            if (player != null && player.getPlayer().isOnline()) {
                // TODO player message request denied from user
            }

            user.removeRequest(request.getName());
            //count++;
        }
        // TODO user message request denied all with count
    }

}
