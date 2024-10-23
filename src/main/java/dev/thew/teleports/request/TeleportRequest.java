package dev.thew.teleports.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public abstract class TeleportRequest {

    protected Player requester;
    public abstract void execute();

}
