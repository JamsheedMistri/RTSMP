package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DayCommand implements CommandExecutor {

    Main main;
    int taskID;

    public DayCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getWorld().getTime() < 12000) {
                player.sendMessage(ChatColor.RED + "You cannot use this command during the day!");
            } else if (main.isCountingDown) {
                player.sendMessage(ChatColor.RED + "There is already a request going on!");
            } else {
                List<Player> players = player.getWorld().getPlayers();
                main.countdown = 10;
                main.initiator = player;
                main.isCountingDown = true;
                main.disagree = false;

                for (Player p : players) {
                    p.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " wants to change the server time to day! " + ChatColor.GOLD + "Do /night in the next 10 to cancel his request.");
                }

                taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RTSMP"), new DaytimeCountdown(this), 20L, 20);
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
