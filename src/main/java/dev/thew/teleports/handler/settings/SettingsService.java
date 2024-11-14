package dev.thew.teleports.handler.settings;

import dev.thew.teleports.Teleports;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsService implements SettingsHandler {

    private FileConfiguration config;

    @Override
    public int getMaxRequests(){
        return config.getInt("max-requests");
    }

    @Override
    public void load() {
        Teleports plugin = Teleports.getInstance();
        config = plugin.getConfig();
    }

    @Override
    public void shutdown() {

    }
}
