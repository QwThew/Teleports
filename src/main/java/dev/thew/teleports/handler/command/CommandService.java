package dev.thew.teleports.handler.command;

import dev.thew.teleports.Teleports;
import dev.thew.teleports.command.tpa.TPACommand;
import dev.thew.teleports.command.tpaccept.TPAcceptCommand;
import dev.thew.teleports.command.tpdeny.TPDenyCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

public class CommandService implements CommandHandler {

    @Override
    public void load() {
        hookCommand("tpa", new TPACommand());
        hookCommand("tpaccept", new TPAcceptCommand());
        hookCommand("tpdeny", new TPDenyCommand());
    }

    @Override
    public void shutdown() {}

    private void hookCommand(String command, TabExecutor tab) {
        PluginCommand pluginCommand = Teleports.getInstance().getCommand(command);
        if (pluginCommand == null) return;

        pluginCommand.setExecutor(tab);
        pluginCommand.setTabCompleter(tab);
    }
}
