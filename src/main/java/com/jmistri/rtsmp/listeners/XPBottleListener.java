package com.jmistri.rtsmp.listeners;

import com.jmistri.rtsmp.util.Glow;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import java.util.ArrayList;
import java.util.List;

public class XPBottleListener implements Listener {

    NamespacedKey key;

    public XPBottleListener(NamespacedKey key) {
        this.key = key;
    }

    @EventHandler
    public void onXPChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        boolean isInMainHand;
        boolean isRefill = false;

        if (player.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)) {
            isInMainHand = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.GLASS_BOTTLE)) {
            isInMainHand = false;
        } else if (player.getInventory().getItemInMainHand().getType().equals(Material.POTION)) {
            isInMainHand = true;
            isRefill = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.POTION)) {
            isInMainHand = false;
            isRefill = true;
        } else {
            return;
        }

        List<String> lore = getItemInHand(player.getInventory(), isInMainHand).getItemMeta().getLore();
        int finalAmount;

        if (!isRefill) {
            List<String> replacementLore = new ArrayList<>();
            int XPGained = event.getAmount();

            // Subtract XP and create new bottles until there isn't enough to create a full bottle
            while (XPGained >= 8 && getItemInHand(player.getInventory(), isInMainHand).getAmount() > 0) {
                // Remove one item from hand
                getItemInHand(player.getInventory(), isInMainHand).setAmount(getItemInHand(player.getInventory(), isInMainHand).getAmount() - 1);

                // Create XP bottles
                ItemStack newXPBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                player.getInventory().addItem(newXPBottle);
                XPGained -= 8;

                // Play sound to signify the finish of a bucket
                playFinishBucketSound(player);
            }

            // Create a partially filled bottle if there is remaining XP
            if (XPGained > 0 && getItemInHand(player.getInventory(), isInMainHand).getAmount() > 0) {
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

                int bottlesRemaining = getItemInHand(player.getInventory(), isInMainHand).getAmount() - 1;

                setItemInHand(player.getInventory(), isInMainHand, potion);

                // Add the replacement bottle
                if (bottlesRemaining > 0) {
                    player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, bottlesRemaining));
                }

                XPGained = 0;

                // Play partial XP pickup sound
                playPartialBucketSound(player);
            }

            finalAmount = XPGained;
        } else {
            // Partially filled bottles (contain lore)
            String[] brokenLore = lore.get(0).split(" ");

            int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);
            if (event.getAmount() + amountStored < 8) {
                lore.set(0, ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + (amountStored + event.getAmount()));

                // Write lore data to partially filled bottle
                ItemMeta im = getItemInHand(player.getInventory(), isInMainHand).getItemMeta();
                im.setLore(lore);
                getItemInHand(player.getInventory(), isInMainHand).setItemMeta(im);

                finalAmount = 0;

                // Play partial XP pickup sound
                playPartialBucketSound(player);
            } else {
                // Add new item
                ItemStack newXPBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                setItemInHand(player.getInventory(), isInMainHand, newXPBottle);

                // Store remaining xp to add to player's bar
                finalAmount = event.getAmount() + amountStored - 8;

                // Play sound to signify the finish of a bucket
                playFinishBucketSound(player);
            }
        }

        player.updateInventory();
        event.setAmount(finalAmount);
    }

    private void playFinishBucketSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.25F);
    }

    private void playPartialBucketSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.25F);
    }

    private ItemStack getItemInHand(PlayerInventory inventory, boolean mainHand) {
        return mainHand ? inventory.getItemInMainHand() : inventory.getItemInOffHand();
    }

    private void setItemInHand(PlayerInventory inventory, boolean mainHand, ItemStack item) {
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
