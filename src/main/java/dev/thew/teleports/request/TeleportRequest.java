package dev.thew.teleports.request;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

@Getter
@Setter
public class TeleportRequest {

    private final String name;
    private final UUID requesterUuid;
    private Location location;
    private long time;

    public TeleportRequest(String name, UUID requesterUuid) {
        this.name = name;
        this.requesterUuid = requesterUuid;
    }

}
