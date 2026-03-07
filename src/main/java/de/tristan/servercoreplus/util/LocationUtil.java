package de.tristan.servercoreplus.util;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public final class LocationUtil {

    private LocationUtil() {
    }

    public static Location getSpawn(ServerCorePlusPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        String worldName = config.getString("spawn.world");
        World world = Bukkit.getWorld(worldName == null ? "world" : worldName);
        if (world == null) {
            return null;
        }

        return new Location(
                world,
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch")
        );
    }

    public static void setSpawn(ServerCorePlusPlugin plugin, Location location) {
        plugin.getConfig().set("spawn.world", location.getWorld() != null ? location.getWorld().getName() : "world");
        plugin.getConfig().set("spawn.x", location.getX());
        plugin.getConfig().set("spawn.y", location.getY());
        plugin.getConfig().set("spawn.z", location.getZ());
        plugin.getConfig().set("spawn.yaw", location.getYaw());
        plugin.getConfig().set("spawn.pitch", location.getPitch());
        plugin.saveConfig();
    }
}
