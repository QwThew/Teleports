package dev.thew.teleports.handler.message.service;

import dev.thew.teleports.handler.message.MessageHandler;
import dev.thew.teleports.handler.message.service.messages.*;
import dev.thew.teleports.utils.FileUtils;
import lombok.NonNull;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService implements MessageHandler {

    private final Map<String, Message> messagesMap = new HashMap<>();

    @Override
    public void load() {
        YamlConfiguration config = FileUtils.loadConfiguration("messages.yml");

        String defaultPath = "messages.";

        List<Message> messages = List.of(
                new Cooldown("tp-cooldown", defaultPath, config),

                new RequesterRequest("tpa-requester-request", defaultPath, config),
                new RequesterAccept("tpa-requester-accept", defaultPath, config),
                new RequesterDeny("tpa-requester-deny", defaultPath, config),
                new RequesterTimeOut("tpa-requester-timeout", defaultPath, config),

                new RequestedRequest("tpa-requested-request", defaultPath, config),
                new RequestedAccept("tpa-requested-accept", defaultPath, config),
                new RequestedDeny("tpa-requested-deny", defaultPath, config),
                new RequestedTimeOut("tpa-requested-timeout", defaultPath, config)
        );

        registerMessages(messages);
    }

    @Override
    public void sendMessage(Player player, String messageName, Object... args) {
        Message message = getMessage(messageName);
        message.execute(player, args);
    }

    public void registerMessages(@NonNull List<Message> messages) {
        for (Message message : messages) messagesMap.put(message.getName(), message);
    }

    public @NonNull Message getMessage(String name) {
        Message message = messagesMap.get(name);
        return message == null ? new Message.EmptyMessage() : message;
    }

    @Override
    public void shutdown() {}
}
