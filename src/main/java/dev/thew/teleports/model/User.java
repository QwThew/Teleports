package dev.thew.teleports.model;

import dev.thew.teleports.utils.FileUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public final class User extends TeleportUser {

    @Getter
    private final Player player;
    @Getter
    private final String name;
    @Getter
    private final UUID UUID;
    private final List<String> ignoredPlayers;
    @Getter
    @Setter
    private long timeLastTeleport;

    @Getter
    private boolean teleportEnabled;

    public User(@NonNull Player player) {
        this.player = player;

        this.name = player.getName();
        this.UUID = player.getUniqueId();

        FileUser loadUser = FileUtils.loadUser(this.UUID.toString());
        this.ignoredPlayers = loadUser.preIgnoredList();
        this.timeLastTeleport = loadUser.preLastTimeTeleport();
        this.teleportEnabled = true;
    }

    public boolean isAuthorized(String permission) {
        return player.hasPermission(permission);
    }

    public void addIgnoredPlayer(String playerName) {
        ignoredPlayers.add(playerName);
    }

    public void removeIgnoredPlayer(String playerName) {
        ignoredPlayers.remove(playerName);
    }

    public boolean isIgnoredPlayer(String playerName) {
        return ignoredPlayers.contains(playerName);
    }

    public void sendMessage(String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public Location getLocation(){
        return this.player.getLocation();
    }

    public List<String> getIgnoredPlayers() {
        return Collections.unmodifiableList(ignoredPlayers);
    }

    public void playSound(Location location, Sound sound, float volume, float pitch) {
        this.player.playSound(location, sound, volume, pitch);
    }

    public record FileUser(List<String> preIgnoredList, long preLastTimeTeleport) {
    }

}
