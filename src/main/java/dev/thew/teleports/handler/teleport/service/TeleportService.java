package dev.thew.teleports.handler.teleport.service;

import dev.thew.teleports.handler.request.TeleportRequest;
import dev.thew.teleports.handler.teleport.TeleportHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeleportService implements TeleportHandler {

    private final HashMap<Player, TeleportRequest> requests = new HashMap<>();

    @Override
    public void load() {

    }

    @Override
    public void putRequest(Player player, TeleportRequest request) {
        requests.put(player, request);
    }

    @Override
    public void shutdown() {
        for (TeleportRequest request : requests.values()) request.timeout();
    }

    public TeleportRequest requestExist(Player requester) {
        return requests.get(requester);
    }
}
