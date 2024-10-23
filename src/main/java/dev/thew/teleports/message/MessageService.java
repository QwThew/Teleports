package dev.thew.teleports.message;

import dev.thew.teleports.message.messages.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService {

    private MessageService() {
        throw new IllegalStateException("MessageService class");
    }

    private static final Map<String, Message> messagesMap = new HashMap<>();

    public static void load(FileConfiguration config) {
        String defaultPath = "messages.";

        List<Message> messages = List.of(
                new Cooldown("tpa-cooldown", defaultPath, config),

                new Request("tpa-requester", defaultPath, config),
                new RequestAccept("tpa-requester-accept", defaultPath, config),
                new RequestDeny("tpa-requester-deny", defaultPath, config),
                new RequestTimeOut("tpa-requester-timeout", defaultPath, config),

                new TargetRequest("tpa-requested-request", defaultPath, config),
                new TargetAccept("tpa-requested-accept", defaultPath, config),
                new TargetDeny("tpa-requested-deny", defaultPath, config),
                new TargetTimeOut("tpa-requested-timeout", defaultPath, config)
        );

        registerMessages(messages);
    }

    public static void registerMessages(@NotNull List<Message> messages) {
        for (Message message : messages)
            messagesMap.put(message.getName(), message);
    }

    public static @NotNull Message getMessage(String name) {
        Message message = messagesMap.get(name);
        if (message == null)
            return new Message.EmptyMessage();
        return message;
    }

    public static void sendMessage(Player player, String messageName, Object... args) {
        Message message = getMessage(messageName);
        message.execute(player, args);
    }

}
