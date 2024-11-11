package dev.thew.teleports.handler.request;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.cooldown.CooldownHandler;
import dev.thew.teleports.handler.message.MessageHandler;
import dev.thew.teleports.handler.message.service.MessageService;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
public abstract class TeleportRequest {

    private final Player requester;
    private final MessageHandler messageHandler;
    private final CooldownHandler cooldownHandler;

    public TeleportRequest(Player requester, CooldownHandler cooldownHandler) {
        this.requester = requester;

        this.messageHandler = HandlerService.getHandler(MessageService.class);
        this.cooldownHandler = cooldownHandler;
    }

    public abstract void execute();
    public abstract void accept();
    public abstract void reject();
    public abstract void timeout();
    public abstract boolean isValid();
    public abstract TypeRequest getType(@NonNull Player player);
}
