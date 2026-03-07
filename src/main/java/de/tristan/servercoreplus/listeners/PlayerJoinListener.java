package de.tristan.servercoreplus.listeners;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.LocationUtil;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinListener implements Listener {

    private final ServerCorePlusPlugin plugin;

    public PlayerJoinListener(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", player.getName());

        if (!player.hasPlayedBefore()) {
            event.setJoinMessage(MessageUtil.get("first-join-message", placeholders));
        } else {
            event.setJoinMessage(MessageUtil.get("join-message", placeholders));
        }

        if (plugin.getConfig().getBoolean("settings.teleport-to-spawn-on-join", false)) {
            Location spawn = LocationUtil.getSpawn(plugin);
            if (spawn != null) {
                player.teleport(spawn);
            }
        }
    }
}
