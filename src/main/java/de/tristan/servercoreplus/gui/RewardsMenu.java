package de.tristan.servercoreplus.gui;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.MessageUtil;
import de.tristan.servercoreplus.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RewardsMenu {

    private static final long DAILY_COOLDOWN = 24L * 60L * 60L * 1000L;
    private final ServerCorePlusPlugin plugin;

    public RewardsMenu(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, MessageUtil.get("menu-title"));

        inventory.setItem(11, createStarterItem(player));
        inventory.setItem(15, createDailyItem(player));

        player.openInventory(inventory);
    }

    private ItemStack createStarterItem(Player player) {
        boolean claimed = plugin.getPlayerDataManager().hasClaimedStarter(player.getUniqueId());
        Material material = claimed ? Material.BARRIER : Material.CHEST;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.get("menu-starter-name"));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.get("menu-starter-lore-1"));
            lore.add(claimed ? MessageUtil.get("menu-starter-claimed-lore") : MessageUtil.get("menu-starter-lore-2"));
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createDailyItem(Player player) {
        long lastClaim = plugin.getPlayerDataManager().getLastDailyClaim(player.getUniqueId());
        long remaining = (lastClaim + DAILY_COOLDOWN) - System.currentTimeMillis();
        boolean available = remaining <= 0;
        Material material = available ? Material.CLOCK : Material.GRAY_DYE;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.get("menu-daily-name"));
            List<String> lore = new ArrayList<>();
            lore.add(MessageUtil.get("menu-daily-lore-1"));
            lore.add(available
                    ? MessageUtil.get("menu-daily-lore-2")
                    : MessageUtil.get("menu-daily-claimed-lore").replace("{time}", TimeUtil.formatDuration(remaining)));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
