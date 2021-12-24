package com.jmistri.rtsmp.managers;

import com.jmistri.rtsmp.util.Glow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XPBottleBucketManager {
    NamespacedKey key;

    public XPBottleBucketManager(NamespacedKey key) {
        this.key = key;
    }

    public void createRecipes() {
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
        catch (IllegalArgumentException e) {}
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
