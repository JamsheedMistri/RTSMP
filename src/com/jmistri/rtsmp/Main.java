package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static HashMap<String, ChatColor> chatColors = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSpawner(), this);
        getServer().getPluginManager().registerEvents(new ToolStats(), this);

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

    @Override
    public void onDisable() {

    }
}
