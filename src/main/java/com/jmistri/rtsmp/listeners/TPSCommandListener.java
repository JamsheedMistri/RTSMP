package com.jmistri.rtsmp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class TPSCommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCommand(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/tps")){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Current TPS: " + ChatColor.WHITE + String.format("%.2f", (((CraftServer) Bukkit.getServer()).getServer().recentTps)[0]));
        }
    }
}
