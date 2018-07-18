package com.jmistri.rtsmp;

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
    public void onXPChange (PlayerExpChangeEvent event) {

        //Event information
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.GLASS_BOTTLE)) {
            //Either Stack of bottles or partially full

            List<String> lore = item.getItemMeta().getLore();
            int remainingXP = 0;

            if (lore == null) {
                //Glass Bottles (no Lore)

                List<String> replacementLore = new ArrayList<>();
                int xpGained = event.getAmount();

                //Subtract xp and create new bottles until there isnt enough to create a full bottle
                while (xpGained > 7 && player.getInventory().getItemInMainHand().getAmount() > 0) {

                    //Remove one item from main hand
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount()-1);

                    //Create xp bottles
                    ItemStack newXPBottle = new ItemStack(Material.EXP_BOTTLE, 1);
                    player.getInventory().addItem(newXPBottle);
                    xpGained -= 8;

                }

                //Create a partially filled bottle if there is remaining xp
                if (xpGained > 0 && player.getInventory().getItemInMainHand().getAmount() > 0) {

                    ItemStack replacementBottle = new ItemStack(Material.GLASS_BOTTLE, 1);

                    //Create a partially filled bottle
                    replacementLore.add("EXP: " + xpGained);

                    //Write Lore data to partially filled bottle
                    ItemMeta im = replacementBottle.getItemMeta();
                    im.setLore(replacementLore);
                    replacementBottle.setItemMeta(im);

                    //Add the replacement bottle
                    player.getInventory().addItem(replacementBottle);

                    //Remove one item from main hand
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount()-1);
                    xpGained = 0;
                }

                remainingXP = xpGained;

            } else {

                //Partially filled bottles (contain lore)
                String[] brokenLore = lore.get(0).split(" ");

                int amountStored = Integer.parseInt(brokenLore[brokenLore.length - 1]);

                if (event.getAmount()+amountStored < 8) {
                    lore.set(0, "EXP: " + (amountStored + event.getAmount()));

                    //Write Lore data to partially filled bottle
                    ItemMeta im = item.getItemMeta();
                    im.setLore(lore);
                    item.setItemMeta(im);

                } else {
                    //remove item
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);

                    //Add new item
                    ItemStack newXPBottle = new ItemStack(Material.EXP_BOTTLE, 1);
                    player.getInventory().addItem(newXPBottle);

                    //Store remaining xp to add to player's bar
                    remainingXP = event.getAmount() - 8 + amountStored;

                }
            }

            player.updateInventory();

            int currentXp = player.getTotalExperience();
            if (currentXp - event.getAmount() > 0) {
                setActualExp(player, currentXp - event.getAmount() + remainingXP);
            } else {
                setActualExp(player, remainingXP);
            }
        }

    }

    private void setActualExp (Player p, int Exp) {

        //Reset player's xp
        p.setTotalExperience(Exp);
        p.setLevel(0);
        p.setExp(0);

        //For each level, subtract xp and add a level
        while (Exp > p.getExpToLevel()) {
            Exp -= p.getExpToLevel();
            p.setLevel(p.getLevel()+1);
        }

        //Add remaining xp
        float xp = (float) Exp / (float) p.getExpToLevel();
        p.setExp(xp);
    }

}
