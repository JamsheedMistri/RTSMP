package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Change format
        event.setFormat(ChatColor.RED + "%1$s " + ChatColor.GRAY + "> " + ChatColor.WHITE + "%2$s");

        // Add color codes
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));

        // Ping user if their name is typed
        String[] brokenMessage = event.getMessage().split(" ");
        ArrayList<String> completedPings = new ArrayList<>();

        for (int i = 0; i < brokenMessage.length; i ++) {
            Player player = Bukkit.getPlayer(brokenMessage[i]);
            if (player != null) {
                brokenMessage[i] = ChatColor.GOLD + brokenMessage[i] + ChatColor.RESET;

                if (!completedPings.contains(brokenMessage[i])) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                    completedPings.add(brokenMessage[i]);
                }
            }
        }

        event.setMessage(String.join(" ", brokenMessage));
    }
}
