package com.jmistri.rtsmp;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import javax.xml.stream.events.Namespace;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class XPBottle implements Listener {

    NamespacedKey key;

    public XPBottle(NamespacedKey key) {
        this.key = key;
    }

    @EventHandler
    public void onXPChange(PlayerExpChangeEvent event) {

        // Event information
        Player player = event.getPlayer();
        boolean mainHand;
        boolean refill = false;

        if (player.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)) {
            mainHand = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.GLASS_BOTTLE)) {
            mainHand = false;
        } else if (player.getInventory().getItemInMainHand().getType().equals(Material.POTION)) {
            mainHand = true;
            refill = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.POTION)) {
            mainHand = false;
            refill = true;
        } else {
            return;
        }

        // Either Stack of bottles or partially full
        List<String> lore = getBottle(player.getInventory(), mainHand).getItemMeta().getLore();
        int remainingXP = 0;

        if (!refill) {
            // Glass Bottles (no Lore)

            List<String> replacementLore = new ArrayList<>();
            int XPGained = event.getAmount();

            // Subtract XP and create new bottles until there isnt enough to create a full bottle
            while (XPGained >= 8 && getBottle(player.getInventory(), mainHand).getAmount() > 0) {
                // Remove one item from main hand
                getBottle(player.getInventory(), mainHand).setAmount(getBottle(player.getInventory(), mainHand).getAmount() - 1);

                // Create XP bottles
                ItemStack newXPBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                player.getInventory().addItem(newXPBottle);
                XPGained -= 8;

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
            }

            // Create a partially filled bottle if there is remaining XP
            if (XPGained > 0 && getBottle(player.getInventory(), mainHand).getAmount() > 0) {
                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta pm = (PotionMeta) potion.getItemMeta();

                Glow glow = new Glow(key);
                pm.addEnchant(glow, 1, true);


                pm.setColor(Color.LIME);

                // Create a partially filled bottle
                replacementLore.add(ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + XPGained);
                replacementLore.add(ChatColor.GRAY + "Total Bottle Capacity " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " 8");

                // Write lore data to partially filled bottle
                pm.setLore(replacementLore);
                pm.setDisplayName(ChatColor.RESET + "Partially Filled XP Bottle");
                potion.setItemMeta(pm);

                int bottlesRemaining = getBottle(player.getInventory(), mainHand).getAmount() - 1;

                setBottle(player.getInventory(), mainHand, potion);

                // Add the replacement bottle
                if (bottlesRemaining > 0) {
                    player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, bottlesRemaining));
                }

                XPGained = 0;

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
            }

            remainingXP = XPGained;
        } else {
            // Partially filled bottles (contain lore)
            String[] brokenLore = lore.get(0).split(" ");

            int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);

            if (event.getAmount() + amountStored <= 7) {
                lore.set(0, ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + (amountStored + event.getAmount()));

                // Write lore data to partially filled bottle
                ItemMeta im = getBottle(player.getInventory(), mainHand).getItemMeta();
                im.setLore(lore);
                getBottle(player.getInventory(), mainHand).setItemMeta(im);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
            } else {
                // Add new item
                ItemStack newXPBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                setBottle(player.getInventory(), mainHand, newXPBottle);

                // Store remaining xp to add to player's bar
                remainingXP = event.getAmount() - 8 + amountStored;

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
            }
        }

        player.updateInventory();

        int currentXP = player.getTotalExperience();
        if (currentXP - event.getAmount() > 0) {
            setActualXP(player, currentXP - event.getAmount() + remainingXP);
        } else {
            setActualXP(player, remainingXP);
        }
    }

    private void setActualXP(Player player, int XP) {
        // Reset player's XP
        player.setTotalExperience(XP);
        player.setLevel(0);
        player.setExp(0);

        // For each level, subtract XP and add a level
        while (XP > player.getExpToLevel()) {
            XP -= player.getExpToLevel();
            player.setLevel(player.getLevel() + 1);
        }

        // Add remaining XP
        player.setExp((float) XP / (float) player.getExpToLevel());
    }

    private ItemStack getBottle(PlayerInventory inventory, boolean mainHand) {
        return mainHand ? inventory.getItemInMainHand() : inventory.getItemInOffHand();
    }

    private void setBottle(PlayerInventory inventory, boolean mainHand, ItemStack item) {
        if (mainHand) {
            inventory.setItemInMainHand(item);
        } else {
            inventory.setItemInOffHand(item);
        }
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta().getLore() == null) {
            return;
        }

        if (event.getItem().getItemMeta().getLore().get(0).split(" ")[0].equals(ChatColor.GRAY + "Experience")) {
            event.setCancelled(true);
        }
    }
}
