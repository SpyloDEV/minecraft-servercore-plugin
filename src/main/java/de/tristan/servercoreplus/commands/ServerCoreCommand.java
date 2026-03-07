package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ServerCoreCommand implements CommandExecutor, TabCompleter {

    private final ServerCorePlusPlugin plugin;

    public ServerCoreCommand(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            MessageUtil.send(sender, "reloaded");
            return true;
        }

        sender.sendMessage(MessageUtil.color("&8[&bServerCorePlus&8] &7Usage: &f/servercore reload"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("reload");
        }
        return List.of();
    }
}
