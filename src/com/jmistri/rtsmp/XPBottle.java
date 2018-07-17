package com.jmistri.rtsmp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class XPBottle implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        ItemStack items = event.getItem();
        Player player = event.getPlayer();

        if(items.getType().equals(Material.GLASS_BOTTLE)
                && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.RIGHT_CLICK_AIR)){

            //If the player has more than 8 xp
            if(player.getTotalExperience() > 8){
                int currentXp = player.getTotalExperience();
                setActualExp(player,currentXp - 8);

                ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);

                player.getInventory().addItem(xpBottle);
                player.getInventory().remove(new ItemStack(Material.GLASS_BOTTLE,1));
                player.updateInventory();

            }

        }

    }

    private void setActualExp(Player p, int Exp){

        //Reset player's xp
        p.setTotalExperience(Exp);
        p.setLevel(0);
        p.setExp(0);

        //For each level, subtract xp and add a level
        for(;Exp > p.getExpToLevel();)
        {
            Exp -= p.getExpToLevel();
            p.setLevel(p.getLevel()+1);
        }

        //Add remaining xp
        float xp = (float)Exp / (float)p.getExpToLevel();
        p.setExp(xp);
    }

}
