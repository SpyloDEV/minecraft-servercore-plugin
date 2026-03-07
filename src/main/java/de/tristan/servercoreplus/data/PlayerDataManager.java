package de.tristan.servercoreplus.data;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataManager {

    private final ServerCorePlusPlugin plugin;
    private File file;
    private FileConfiguration config;

    public PlayerDataManager(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                plugin.getLogger().severe("Could not create playerdata.yml: " + exception.getMessage());
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException exception) {
            plugin.getLogger().severe("Could not save playerdata.yml: " + exception.getMessage());
        }
    }

    public boolean hasClaimedStarter(UUID uuid) {
        return config.getBoolean(path(uuid, "starter-claimed"), false);
    }

    public void setClaimedStarter(UUID uuid, boolean value) {
        config.set(path(uuid, "starter-claimed"), value);
        save();
    }

    public long getLastDailyClaim(UUID uuid) {
        return config.getLong(path(uuid, "last-daily-claim"), 0L);
    }

    public void setLastDailyClaim(UUID uuid, long timestamp) {
        config.set(path(uuid, "last-daily-claim"), timestamp);
        save();
    }

    private String path(UUID uuid, String key) {
        return "players." + uuid + "." + key;
    }
}
