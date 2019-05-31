package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            int ping = -1;

            try {
                Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            player.sendMessage(ChatColor.RED + "Your Ping: " + ChatColor.WHITE + (ping == -1 ? "Error retrieving ping." : ping + "ms"));

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You cannot use this command unless you are a player!");
            return false;
        }
    }
}
