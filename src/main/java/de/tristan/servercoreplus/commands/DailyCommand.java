package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.ItemUtil;
import de.tristan.servercoreplus.util.MessageUtil;
import de.tristan.servercoreplus.util.TimeUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyCommand implements CommandExecutor {

    private static final long DAILY_COOLDOWN = 24L * 60L * 60L * 1000L;
    private final ServerCorePlusPlugin plugin;

    public DailyCommand(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "only-player");
            return true;
        }

        if (!plugin.getConfig().getBoolean("settings.daily-reward-enabled", true)) {
            MessageUtil.send(player, "daily-disabled");
            return true;
        }

        long lastClaim = plugin.getPlayerDataManager().getLastDailyClaim(player.getUniqueId());
        long now = System.currentTimeMillis();
        long remaining = (lastClaim + DAILY_COOLDOWN) - now;

        if (remaining > 0) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("time", TimeUtil.formatDuration(remaining));
            MessageUtil.send(player, "daily-wait", placeholders);
            return true;
        }

        List<ItemStack> items = ItemUtil.loadConfiguredItems(plugin, "settings.daily-reward.items");
        for (ItemStack item : items) {
            player.getInventory().addItem(item).values().forEach(overflow ->
                    player.getWorld().dropItemNaturally(player.getLocation(), overflow)
            );
        }

        plugin.getPlayerDataManager().setLastDailyClaim(player.getUniqueId(), now);
        MessageUtil.send(player, "daily-claimed");
        return true;
    }
}
