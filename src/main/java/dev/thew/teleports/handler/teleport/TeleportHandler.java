package dev.thew.teleports.handler.teleport;

import dev.thew.teleports.handler.Handler;
import dev.thew.teleports.handler.request.TeleportRequest;
import org.bukkit.entity.Player;

public interface TeleportHandler extends Handler {
    void putRequest(Player player, TeleportRequest request);
    TeleportRequest requestExist(Player requester);
}
