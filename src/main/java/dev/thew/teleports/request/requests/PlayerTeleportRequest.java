package dev.thew.teleports.request.requests;

import dev.thew.teleports.message.MessageService;
import dev.thew.teleports.request.TeleportRequest;
import org.bukkit.entity.Player;

public class PlayerTeleportRequest extends TeleportRequest {

    private final Player requested;

    public PlayerTeleportRequest(Player requster, Player requested) {
        super(requster);
        this.requested = requested;
    }

    @Override
    public void execute() {
        requester.teleport(requested);
        MessageService.sendMessage(requester, "tpa-requester", requested);
        MessageService.sendMessage(requested, "tpa-requested", requester);
    }
}
