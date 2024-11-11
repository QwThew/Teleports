package dev.thew.teleports.command.tpa;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.cooldown.CooldownHandler;
import dev.thew.teleports.handler.cooldown.service.PlayerTeleportCooldown;
import dev.thew.teleports.handler.request.TeleportRequest;
import dev.thew.teleports.handler.request.requests.PlayerTeleportRequest;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;


import java.util.List;

public class TPACommand implements TabExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (args.length != 1) return false;
        if (!(sender instanceof Player requester)) return false;

        String targetName = args[0];
        Player requested = Bukkit.getPlayerExact(targetName);
        if (requested == null) return false;

        CooldownHandler cooldownHandler = HandlerService.getHandler(PlayerTeleportCooldown.class);
        TeleportRequest teleportRequest = new PlayerTeleportRequest(requester, requested, cooldownHandler);

        if (!teleportRequest.isValid()) return false;

        teleportRequest.execute();
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String @NonNull [] args) {
        if (args.length <= 1) return null;
        return List.of("");
    }
}
