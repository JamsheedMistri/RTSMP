package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class XPBottle implements Listener {

    @EventHandler
    public void onXPChange(PlayerExpChangeEvent event) {

        // Event information
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.getType().equals(Material.GLASS_BOTTLE)) {
            return;
        }

        // Either Stack of bottles or partially full
        List<String> lore = item.getItemMeta().getLore();
        int remainingXP = 0;

        if (lore == null) {
            // Glass Bottles (no Lore)

            List<String> replacementLore = new ArrayList<>();
            int XPGained = event.getAmount();

            // Subtract XP and create new bottles until there isnt enough to create a full bottle
            while (XPGained >= 8 && player.getInventory().getItemInMainHand().getAmount() > 0) {
                // Remove one item from main hand
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                // Create XP bottles
                ItemStack newXPBottle = new ItemStack(Material.EXP_BOTTLE, 1);
                player.getInventory().addItem(newXPBottle);
                XPGained -= 8;
            }

            // Create a partially filled bottle if there is remaining xp
            if (XPGained > 0 && player.getInventory().getItemInMainHand().getAmount() > 0) {
                ItemStack replacementBottle = new ItemStack(Material.GLASS_BOTTLE, 1);

                // Create a partially filled bottle
                replacementLore.add(ChatColor.GREEN + "Experience " + ChatColor.GRAY + "- " + XPGained);

                // Write lore data to partially filled bottle
                ItemMeta im = replacementBottle.getItemMeta();
                im.setLore(replacementLore);
                replacementBottle.setItemMeta(im);

                int bottlesRemaining = player.getInventory().getItemInMainHand().getAmount() - 1;

                player.getInventory().setItemInMainHand(replacementBottle);

                // Add the replacement bottle
                if (bottlesRemaining > 0) {
                    player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, bottlesRemaining));
                }

                XPGained = 0;
            }

            remainingXP = XPGained;
        } else {
            // Partially filled bottles (contain lore)
            String[] brokenLore = lore.get(0).split(" ");

            int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);

            if (event.getAmount() + amountStored < 8) {
                lore.set(0, ChatColor.GREEN + "Experience " + ChatColor.GRAY + "- " + (amountStored + event.getAmount()));

                // Write lore data to partially filled bottle
                ItemMeta im = item.getItemMeta();
                im.setLore(lore);
                item.setItemMeta(im);

            } else {
                // Remove item
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                // Add new item
                ItemStack newXPBottle = new ItemStack(Material.EXP_BOTTLE, 1);
                player.getInventory().addItem(newXPBottle);

                // Store remaining xp to add to player's bar
                remainingXP = event.getAmount() - 8 + amountStored;

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

}
