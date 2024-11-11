package dev.thew.teleports.handler.request.requests;

import dev.thew.teleports.handler.cooldown.CooldownHandler;
import dev.thew.teleports.handler.message.MessageHandler;
import dev.thew.teleports.handler.request.TeleportRequest;
import dev.thew.teleports.handler.request.TypeRequest;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
public class RandomTeleportRequest extends TeleportRequest {

    private final Location randomLocation;
    private final CooldownHandler cooldownHandler;

    public RandomTeleportRequest(Player requester, Location randomLocation, CooldownHandler cooldownHandler) {
       super(requester, cooldownHandler);
        this.randomLocation = randomLocation;

        this.cooldownHandler = cooldownHandler;
    }

    @Override
    public void execute() {
        Player requester = getRequester();
        MessageHandler messageHandler = getMessageHandler();

        requester.teleport(randomLocation);
        messageHandler.sendMessage(requester, "tpa-requester", randomLocation);
    }

    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }

    @Override
    public void timeout() {

    }

    @Override
    public boolean isValid() {
        Player requester = getRequester();

        return !cooldownHandler.inCooldown(requester) && requester != null && randomLocation != null;
    }

    @Override
    public TypeRequest getType(@NonNull Player player) {
        if (!player.equals(getRequester())) return null;
        return TypeRequest.REQUESTER;
    }
}
