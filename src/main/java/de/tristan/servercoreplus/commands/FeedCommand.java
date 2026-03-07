package de.tristan.servercoreplus.commands;

import de.tristan.servercoreplus.ServerCorePlusPlugin;
import de.tristan.servercoreplus.util.CooldownManager;
import de.tristan.servercoreplus.util.MessageUtil;
import de.tristan.servercoreplus.util.TimeUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class FeedCommand implements CommandExecutor, TabCompleter {

    private final ServerCorePlusPlugin plugin;
    private final CooldownManager cooldownManager = new CooldownManager();

    public FeedCommand(ServerCorePlusPlugin plugin) {
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
            int cooldown = plugin.getConfig().getInt("settings.feed-cooldown-seconds", 45);
            if (cooldownManager.isOnCooldown(player.getUniqueId(), cooldown)) {
                MessageUtil.send(player, "cooldown", "time", TimeUtil.formatDuration(cooldownManager.getRemainingMillis(player.getUniqueId(), cooldown)));
                return true;
            }

            player.setFoodLevel(20);
            player.setSaturation(20F);
            cooldownManager.setUsedNow(player.getUniqueId());
            MessageUtil.send(player, "feed-self");
            return true;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            MessageUtil.send(sender, "player-not-found");
            return true;
        }

        target.setFoodLevel(20);
        target.setSaturation(20F);
        MessageUtil.send(sender, "feed-other", "player", target.getName());
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
