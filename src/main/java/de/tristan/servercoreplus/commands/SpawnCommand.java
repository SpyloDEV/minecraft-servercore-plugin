package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.LocationUtil;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final ServerCorePlusPlugin plugin;

    public SpawnCommand(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "only-player");
            return true;
        }

        Location spawn = LocationUtil.getSpawn(plugin);
        if (spawn == null) {
            MessageUtil.send(player, "spawn-missing");
            return true;
        }

        player.teleport(spawn);
        MessageUtil.send(player, "spawn-teleport");
        return true;
    }
}
