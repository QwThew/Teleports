package dev.thew.teleports.model;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.settings.SettingsHandler;
import dev.thew.teleports.handler.settings.SettingsService;
import dev.thew.teleports.request.AsyncTeleport;
import dev.thew.teleports.request.TeleportRequest;
import dev.thew.teleports.utils.FileUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class User {

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
    private final transient LinkedHashMap<String, TeleportRequest> teleportRequestQueue = new LinkedHashMap<>();
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

    public boolean hasOutstandingRequest(String playerUsername, boolean here) {
        final TeleportRequest request = getOutstandingRequest(playerUsername, false);
        return request != null && request.isHere() == here;
    }

    public @Nullable TeleportRequest getOutstandingRequest(String playerUsername, boolean inform) {
        if (!teleportRequestQueue.containsKey(playerUsername)) {
            return null;
        }

        final long timeout = 120;
        final TeleportRequest request = teleportRequestQueue.get(playerUsername);

        if (System.currentTimeMillis() - request.getTime() <= timeout * 1000) return request;

        teleportRequestQueue.remove(playerUsername);
        if (inform) {
            // TODO message TIMEOUT
        }
        return null;
    }

    public boolean hasPendingTpaRequests(boolean inform, boolean excludeHere) {
        return getNextRequest(inform, false, excludeHere) != null;
    }

    public TeleportRequest removeRequest(String playerUsername) {
        return teleportRequestQueue.remove(playerUsername);
    }

    public TeleportRequest getNextRequest(boolean inform, boolean ignoreExpirations, boolean excludeHere) {
        if (teleportRequestQueue.isEmpty()) return null;

        final long timeout = 120;
        final List<String> keys = new ArrayList<>(teleportRequestQueue.keySet());
        Collections.reverse(keys);

        TeleportRequest nextRequest = null;
        for (final String key : keys) {
            final TeleportRequest request = teleportRequestQueue.get(key);
            if (System.currentTimeMillis() - request.getTime() <= TimeUnit.SECONDS.toMillis(timeout)) {
                if (excludeHere && request.isHere()) continue;

                if (ignoreExpirations) return request;
                else if (nextRequest == null) nextRequest = request;
            } else {
                if (inform) {
                    // TODO message TIMEOUT
                }
                teleportRequestQueue.remove(key);
            }
        }
        return nextRequest;
    }

    public void requestTeleport(final User user) {
        final TeleportRequest request = teleportRequestQueue.getOrDefault(user.getName(), new TeleportRequest(user.getName(), user.getUUID()));

        request.setTime(System.currentTimeMillis());
        request.setLocation(user.getLocation());

        // Handle max queue size
        teleportRequestQueue.remove(request.getName());
        SettingsHandler settingsHandler = HandlerService.getHandler(SettingsService.class);
        if (teleportRequestQueue.size() >= settingsHandler.getMaxRequests()) {
            final List<String> keys = new ArrayList<>(teleportRequestQueue.keySet());
            teleportRequestQueue.remove(keys.get(keys.size() - 1));
        }

        // Add request to queue
        teleportRequestQueue.put(request.getName(), request);
    }

    public List<String> getIgnoredPlayers() {
        return Collections.unmodifiableList(ignoredPlayers);
    }

    public Collection<String> getPendingTpaKeys() {
        return teleportRequestQueue.keySet();
    }

    public record FileUser(List<String> preIgnoredList, long preLastTimeTeleport) {
    }

}
