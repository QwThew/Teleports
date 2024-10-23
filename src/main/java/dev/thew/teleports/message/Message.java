package dev.thew.teleports.message;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@Getter
public abstract class Message {

    protected String name;
    protected String text;
    protected Sound sound;
    protected float volume;
    protected float pitch;

    protected Message(String name, String defaultPath, FileConfiguration config){
        if (config == null) return;

        String path = defaultPath + name;
        this.text = config.getString(path + ".text");

        String soundText = config.getString(path + ".sound");
        this.sound = null;
        if (soundText != null && !soundText.equalsIgnoreCase("none") && !soundText.equalsIgnoreCase("null"))
            this.sound = Sound.valueOf(soundText.toUpperCase());

        this.volume = config.getString(path + ".volume") == null ? 1 : config.getInt(path + ".volume");
        this.pitch = config.getString(path + ".pitch") == null ? 1 : config.getInt(path + ".pitch");
        this.name = name;
    }

    @NonNull
    public abstract String finallyText(@NonNull Player player, Object... args);

    public void execute(@NonNull Player player, Object... args) {
        player.sendMessage(finallyText(player, args));

        if (sound != null)
            player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public String replace(String oldChar, String newText){
        String output = this.text;
        return output.replace(oldChar, newText);
    }


    public static class EmptyMessage extends Message{
        public EmptyMessage(){
            super(null, null, null);
        }

        @Override
        public @NonNull String finallyText(@NonNull Player player, Object... args) {
            return "";
        }
    }
}
