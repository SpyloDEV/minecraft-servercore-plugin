package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.CooldownManager;
import de.tristan.servercoreplus.util.MessageUtil;
import de.tristan.servercoreplus.util.TimeUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HealCommand implements CommandExecutor, TabCompleter {

    private final ServerCorePlusPlugin plugin;
    private final CooldownManager cooldownManager = new CooldownManager();

    public HealCommand(ServerCorePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null && args.length == 0) {
            MessageUtil.send(sender, "only-player");
            return true;
        }

        if (args.length == 0) {
            int cooldown = plugin.getConfig().getInt("settings.heal-cooldown-seconds", 45);
            if (cooldownManager.isOnCooldown(player.getUniqueId(), cooldown)) {
                MessageUtil.send(player, "cooldown", "time", TimeUtil.formatDuration(cooldownManager.getRemainingMillis(player.getUniqueId(), cooldown)));
                return true;
            }

            double maxHealth = player.getAttribute(Attribute.MAX_HEALTH) != null
                    ? player.getAttribute(Attribute.MAX_HEALTH).getValue()
                    : 20.0;
            player.setHealth(maxHealth);
            player.setFireTicks(0);
            cooldownManager.setUsedNow(player.getUniqueId());
            MessageUtil.send(player, "heal-self");
            return true;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            MessageUtil.send(sender, "player-not-found");
            return true;
        }

        double maxHealth = target.getAttribute(Attribute.MAX_HEALTH) != null
                ? target.getAttribute(Attribute.MAX_HEALTH).getValue()
                : 20.0;
        target.setHealth(maxHealth);
        target.setFireTicks(0);
        MessageUtil.send(sender, "heal-other", "player", target.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return Collections.emptyList();
    }
}
