package de.tristan.servercoreplus;

import de.tristan.servercoreplus.commands.DailyCommand;
import de.tristan.servercoreplus.commands.FeedCommand;
import de.tristan.servercoreplus.commands.HealCommand;
import de.tristan.servercoreplus.commands.RewardsCommand;
import de.tristan.servercoreplus.commands.ServerCoreCommand;
import de.tristan.servercoreplus.commands.SetSpawnCommand;
import de.tristan.servercoreplus.commands.SpawnCommand;
import de.tristan.servercoreplus.commands.StarterCommand;
import de.tristan.servercoreplus.data.PlayerDataManager;
import de.tristan.servercoreplus.gui.RewardsMenu;
import de.tristan.servercoreplus.listeners.PlayerJoinListener;
import de.tristan.servercoreplus.listeners.RewardsMenuListener;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerCorePlusPlugin extends JavaPlugin {

    private static ServerCorePlusPlugin instance;
    private PlayerDataManager playerDataManager;
    private RewardsMenu rewardsMenu;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResourceIfMissing("messages.yml");

        this.playerDataManager = new PlayerDataManager(this);
        this.rewardsMenu = new RewardsMenu(this);
        MessageUtil.load(this);

        registerCommands();
        registerListeners();

        getLogger().info("ServerCorePlus has been enabled.");
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.save();
        }
        getLogger().info("ServerCorePlus has been disabled.");
    }

    public void reloadPlugin() {
        reloadConfig();
        MessageUtil.load(this);
        playerDataManager.reload();
    }

    private void registerCommands() {
        register("spawn", new SpawnCommand(this));
        register("setspawn", new SetSpawnCommand(this));
        register("starter", new StarterCommand(this));
        register("daily", new DailyCommand(this));
        register("rewards", new RewardsCommand(this));
        register("heal", new HealCommand(this));
        register("feed", new FeedCommand(this));
        register("servercore", new ServerCoreCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new RewardsMenuListener(this), this);
    }

    private void register(String name, Object executor) {
        PluginCommand command = getCommand(name);
        if (command == null) {
            getLogger().warning("Command not found in plugin.yml: " + name);
            return;
        }

        if (executor instanceof org.bukkit.command.CommandExecutor commandExecutor) {
            command.setExecutor(commandExecutor);
        }
        if (executor instanceof org.bukkit.command.TabCompleter tabCompleter) {
            command.setTabCompleter(tabCompleter);
        }
    }

    private void saveResourceIfMissing(String path) {
        if (getResource(path) != null && !new java.io.File(getDataFolder(), path).exists()) {
            saveResource(path, false);
        }
    }

    public static ServerCorePlusPlugin getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public RewardsMenu getRewardsMenu() {
        return rewardsMenu;
    }
}
