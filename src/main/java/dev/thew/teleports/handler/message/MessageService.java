package dev.thew.teleports.handler.message;

import dev.thew.teleports.utils.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageService implements MessageHandler{

    public static String COMMAND_NOT_FOUND;

    static {
        YamlConfiguration messages = FileUtils.loadConfiguration("messages.yml");


    }

    @Override
    public void load() {
        // TODO document why this method is empty
    }

    @Override
    public void shutdown() {
        // TODO document why this method is empty
    }
}
