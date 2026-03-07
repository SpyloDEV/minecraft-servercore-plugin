package de.tristan.servercoreplus.util;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static List<ItemStack> loadConfiguredItems(ServerCorePlusPlugin plugin, String path) {
        List<ItemStack> items = new ArrayList<>();
        List<?> rawList = plugin.getConfig().getList(path);
        if (rawList == null) {
            return items;
        }

        for (Object entry : rawList) {
            if (!(entry instanceof Map<?, ?> map)) {
                continue;
            }

            Object materialObject = map.containsKey("material") ? map.get("material") : "STONE";
            Object amountObject = map.containsKey("amount") ? map.get("amount") : 1;

            String materialName = String.valueOf(materialObject);
            int amount;
            try {
                amount = Integer.parseInt(String.valueOf(amountObject));
            } catch (NumberFormatException exception) {
                amount = 1;
            }

            Material material = Material.matchMaterial(materialName);
            if (material == null || material.isAir()) {
                continue;
            }

            items.add(new ItemStack(material, Math.max(1, amount)));
        }

        return items;
    }
}
