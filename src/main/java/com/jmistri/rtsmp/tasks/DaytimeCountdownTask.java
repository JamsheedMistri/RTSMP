package com.jmistri.rtsmp.tasks;

import com.jmistri.rtsmp.commands.DayCommand;
import com.jmistri.rtsmp.managers.CountdownManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DaytimeCountdownTask implements Runnable {
    CountdownManager countdownManager;
    DayCommand command;

    public DaytimeCountdownTask(CountdownManager countdownManager, DayCommand command) {
        this.countdownManager = countdownManager;
        this.command = command;
    }

    @Override
    public void run() {
        Player initiator = countdownManager.getInitiator();

        if (countdownManager.playerHasDisagreed()) {
            for (Player p : initiator.getWorld().getPlayers()) {
                p.sendMessage(ChatColor.RED + "Someone has voted to continue through the night or thunderstorm.");
            }

            countdownManager.cancelCountdown();

            Bukkit.getServer().getScheduler().cancelTask(command.taskID);
        } else if (countdownManager.getCountdownValue() <= 0) {
            for (Player p : initiator.getWorld().getPlayers()) {
                p.sendMessage(ChatColor.GOLD + "Everyone has agreed to skip the night or thunderstorm.");
            }

            initiator.getWorld().setTime(0);
            initiator.getWorld().setThundering(false);
            countdownManager.cancelCountdown();

            Bukkit.getServer().getScheduler().cancelTask(command.taskID);
        } else {
            for (Player p : initiator.getWorld().getPlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(
                                (ChatColor.GRAY + "Skipping the night in " + ChatColor.GOLD + countdownManager.getCountdownValue() + ChatColor.GRAY + " seconds... "
                                        + ChatColor.GRAY + "(do " + ChatColor.GOLD + "/night" + ChatColor.GRAY + " to cancel)")
                        ));
            }

            countdownManager.countDownByOne();
        }
    }
}
