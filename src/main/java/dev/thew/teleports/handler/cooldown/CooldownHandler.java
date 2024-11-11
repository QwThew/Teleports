package dev.thew.teleports.handler.cooldown;

import dev.thew.teleports.Teleports;
import dev.thew.teleports.handler.Handler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public abstract class CooldownHandler implements Listener, Handler {

    public CooldownHandler() {
        Teleports teleports = Teleports.getInstance();
        Bukkit.getPluginManager().registerEvents(this, teleports);
    }

    private final HashMap<Player, Long> cooldown = new HashMap<>();

    @Override
    public void load(){}

    @Override
    public void shutdown(){}

    public boolean inCooldown(final Player player) {
        long maxCooldown = getCooldown(player) * 1000L;
        long current = System.currentTimeMillis();
        long diff = current + maxCooldown;
        long time = cooldown.getOrDefault(player, 0L);

        if (diff >= time) {
            cooldown.put(player, current);
            return true;
        } else return false;
    }

    public String renderCooldown(Player player){
        long current = System.currentTimeMillis();
        long time = cooldown.getOrDefault(player, 0L);
        long diff = current - time;
        int hours = (int) (diff / 3600);
        int minutes = (int) ((diff - hours * 3600) / 60);
        int seconds = (int) (diff - hours * 3600 - minutes * 60);

        return (hours > 0 ? hours + " часов " : "") + (minutes > 0 ? minutes + " минут " : "") + seconds + " секунд";
    }

    public abstract int getCooldown(Player player);

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        cooldown.remove(event.getPlayer());
    }
}
