package dev.thew.teleports.message.messages;

import dev.thew.teleports.message.Message;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RequestTimeOut extends Message {

    public RequestTimeOut(String name, String path, FileConfiguration config) {
        super(name, path, config);
    }

    @Override
    public @NonNull String finallyText(@NonNull Player player, Object... args) {
        return text;
    }
}
