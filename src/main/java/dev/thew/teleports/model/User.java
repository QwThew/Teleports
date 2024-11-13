package dev.thew.teleports.model;

import dev.thew.teleports.utils.FileUtils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class User {

    @Getter
    private final Player player;
    @Getter
    private final String name;
    @Getter
    private final String uuid;
    private final List<String> ignoredPlayers;
    @Getter
    private long timeLastTeleport;

    public User(@NonNull Player player){
        this.player = player;

        this.name = player.getName();
        this.uuid = player.getUniqueId().toString();

        FileUser loadUser = FileUtils.loadUser(this.uuid);
        this.ignoredPlayers = loadUser.preIgnoredList();
        this.timeLastTeleport = loadUser.preLastTimeTeleport();
    }

    public boolean isAuthorized(String permission){
        return player.hasPermission(permission);
    }

    public void addIgnoredPlayer(String playerName){
        ignoredPlayers.add(playerName);
    }

    public void removeIgnoredPlayer(String playerName){
        ignoredPlayers.remove(playerName);
    }

    public boolean isIgnoredPlayer(String playerName){
        return ignoredPlayers.contains(playerName);
    }

    public List<String> getIgnoredPlayers(){
        return Collections.unmodifiableList(ignoredPlayers);
    }

    public record FileUser(List<String> preIgnoredList, long preLastTimeTeleport) {
    }


}
