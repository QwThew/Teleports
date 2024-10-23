package dev.thew.teleports;

import dev.thew.teleports.command.cmd.tpa.TPACommand;
import dev.thew.teleports.command.cmd.tpaccept.TPAcceptCommand;
import dev.thew.teleports.command.cmd.tpdeny.TPDenyCommand;
import dev.thew.teleports.message.MessageService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Teleports extends JavaPlugin {

    @Getter
    @Setter
    private static Teleports instance;

    @Override
    public void onEnable() {
        setInstance(this);

        if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
        FileConfiguration config = getConfig();

        MessageService.load(config);

        setupCommands();
    }

    @Override
    public void onDisable() {

    }

    private void setupCommands(){
        TPACommand tpaCommand = new TPACommand();
        TPAcceptCommand tpAcceptCommand = new TPAcceptCommand();
        TPDenyCommand tpDenyCommand = new TPDenyCommand();

        hookCommand("tpa", tpaCommand);
        hookCommand("tpaccept", tpAcceptCommand);
        hookCommand("tpdeny", tpDenyCommand);
    }

    private void hookCommand(String command, TabExecutor tab) {
        PluginCommand pluginCommand = Teleports.getInstance().getCommand(command);
        if (pluginCommand == null) return;

        pluginCommand.setExecutor(tab);
        pluginCommand.setTabCompleter(tab);
    }
}
