package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Map;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Change format
        event.setFormat(ChatColor.RED + "%1$s " + ChatColor.GRAY + "> " + ChatColor.WHITE + "%2$s");

        // Add color codes
        for (Map.Entry<String, ChatColor> entry : Main.chatColors.entrySet()) {
            event.setMessage(event.getMessage().replaceAll(entry.getKey(), entry.getValue().toString()));
        }

        // Ping user if their name is typed
        ArrayList<String> checkedPlayers = new ArrayList<>();

        OuterLoop:
        for (String word : event.getMessage().split(" ")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (checkedPlayers.contains(word)) {
                    continue;
                }

                if (word.equals(player.getDisplayName())) {
                    event.setMessage(event.getMessage().replaceAll(word, ChatColor.GOLD + word + ChatColor.RESET));
                    player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                    checkedPlayers.add(word);

                    continue OuterLoop;
                }
            }
        }
    }
}
