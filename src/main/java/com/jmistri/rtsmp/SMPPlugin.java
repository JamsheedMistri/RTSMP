package com.jmistri.rtsmp;

import com.jmistri.rtsmp.commands.AddCommand;
import com.jmistri.rtsmp.commands.DayCommand;
import com.jmistri.rtsmp.commands.NightCommand;
import com.jmistri.rtsmp.commands.PingCommand;
import com.jmistri.rtsmp.listeners.*;
import com.jmistri.rtsmp.managers.CountdownManager;
import com.jmistri.rtsmp.managers.XPBottleBucketManager;
import com.jmistri.rtsmp.util.Glow;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SMPPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        NamespacedKey key = new NamespacedKey(this, this.getDescription().getName());
        CountdownManager countdownManager = new CountdownManager();
        XPBottleBucketManager xpBottleBucketManager = new XPBottleBucketManager(key);
        xpBottleBucketManager.registerGlow();
        xpBottleBucketManager.createRecipes();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSpawner(), this);
        getServer().getPluginManager().registerEvents(new ToolStatsListener(), this);
        getServer().getPluginManager().registerEvents(new ZombieVillagerSpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new XPBottleListener(key), this);
        getServer().getPluginManager().registerEvents(new XPBucketListener(key), this);
        Bukkit.getPluginManager().registerEvents(new TPSCommandListener(),this);
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("day").setExecutor(new DayCommand(this, countdownManager));
        this.getCommand("night").setExecutor(new NightCommand(countdownManager));
        this.getCommand("add").setExecutor(new AddCommand());
    }

    @Override
    public void onDisable() {

    }
}
