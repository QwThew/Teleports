package dev.thew.teleports.utils;

import dev.thew.teleports.Teleports;
import lombok.NonNull;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static @NonNull YamlConfiguration loadConfiguration(String fileName) {

        Plugin instance = Teleports.getInstance();

        instance.saveResource(fileName, false);
        File file = new File(instance.getDataFolder(), fileName);

        return YamlConfiguration.loadConfiguration(file);
    }
}
