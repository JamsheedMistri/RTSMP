package com.jmistri.rtsmp;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Vector;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class XPBottle implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        ItemStack items = event.getItem();
        Player player = Bukkit.getPlayer("kylemarino22");

        if(items.getType().equals(Material.GLASS_BOTTLE)
                && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(player.getTotalExperience() > 7){
                int currentXp = player.getTotalExperience();
                player.setTotalExperience(currentXp - 7);
//                player.setLevel(15);
                player.sendMessage("Level: " +  player.getLevel());
                player.sendMessage("Totalxp: " + getTotalExperience(player));




                ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);

                player.getInventory().addItem(xpBottle);
                player.getInventory().remove(new ItemStack(Material.EXP_BOTTLE,1));
                player.updateInventory();

            }
//            player.sendMessage("xP: " + items);

        }
        player.sendMessage("Item: " + items);
        player.sendMessage("Item: " + event.getAction());


    }

    private float getTotalExperience(Player Who) {
        int level = Who.getLevel();
        float progress = Who.getExp();
        float totalExp = 0;

        if(level < 15){
            totalExp += level * level + 6*level;
        }
        else if(level < 30){
            totalExp += 2.5 * level * level - 40.5 * level + 360;
        }
        else {
            totalExp += 4.5*level*level - 162.5 * level + 2220;
        }
        progress = (level >= 15 ) ? ((level >= 30) ? progress * (112 + (level - 30) * 9) : progress * (37 + (level - 15) * 5)) : progress * (7 + level * 2);

        return totalExp + progress;
    }

    private void setTotalExperience(Player who, float Exp){

        if(Exp < 37){
//            int level =
        }


    }
}
