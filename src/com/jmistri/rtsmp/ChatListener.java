package com.jmistri.rtsmp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatListener implements Listener {

    static HashMap<String, ChatColor> chatColors = new HashMap<>();

    static {
        chatColors.put("&a", ChatColor.GREEN);
        chatColors.put("&b", ChatColor.AQUA);
        chatColors.put("&c", ChatColor.RED);
        chatColors.put("&d", ChatColor.LIGHT_PURPLE);
        chatColors.put("&e", ChatColor.YELLOW);
        chatColors.put("&f", ChatColor.WHITE);
        chatColors.put("&l", ChatColor.BOLD);
        chatColors.put("&n", ChatColor.UNDERLINE);
        chatColors.put("&o", ChatColor.ITALIC);
        chatColors.put("&k", ChatColor.MAGIC);
        chatColors.put("&m", ChatColor.STRIKETHROUGH);
        chatColors.put("&r", ChatColor.RESET);
        chatColors.put("&0", ChatColor.BLACK);
        chatColors.put("&1", ChatColor.DARK_BLUE);
        chatColors.put("&2", ChatColor.DARK_GREEN);
        chatColors.put("&3", ChatColor.DARK_AQUA);
        chatColors.put("&4", ChatColor.DARK_RED);
        chatColors.put("&5", ChatColor.DARK_PURPLE);
        chatColors.put("&6", ChatColor.GOLD);
        chatColors.put("&7", ChatColor.GRAY);
        chatColors.put("&8", ChatColor.DARK_GRAY);
        chatColors.put("&9", ChatColor.BLUE);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Change format
        event.setFormat(ChatColor.RED + "%1$s " + ChatColor.GRAY + "> " + ChatColor.WHITE + "%2$s");

        // Add color codes
        for (Map.Entry<String, ChatColor> entry : chatColors.entrySet()) {
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
