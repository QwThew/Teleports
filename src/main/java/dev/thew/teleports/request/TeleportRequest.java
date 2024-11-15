package dev.thew.teleports.request;

import dev.thew.teleports.model.User;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

@Getter
@Setter
public class TeleportRequest {

    private final String name;
    private Location location;
    private long time;
    private boolean complete = true;

    public TeleportRequest(String name) {
        this.name = name;
    }

    public void execute(User sender){

    }

    public void accept(){
        if (!isValid()) return;
    }

    public void reject(){

    }

    private boolean isValid(){
        return complete;
    }

}
