package dev.thew.teleports;

import dev.thew.teleports.handler.HandlerService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Teleports extends JavaPlugin {

    @Getter
    @Setter
    private static Teleports instance;
    private final HandlerService service = new HandlerService();

    @Override
    public void onEnable() {
        setInstance(this);
        service.load();
    }

    @Override
    public void onDisable() {}
}
