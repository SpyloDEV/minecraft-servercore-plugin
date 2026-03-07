package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.ItemUtil;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StarterCommand implements CommandExecutor {

    private final ServerCorePlusPlugin plugin;

    public StarterCommand(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "only-player");
            return true;
        }

        if (!plugin.getConfig().getBoolean("settings.starter-kit-enabled", true)) {
            MessageUtil.send(player, "starter-disabled");
            return true;
        }

        if (plugin.getPlayerDataManager().hasClaimedStarter(player.getUniqueId())) {
            MessageUtil.send(player, "starter-already");
            return true;
        }

        List<ItemStack> items = ItemUtil.loadConfiguredItems(plugin, "settings.starter-kit.items");
        for (ItemStack item : items) {
            player.getInventory().addItem(item).values().forEach(overflow ->
                    player.getWorld().dropItemNaturally(player.getLocation(), overflow)
            );
        }

        plugin.getPlayerDataManager().setClaimedStarter(player.getUniqueId(), true);
        MessageUtil.send(player, "starter-claimed");
        return true;
    }
}
