package dev.thew.teleports.handler.user;

import dev.thew.teleports.Teleports;
import dev.thew.teleports.model.User;
import dev.thew.teleports.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class UserService implements UserHandler, Listener {

    private final HashMap<Player, User> users = new HashMap<>();

    @Override
    public void load() {
        Teleports instance = Teleports.getInstance();
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    @Override
    public User getUser(Player player) {
        return users.getOrDefault(player, null);
    }

    @Override
    public User getUser(String playerName) {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) return null;
        return getUser(player);
    }

    @Override
    public User loadUser(Player player) {
        User user = getUser(player);
        if (user != null) return user;

        return new User(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = loadUser(player);
        users.put(player, user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        User user = getUser(player);
        unloadUser(user, true);
    }

    @Override
    public void unloadUser(User user, boolean isCached) {
        FileUtils.saveFileUser(user);

        if (isCached)
            Bukkit.getScheduler().runTaskLaterAsynchronously(Teleports.getInstance(), () -> users.remove(user.getPlayer()), 3600L);
    }

    @Override
    public List<User> getUsers(){
        return new ArrayList<>(users.values());
    }

    @Override
    public void shutdown() {
        users.forEach((key, value) -> unloadUser(value, false));
    }
}
