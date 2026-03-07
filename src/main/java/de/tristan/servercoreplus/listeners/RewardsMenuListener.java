package de.tristan.servercoreplus.listeners;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RewardsMenuListener implements Listener {

    private final ServerCorePlusPlugin plugin;

    public RewardsMenuListener(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String stripped = org.bukkit.ChatColor.stripColor(event.getView().getTitle());
        String expected = org.bukkit.ChatColor.stripColor(MessageUtil.get("menu-title"));
        if (stripped == null || !stripped.equalsIgnoreCase(expected)) {
            return;
        }

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        switch (event.getSlot()) {
            case 11 -> player.performCommand("starter");
            case 15 -> player.performCommand("daily");
            default -> {
                return;
            }
        }

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getRewardsMenu().open(player), 1L);
    }
}
