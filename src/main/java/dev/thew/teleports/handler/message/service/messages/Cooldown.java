package dev.thew.teleports.handler.message.service.messages;

import dev.thew.teleports.handler.message.service.Message;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Cooldown extends Message {

    public Cooldown(String name, String path, FileConfiguration config) {
        super(name, path, config);
    }

    @Override
    public @NonNull String finallyText(@NonNull Player player, Object... args) {
        String time = args[0] instanceof String ? (String) args[0] : "";
        return text.replace("%time%", time);
    }


}
