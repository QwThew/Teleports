package dev.thew.teleports.handler;

import dev.thew.teleports.handler.message.MessageHandler;
import dev.thew.teleports.handler.message.MessageService;
import dev.thew.teleports.handler.settings.SettingsHandler;
import dev.thew.teleports.handler.settings.SettingsService;
import dev.thew.teleports.handler.user.UserHandler;
import dev.thew.teleports.handler.user.UserService;

import java.util.HashMap;

public final class HandlerService {

    private static final HashMap<String, Handler> handlers = new HashMap<>();

    public void load() {

        //UserService
        UserHandler userHandler = new UserService();
        addHandler(userHandler);

        //SettingsService
        SettingsHandler settingsHandler = new SettingsService();
        addHandler(settingsHandler);

        MessageHandler messageHandler = new MessageService();
        addHandler(messageHandler);

        loadHandler();
    }

    public void shutdown() {
        for (Handler handler : handlers.values()) handler.shutdown();
    }

    private void loadHandler() {
        for (Handler handler : handlers.values()) handler.load();
    }

    public static <T extends Handler> T getHandler(Class<T> handlerClass) {
        String name = handlerClass.getSimpleName();

        Handler handler = handlers.get(name);
        return handlerClass.cast(handler);
    }

    public void addHandler(Handler handler) {
        handlers.put(handler.getClass().getSimpleName(), handler);
    }


}
