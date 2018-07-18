package com.jmistri.rtsmp;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class XPBucket implements Listener {

    @EventHandler
    public void onXPChange(PlayerExpChangeEvent event) {

        // Event information
        Player player = event.getPlayer();
        boolean mainHand;
        boolean refill = false;

        if (player.getInventory().getItemInMainHand().getType().equals(Material.BUCKET) && isBucket(player.getInventory().getItemInMainHand())) {
            mainHand = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.BUCKET) && isBucket(player.getInventory().getItemInOffHand())) {
            mainHand = false;
        } else if (player.getInventory().getItemInMainHand().getType().equals(Material.WATER_BUCKET) && isBucket(player.getInventory().getItemInMainHand())) {
            mainHand = true;
            refill = true;
        } else if (player.getInventory().getItemInOffHand().getType().equals(Material.WATER_BUCKET) && isBucket(player.getInventory().getItemInOffHand())) {
            mainHand = false;
            refill = true;
        } else {
            return;
        }

        // Either Stack of bottles or partially full
        List<String> lore = getBucket(player.getInventory(), mainHand).getItemMeta().getLore();
        int remainingXP = 0;

        if (!refill) {
            // Glass Bottles (no Lore)

            List<String> replacementLore = new ArrayList<>();
            int XPGained = event.getAmount();

            // Subtract XP and create new bottles until there isnt enough to create a full bottle
            if (XPGained >= 160) {
                // Remove one item from main hand
                setBucket(player.getInventory(), mainHand, new ItemStack(Material.EXP_BOTTLE, 20));

                XPGained -= 160;

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.25F);
            }
            // Create a partially filled bottle if there is remaining XP
            else {
                ItemStack bucket = new ItemStack(Material.WATER_BUCKET);
                ItemMeta im = bucket.getItemMeta();

                Glow glow = new Glow(100);
                im.addEnchant(glow, 1, true);

                // Create a partially filled bottle
                replacementLore.add(ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + XPGained);
                replacementLore.add(ChatColor.GRAY + "Total Bottle Capacity " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " 160");

                NBTItem stack = new NBTItem(bucket);
                stack.setString(Integer.toString((int) (Math.random() * 9999999)), Integer.toString((int) (Math.random() * 9999999)));
                bucket = stack.getItem();

                // Write lore data to partially filled bottle
                im.setLore(replacementLore);
                im.setDisplayName(ChatColor.RESET + "Partially Filled XP Bucket");
                bucket.setItemMeta(im);

                setBucket(player.getInventory(), mainHand, bucket);

                XPGained = 0;

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.25F);
            }

            remainingXP = XPGained;
        } else {
            // Partially filled bottles (contain lore)
            String[] brokenLore = lore.get(0).split(" ");

            int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);

            if (event.getAmount() + amountStored <= 159) {
                lore.set(0, ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + (amountStored + event.getAmount()));

                // Write lore data to partially filled bottle
                ItemMeta im = getBucket(player.getInventory(), mainHand).getItemMeta();
                im.setLore(lore);
                getBucket(player.getInventory(), mainHand).setItemMeta(im);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.25F);
            } else {
                // Add new item
                ItemStack newXPBottle = new ItemStack(Material.EXP_BOTTLE, 20);
                setBucket(player.getInventory(), mainHand, newXPBottle);

                // Store remaining xp to add to player's bar
                remainingXP = event.getAmount() - 160 + amountStored;

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.25F);
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

    private ItemStack getBucket(PlayerInventory inventory, boolean mainHand) {
        return mainHand ? inventory.getItemInMainHand() : inventory.getItemInOffHand();
    }

    private void setBucket(PlayerInventory inventory, boolean mainHand, ItemStack item) {
        if (mainHand) {
            inventory.setItemInMainHand(item);
        } else {
            inventory.setItemInOffHand(item);
        }
    }

    @EventHandler
    public void onPlace(PlayerBucketEmptyEvent event) {
        if (!event.getBucket().equals(Material.WATER_BUCKET)) {
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WATER_BUCKET)) {
            if (isBucket(event.getPlayer().getInventory().getItemInMainHand())) {
                event.setCancelled(true);
                event.getPlayer().getInventory().setItemInMainHand(event.getPlayer().getInventory().getItemInMainHand());
            }
        } else {
            if (isBucket(event.getPlayer().getInventory().getItemInOffHand())) {
                event.setCancelled(true);
                event.getPlayer().getInventory().setItemInOffHand(event.getPlayer().getInventory().getItemInOffHand());
            }
        }
    }

    private boolean isBucket(ItemStack bucket) {
        if (bucket.getItemMeta() == null) return false;
        if (bucket.getItemMeta().getLore() == null) return false;
        return bucket.getItemMeta().getLore().get(0).split(" ")[0].equals(ChatColor.GRAY + "Experience");
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (isBucket(event.getCurrentItem())) {
            NBTItem stack = new NBTItem(event.getCurrentItem());
            stack.setString(Integer.toString((int) (Math.random() * 9999999)), Integer.toString((int) (Math.random() * 9999999)));
            event.setCurrentItem(stack.getItem());
        }
    }
}
