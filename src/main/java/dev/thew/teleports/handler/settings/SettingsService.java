package dev.thew.teleports.handler.settings;

import dev.thew.teleports.Teleports;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsService implements SettingsHandler {

    private FileConfiguration config;

    @Override
    public void load() {
        Teleports plugin = Teleports.getInstance();
        config = plugin.getConfig();
    }

    @Override
    public int getMaxRequests(){
        return config.getInt("max-requests");
    }

    @Override
    public int getTeleportDelay(){
        return config.getInt("delay");
    }

    @Override
    public int getTeleportTimeOut(){
        return config.getInt("time-out");
    }

    @Override
    public void shutdown() {
        // TODO document why this method is empty
    }
}
