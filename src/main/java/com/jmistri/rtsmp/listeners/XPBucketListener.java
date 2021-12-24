package com.jmistri.rtsmp.listeners;

import com.jmistri.rtsmp.util.Glow;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class XPBucketListener implements Listener {
    NamespacedKey key;

    public XPBucketListener(NamespacedKey key) {
        this.key = key;
    }

    @EventHandler
    public void onXPChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        boolean isInMainHand;
        boolean isRefill = false;

        if (inv.getItemInMainHand().getType().equals(Material.BUCKET) && isBucket(inv.getItemInMainHand())) {
            isInMainHand = true;
        } else if (inv.getItemInOffHand().getType().equals(Material.BUCKET) && isBucket(inv.getItemInOffHand())) {
            isInMainHand = false;
        } else if (inv.getItemInMainHand().getType().equals(Material.WATER_BUCKET) && isBucket(inv.getItemInMainHand())) {
            isInMainHand = true;
            isRefill = true;
        } else if (inv.getItemInOffHand().getType().equals(Material.WATER_BUCKET) && isBucket(inv.getItemInOffHand())) {
            isInMainHand = false;
            isRefill = true;
        } else {
            return;
        }

        List<String> lore = getItemInHand(player.getInventory(), isInMainHand).getItemMeta().getLore();
        int finalAmount;

        // Their bucket is empty
        if (!isRefill) {
            List<String> replacementLore = new ArrayList<>();

            // Picked up enough XP for bottle to fill
            if (event.getAmount() >= 160) {
                // Award 20 XP bottles
                setItemInHand(player.getInventory(), isInMainHand, new ItemStack(Material.EXPERIENCE_BOTTLE, 20));
                // Give remaining XP
                finalAmount = event.getAmount() - 160;
                // Play sound to signify the finish of a bucket
                playFinishBucketSound(player);
            }
            // Create a partially filled bottle if there is remaining XP
            else {
                ItemStack bucket = new ItemStack(Material.WATER_BUCKET);
                ItemMeta im = bucket.getItemMeta();

                Glow glow = new Glow(key);
                im.addEnchant(glow, 1, true);

                // Create a partially filled bucket
                replacementLore.add(ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + event.getAmount());
                replacementLore.add(ChatColor.GRAY + "Total Bottle Capacity " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " 160");

                NBTItem stack = new NBTItem(bucket);
                stack.setString(Integer.toString((int) (Math.random() * 9999999)), Integer.toString((int) (Math.random() * 9999999)));
                bucket = stack.getItem();

                // Write lore data to partially filled bucket
                im.setLore(replacementLore);
                im.setDisplayName(ChatColor.RESET + "Partially Filled XP Bucket");
                bucket.setItemMeta(im);

                // Update their bucket
                setItemInHand(player.getInventory(), isInMainHand, bucket);
                // Don't award them any XP
                finalAmount = 0;
                // Play partial XP pickup sound
                playPartialBucketSound(player);
            }
            // Partially filled buckets (contain lore)
        } else {
            String[] brokenLore = lore.get(0).split(" ");
            // Amount stored in the bucket already
            int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);

            // If the amount after this is still not enough to fill the bucket
            if (event.getAmount() + amountStored < 160) {
                lore.set(0, ChatColor.GRAY + "Experience " + ChatColor.DARK_GRAY + "-" + ChatColor.GREEN + " " + (amountStored + event.getAmount()));

                // Write lore data to partially filled bottle
                ItemMeta im = getItemInHand(player.getInventory(), isInMainHand).getItemMeta();
                im.setLore(lore);
                getItemInHand(player.getInventory(), isInMainHand).setItemMeta(im);

                // Don't award them any XP
                finalAmount = 0;
                // Play partial XP pickup sound
                playPartialBucketSound(player);
            } else {
                // Add new item
                ItemStack newXPBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 20);
                setItemInHand(player.getInventory(), isInMainHand, newXPBottle);
                // Store remaining xp to add to player's bar
                finalAmount = event.getAmount() + amountStored - 160;
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
