package dev.thew.teleports.handler.message;

import dev.thew.teleports.handler.Handler;
import dev.thew.teleports.handler.message.service.Message;
import lombok.NonNull;
import org.bukkit.entity.Player;

public interface MessageHandler extends Handler {

    @NonNull
    Message getMessage(String name);
    void sendMessage(Player player, String messageName, Object... args);

}
