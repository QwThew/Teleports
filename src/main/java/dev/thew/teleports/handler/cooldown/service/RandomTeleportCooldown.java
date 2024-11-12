package dev.thew.teleports.handler.cooldown.service;

import dev.thew.teleports.handler.cooldown.CooldownHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashMap;

public class RandomTeleportCooldown extends CooldownHandler implements Listener {

    @Override
    public int getCooldown(final Player player) {
        int max = 0;

        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if (!permission.getPermission().startsWith("teleports.random.cooldown.")) continue;

            String[] permissionParts = permission.getPermission().split("\\.");
            if (permissionParts.length != 3) continue;

            int limit = Integer.parseInt(permissionParts[2]);
            if (limit > max) max = limit;
        }

        return max;
    }

}
