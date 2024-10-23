package dev.thew.teleports.command;

import dev.thew.teleports.Teleports;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class LongCommandExecutor implements TabExecutor {
    @Getter(AccessLevel.PROTECTED)
    private final List<SubCommandWrapper> subCommands = new ArrayList<>();

    protected void add(SubCommand command, String[] aliases, Permission permission) {
        this.subCommands.add(new SubCommandWrapper(command, aliases, permission));
    }

    @Nullable
    protected SubCommandWrapper getWrapperFromLabel(String label) {
        for (SubCommandWrapper wrapper: subCommands)
            for (String alias: wrapper.aliases)
                if (alias.equalsIgnoreCase(label))
                    return wrapper;

        return null;
    }

    protected List<String> getAliases() {
        final List<String> result = new ArrayList<>();
        for (final SubCommandWrapper wrapper: subCommands) {
            String alias = wrapper.aliases[0];
            result.add(alias);
        }
        return result;
    }



    public record SubCommandWrapper(SubCommand command, String[] aliases, Permission permission) {}
}
