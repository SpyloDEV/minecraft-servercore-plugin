package de.tristan.servercoreplus.util;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class MessageUtil {

    private static FileConfiguration messages;
    private static String prefix = "";

    private MessageUtil() {
    }

    public static void load(ServerCorePlusPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(file);
        prefix = color(messages.getString("prefix", "&8[&bPlugin&8] &7"));
    }

    public static String get(String key) {
        return color(messages.getString(key, "Missing message: " + key));
    }

    public static String get(String key, Map<String, String> placeholders) {
        String message = get(key);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }

    public static void send(CommandSender sender, String key) {
        sender.sendMessage(prefix + get(key));
    }

    public static void send(CommandSender sender, String key, String placeholder, String value) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put(placeholder, value);
        sender.sendMessage(prefix + get(key, placeholders));
    }

    public static void send(CommandSender sender, String key, Map<String, String> placeholders) {
        sender.sendMessage(prefix + get(key, placeholders));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
