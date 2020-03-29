package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class AddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Please include a username! Usage: /add <username>");
            return false;
        }

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, "whitelist add " + args[0]);
        sender.sendMessage(ChatColor.GRAY + "Successfully added " + ChatColor.GOLD + args[0] + ChatColor.GRAY + " to the whitelist.");
        return true;
    }
}
