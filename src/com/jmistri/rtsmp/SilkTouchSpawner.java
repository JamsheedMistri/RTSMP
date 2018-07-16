package com.jmistri.rtsmp;

import de.tr7zw.itemnbtapi.NBTItem;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilkTouchSpawner implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.MOB_SPAWNER && event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            event.setExpToDrop(0);

            CreatureSpawner cs = (CreatureSpawner) event.getBlock().getState();
            EntityType type = cs.getSpawnedType();

            NBTItem stack = new NBTItem(new ItemStack(Material.MOB_SPAWNER));
            stack.setString("mob", type.toString());

            String formattedType = type.toString().toLowerCase();
            formattedType = formattedType.replaceAll("_", " ");
            formattedType = WordUtils.capitalize(formattedType);

            ItemStack item = stack.getItem();

            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.RESET + formattedType + " Spawner");
            item.setItemMeta(im);

            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
            NBTItem stack = new NBTItem(event.getItemInHand());

            String type = "pig";

            try {
                type = stack.getString("mob");
            } catch (Exception e) {}

            BlockState blockState = event.getBlockPlaced().getState();
            CreatureSpawner spawner = ((CreatureSpawner) blockState);

            for (EntityType e : EntityType.values()) {
                if (e.toString().equalsIgnoreCase(type)) {
                    spawner.setSpawnedType(e);
                }
            }

            blockState.update();
        }
    }
}
