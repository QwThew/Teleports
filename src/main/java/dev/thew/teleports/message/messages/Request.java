package dev.thew.teleports.message.messages;

import dev.thew.teleports.message.Message;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Request extends Message {

    public Request(String name, String path, FileConfiguration config) {
        super(name, path, config);
    }

    @Override
    public @NonNull String finallyText(@NotNull Player player, Object... args) {
        return text;
    }


}
