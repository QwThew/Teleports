package dev.thew.teleports.handler.user;

import dev.thew.teleports.handler.Handler;
import dev.thew.teleports.model.User;
import org.bukkit.entity.Player;

public interface UserHandler extends Handler {

    User getUser(Player player);
    User createUser(Player player);
    void shutdownUser(User user);


}
