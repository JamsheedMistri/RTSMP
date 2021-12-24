package com.jmistri.rtsmp.commands;

import com.jmistri.rtsmp.managers.CountdownManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand implements CommandExecutor {

    CountdownManager countdownManager;

    public NightCommand(CountdownManager countdownManager) {
        this.countdownManager = countdownManager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!countdownManager.isCountingDown()) {
                player.sendMessage(ChatColor.RED + "There is currently no request! You can request the world to change to daytime by doing /day.");
            } else {
                countdownManager.playerDisagree();
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
