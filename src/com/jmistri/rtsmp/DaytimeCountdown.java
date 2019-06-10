package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DaytimeCountdown implements Runnable {

    DayCommand command;

    public DaytimeCountdown(DayCommand command) {
        this.command = command;
    }

    @Override
    public void run() {
        if (command.main.disagree) {
            for (Player p : command.main.initiator.getWorld().getPlayers()) {
                p.sendMessage(ChatColor.GOLD + "Someone has voted to continue through the night.");
            }

            command.main.isCountingDown = false;

            Bukkit.getServer().getScheduler().cancelTask(command.taskID);
        } else if (command.main.countdown <= 0) {
            for (Player p : command.main.initiator.getWorld().getPlayers()) {
                p.sendMessage(ChatColor.GOLD + "Everyone has agreed to skip the night.");
            }

            command.main.initiator.getWorld().setTime(0);
            command.main.isCountingDown = false;

            Bukkit.getServer().getScheduler().cancelTask(command.taskID);
        } else {
            for (Player p : command.main.initiator.getWorld().getPlayers()) {
                p.sendMessage(ChatColor.GOLD + Integer.toString(command.main.countdown) + "...");
            }
            command.main.countdown --;
        }
    }
}
