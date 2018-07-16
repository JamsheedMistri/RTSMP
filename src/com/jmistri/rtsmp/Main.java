package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSpawner(), this);
        getServer().getPluginManager().registerEvents(new ToolStats(), this);
    }

    @Override
    public void onDisable() {

    }
}
