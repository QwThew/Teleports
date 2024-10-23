package dev.thew.teleports.request.requests;

import dev.thew.teleports.message.MessageService;
import dev.thew.teleports.request.TeleportRequest;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RandomTeleportRequest extends TeleportRequest {

    private final Location randomLocation;

    public RandomTeleportRequest(Player requster, Location randomLocation) {
        super(requster);
        this.randomLocation = randomLocation;
    }

    @Override
    public void execute() {
        requester.teleport(randomLocation);
        MessageService.sendMessage(requester, "tpa-requester", randomLocation);
    }
}
