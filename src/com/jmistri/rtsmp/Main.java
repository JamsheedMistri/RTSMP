package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        registerGlow();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSpawner(), this);
        getServer().getPluginManager().registerEvents(new ToolStats(), this);
        getServer().getPluginManager().registerEvents(new ZombieVillagerSpawner(), this);
        getServer().getPluginManager().registerEvents(new XPBottle(), this);
        getServer().getPluginManager().enablePlugin(new Recipies());
    }

    @Override
    public void onDisable() {

    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(100);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
