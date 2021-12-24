package com.jmistri.rtsmp.commands;

import com.jmistri.rtsmp.SMPPlugin;
import com.jmistri.rtsmp.managers.CountdownManager;
import com.jmistri.rtsmp.tasks.DaytimeCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class DayCommand implements CommandExecutor {

    public int taskID;
    private SMPPlugin plugin;
    private CountdownManager countdownManager;

    public DayCommand(SMPPlugin plugin, CountdownManager countdownManager) {
        this.plugin = plugin;
        this.countdownManager = countdownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.getWorld().getTime() < 12000 && !player.getWorld().isThundering()) {
                player.sendMessage(ChatColor.RED + "You cannot use this command during the day or when it is not thundering!");
            } else if (countdownManager.isCountingDown()) {
                player.sendMessage(ChatColor.RED + "There is already a request going on!");
            } else if (!player.isSleeping()) {
                player.sendMessage(ChatColor.RED + "You cannot use this command unless you are in a bed!");
            } else {
                List<Player> players = player.getWorld().getPlayers();
                countdownManager.initiateCountdown(player);

                for (Player p : players) {
                    p.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " wants to change the server time to day and remove thunderstorms! " + ChatColor.GOLD + "Do /night in the next 10 seconds to cancel his request.");
                }

                taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DaytimeCountdownTask(countdownManager, this), 20, 20);
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
