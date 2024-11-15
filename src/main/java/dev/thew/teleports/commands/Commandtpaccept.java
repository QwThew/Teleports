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
import java.util.concurrent.CompletableFuture;

public class Commandtpaccept implements TabExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        if (!(commandSender instanceof Player player)) return false;
        UserHandler userHandler = HandlerService.getHandler(UserService.class);
        User user = userHandler.getUser(player);
        if (user == null) return false;

        List<String> playerName = new ArrayList<>(Arrays.asList(args));
        boolean allAccepted = playerName.contains("*") || playerName.contains("all");

        if (!user.hasPendingRequests(true, allAccepted)) return false; // TODO user message no pending request

        if (args.length > 0){
            if (allAccepted) {
                acceptAllRequests(user, commandLabel, userHandler);
                return true;
            }

            // TODO user message request accepted

            User senderRequest = userHandler.getUser(args[0]);
            if (senderRequest == null) return false; // TODO user message user offline
            handleTeleport(user, user.getOutstandingRequest(senderRequest.getName(), true), commandLabel, userHandler);
        } else {

            // TODO user message request accepted

            TeleportRequest request = user.getNextRequest(true, false, false);
            handleTeleport(user, request, commandLabel, userHandler);
        }
        return true;
    }

    private void acceptAllRequests(final User user, final String commandLabel, final UserHandler userHandler) {
        TeleportRequest request;
        //int count = 0;
        while ((request = user.getNextRequest(true, true, true)) != null) {
            try {
                handleTeleport(user, request, commandLabel, userHandler);
                //count++;
            } catch (Exception ignored) {}
            finally {
                user.removeRequest(request.getName());
            }
        }
        // TODO user message request all accepted with count
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String commandLabel, String @NonNull [] args) {
        return Collections.emptyList();
    }

    private void handleTeleport(final User user, final TeleportRequest request, String commandLabel, final UserHandler userHandler) {
        if (request == null) return; // TODO message no pending request

        final User senderRequest = userHandler.getUser(request.getName());

        if (!senderRequest.getPlayer().isOnline()) {
            user.removeRequest(request.getName());
            // TODO message no pending request
            return;
        }

        // TODO senderRequest message request accepted from user

        final CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.exceptionally(e -> {
            // TODO user message pending teleport was cancelle
            return false;
        });

        //final AsyncTeleport teleport = senderRequest.getAsyncTeleport();
        //teleport.setTpType(AsyncTeleport.TeleportType.TPA);
        //teleport.teleport(user.getBase(), charge, PlayerTeleportEvent.TeleportCause.COMMAND, future);

        user.removeRequest(request.getName());
    }
}
