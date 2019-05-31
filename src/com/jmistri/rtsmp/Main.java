package com.jmistri.rtsmp;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    NamespacedKey key;

    @Override
    public void onEnable() {
        key = new NamespacedKey(this, this.getDescription().getName());

        registerGlow();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSpawner(), this);
        getServer().getPluginManager().registerEvents(new ToolStats(), this);
        getServer().getPluginManager().registerEvents(new ZombieVillagerSpawner(), this);
        getServer().getPluginManager().registerEvents(new XPBottle(key), this);
        getServer().getPluginManager().registerEvents(new XPBucket(key), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(),this);
        this.getCommand("ping").setExecutor(new PingCommand());

        ItemStack bucket = new ItemStack(Material.BUCKET);
        ItemMeta im = bucket.getItemMeta();
        List<String> lore = new ArrayList<>();

        Glow glow = new Glow(key);
        im.addEnchant(glow, 1, true);

        lore.add(ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " 0");
        lore.add(ChatColor.GRAY + "Total Bottle Capacity " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " 160");

        // Write lore data to partially filled bottle
        im.setLore(lore);
        im.setDisplayName(ChatColor.RESET + "Empty XP Bucket");
        bucket.setItemMeta(im);

        ShapedRecipe recipe = new ShapedRecipe(key, bucket);

        recipe.shape("   ", "I I", " I ");
        recipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.addRecipe(recipe);
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
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
