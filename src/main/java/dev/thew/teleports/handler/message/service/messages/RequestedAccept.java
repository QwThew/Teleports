package dev.thew.teleports.handler.message.service.messages;

import dev.thew.teleports.handler.message.service.Message;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RequestedAccept extends Message {
    
    public RequestedAccept(String name, String path, FileConfiguration config) {
        super(name, path, config);
    }

    @Override
    public @NonNull String finallyText(@NonNull Player player, Object... args) {
        Player requester = args[0] instanceof Player ? (Player) args[0] : player;
        return text.replace("%requester%", requester.getName());
    }
}
