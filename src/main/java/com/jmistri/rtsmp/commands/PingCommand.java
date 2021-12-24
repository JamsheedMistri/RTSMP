package com.jmistri.rtsmp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage(ChatColor.RED + "Your Ping: " + ChatColor.WHITE + player.getPing());
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
