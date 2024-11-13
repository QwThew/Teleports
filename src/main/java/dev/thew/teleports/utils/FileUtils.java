package dev.thew.teleports.utils;

import dev.thew.teleports.Teleports;
import dev.thew.teleports.model.User;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

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

    public static @NonNull User.FileUser loadUser(String uuid) {
        Plugin instance = Teleports.getInstance();

        File file = new File(instance.getDataFolder(), uuid);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        List<String> ignoredPlayer = yml.getStringList("ignored");
        long lastTimeTeleport = yml.getLong("lastTeleport");

        return new User.FileUser(ignoredPlayer, lastTimeTeleport);
    }

    @SneakyThrows
    public static void saveFileUser(User user){
        Plugin instance = Teleports.getInstance();

        File file = new File(instance.getDataFolder(), user.getUuid());

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set("ignored", user.getIgnoredPlayers());
        yml.set("lastTeleport", user.getTimeLastTeleport());

        yml.save(file);
    }
}
