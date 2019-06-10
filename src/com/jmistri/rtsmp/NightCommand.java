package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NightCommand implements CommandExecutor {

    Main main;

    public NightCommand(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!main.isCountingDown) {
                player.sendMessage(ChatColor.RED + "There is currently no request! You can request the world to change to daytime by doing /day.");
            } else {
                main.disagree = true;
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
