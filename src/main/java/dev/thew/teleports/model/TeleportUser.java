package dev.thew.teleports.model;

import dev.thew.teleports.handler.HandlerService;
import dev.thew.teleports.handler.settings.SettingsHandler;
import dev.thew.teleports.handler.settings.SettingsService;
import dev.thew.teleports.request.TeleportRequest;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TeleportUser {
    private final LinkedHashMap<String, TeleportRequest> teleportRequestQueue = new LinkedHashMap<>();

    public boolean hasOutstandingRequest(String playerUsername) {
        final TeleportRequest request = getOutstandingRequest(playerUsername, false);
        return request != null;
    }

    public @Nullable TeleportRequest getOutstandingRequest(String playerUsername, boolean inform) {
        if (!teleportRequestQueue.containsKey(playerUsername)) return null;

        final long timeout = 120;
        final TeleportRequest request = teleportRequestQueue.get(playerUsername);

        if (System.currentTimeMillis() - request.getTime() <= timeout * 1000) return request;

        teleportRequestQueue.remove(playerUsername);
        if (inform) {
            // TODO message TIMEOUT
        }
        return null;
    }

    public boolean hasPendingRequests(boolean inform, boolean excludeHere) {
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
                if (excludeHere) continue;

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
        final TeleportRequest request = teleportRequestQueue.getOrDefault(user.getName(), new TeleportRequest(user.getName()));

        request.setTime(System.currentTimeMillis());
        request.setLocation(user.getLocation());

        teleportRequestQueue.remove(request.getName());
        SettingsHandler settingsHandler = HandlerService.getHandler(SettingsService.class);
        if (teleportRequestQueue.size() >= settingsHandler.getMaxRequests()) {
            final List<String> keys = new ArrayList<>(getPendingKeys());
            teleportRequestQueue.remove(keys.get(keys.size() - 1));
        }

        teleportRequestQueue.put(request.getName(), request);
    }

    public Collection<String> getPendingKeys() {
        return teleportRequestQueue.keySet();
    }

}
