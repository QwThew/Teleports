package dev.thew.teleports.handler.request.requests;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.cooldown.CooldownHandler;
import dev.thew.teleports.handler.message.MessageHandler;
import dev.thew.teleports.handler.request.TeleportRequest;
import dev.thew.teleports.handler.request.TypeRequest;
import dev.thew.teleports.handler.teleport.TeleportHandler;
import dev.thew.teleports.handler.teleport.service.TeleportService;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
public class PlayerTeleportRequest extends TeleportRequest {

    private final Player requested;
    private final CooldownHandler cooldownHandler;

    public PlayerTeleportRequest(Player requester, Player requested, CooldownHandler cooldownHandler) {
        super(requester, cooldownHandler);

        this.requested = requested;
        this.cooldownHandler = cooldownHandler;

        TeleportHandler handler = HandlerService.getHandler(TeleportService.class);
        handler.putRequest(requested, this);
        handler.putRequest(requester, this);
    }

    @Override
    public void execute() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        messageHandler.sendMessage(requester, "tpa-requester-request", requested);
        messageHandler.sendMessage(requested, "tpa-requested-request", requester);
    }

    @Override
    public void accept() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        requester.teleport(requested);
        messageHandler.sendMessage(requester, "tpa-requester-accept", requested);
        messageHandler.sendMessage(requested, "tpa-requested-accept", requester);
    }

    @Override
    public void reject() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        messageHandler.sendMessage(requester, "tpa-requester-deny", requested);
        messageHandler.sendMessage(requested, "tpa-requested-deny", requester);
    }

    @Override
    public void timeout() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        messageHandler.sendMessage(requester, "tpa-requester-timeout", requested);
        messageHandler.sendMessage(requested, "tpa-requested-timeout", requester);
    }

    @Override
    public boolean isValid() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        if (!requester.hasPermission("teleports.cooldown.bypass") && cooldownHandler.inCooldown(requester)) {
            String cooldown = cooldownHandler.renderCooldown(requester);
            messageHandler.sendMessage(requester, "tp-cooldown", cooldown);
            return false;
        }

        return true;
    }

    @Override
    public TypeRequest getType(@NonNull Player player) {
        if (player.equals(requested)) return TypeRequest.REQUESTED;
        else if (player.equals(getRequested())) return TypeRequest.REQUESTER;
        else return null;
    }


}
