package dev.thew.teleports.command.cmd.tpa;

import dev.thew.teleports.command.LongCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TPACommand extends LongCommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return false;
        if (!(sender instanceof Player player)) return false;

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length <= 1) return null;
        return List.of("");
    }
}
