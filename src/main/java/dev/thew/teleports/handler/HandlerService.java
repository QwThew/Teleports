package dev.thew.teleports.handler;

import dev.thew.teleports.handler.command.CommandService;
import dev.thew.teleports.handler.cooldown.service.PlayerTeleportCooldown;
import dev.thew.teleports.handler.cooldown.service.RandomTeleportCooldown;
import dev.thew.teleports.handler.message.service.MessageService;
import dev.thew.teleports.handler.teleport.service.TeleportService;

import java.util.HashMap;

public class HandlerService implements Handler {

    private static final HashMap<String, Handler> handlersMap = new HashMap<>();

    @Override
    public void load() {

        // TeleportHandler
        addHandler(new TeleportService());

        // CooldownHandler
        addHandler(new PlayerTeleportCooldown(), new RandomTeleportCooldown());

        // CommandHandler
        addHandler(new CommandService());

        // MessageHandler
        addHandler(new MessageService());

        loadHandlers();
    }

    public static <T extends Handler> T getHandler(Class<T> handlerClass) {
        String name = handlerClass.getSimpleName();

        Handler handler = handlersMap.get(name);
        return handlerClass.cast(handler);
    }

    private void loadHandlers() {
        for (Handler handler : handlersMap.values()) handler.load();
    }

    private void addHandler(Handler... handlers) {
        for (Handler handler : handlers) handlersMap.put(handler.getClass().getSimpleName(), handler);
    }

    @Override
    public void shutdown(){
        for (Handler handler : handlersMap.values()) handler.shutdown();
    }

}
