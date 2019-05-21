package com.jmistri.rtsmp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToolStats implements Listener {
    static ArrayList<Material> pickaxes = new ArrayList<>();
    static {
        pickaxes.add(Material.WOODEN_PICKAXE);
        pickaxes.add(Material.STONE_PICKAXE);
        pickaxes.add(Material.GOLDEN_PICKAXE);
        pickaxes.add(Material.IRON_PICKAXE);
        pickaxes.add(Material.DIAMOND_PICKAXE);
    }

    static ArrayList<Material> weapons = new ArrayList<>();
    static {
        weapons.add(Material.WOODEN_SWORD);
        weapons.add(Material.STONE_SWORD);
        weapons.add(Material.GOLDEN_SWORD);
        weapons.add(Material.IRON_SWORD);
        weapons.add(Material.DIAMOND_SWORD);
        weapons.add(Material.BOW);
        weapons.add(Material.CROSSBOW);
    }

    static HashMap<Material, String> pickaxe_blocks = new HashMap<>();
    static
    {
        pickaxe_blocks.put(Material.COAL_ORE, ChatColor.DARK_GRAY + "Coal Ore");
        pickaxe_blocks.put(Material.IRON_ORE, ChatColor.GRAY + "Iron Ore");
        pickaxe_blocks.put(Material.REDSTONE_ORE, ChatColor.DARK_RED + "Redstone Ore");
        pickaxe_blocks.put(Material.GOLD_ORE, ChatColor.GOLD + "Gold Ore");
        pickaxe_blocks.put(Material.DIAMOND_ORE, ChatColor.AQUA + "Diamond Ore");
        pickaxe_blocks.put(Material.EMERALD_ORE, ChatColor.GREEN + "Emerald Ore");
        pickaxe_blocks.put(Material.NETHER_QUARTZ_ORE, ChatColor.RED + "Quartz Ore");
    }

    static HashMap<EntityType, String> weapon_entities = new HashMap<>();
    static
    {
        weapon_entities.put(EntityType.ELDER_GUARDIAN, ChatColor.DARK_AQUA + "Elder Guardian");
        weapon_entities.put(EntityType.WITHER_SKELETON, ChatColor.DARK_GRAY + "Wither Skeleton");
        weapon_entities.put(EntityType.HUSK, ChatColor.GRAY + "Husk");
        weapon_entities.put(EntityType.ZOMBIE_VILLAGER, ChatColor.DARK_GREEN + "Zombie");
        weapon_entities.put(EntityType.SKELETON_HORSE, ChatColor.GOLD + "Horse");
        weapon_entities.put(EntityType.ZOMBIE_HORSE, ChatColor.GOLD + "Horse");
        weapon_entities.put(EntityType.DONKEY, ChatColor.GOLD + "Donkey");
        weapon_entities.put(EntityType.MULE, ChatColor.GOLD + "Mule");
        weapon_entities.put(EntityType.EVOKER, ChatColor.DARK_GRAY + "Evoker");
        weapon_entities.put(EntityType.VEX, ChatColor.DARK_GRAY + "Vex");
        weapon_entities.put(EntityType.VINDICATOR, ChatColor.DARK_GRAY + "Vindicator");
        weapon_entities.put(EntityType.ILLUSIONER, ChatColor.BLUE + "Illusioner");
        weapon_entities.put(EntityType.CREEPER, ChatColor.GREEN + "Creeper");
        weapon_entities.put(EntityType.SKELETON, ChatColor.WHITE + "Skeleton");
        weapon_entities.put(EntityType.SPIDER, ChatColor.DARK_RED + "Spider");
        weapon_entities.put(EntityType.GIANT, ChatColor.DARK_GREEN + "Giant");
        weapon_entities.put(EntityType.ZOMBIE, ChatColor.DARK_GREEN + "Zombie");
        weapon_entities.put(EntityType.SLIME, ChatColor.GREEN + "Slime");
        weapon_entities.put(EntityType.GHAST, ChatColor.WHITE + "Ghast");
        weapon_entities.put(EntityType.PIG_ZOMBIE, ChatColor.LIGHT_PURPLE + "Zombie Pigman");
        weapon_entities.put(EntityType.ENDERMAN, ChatColor.DARK_PURPLE + "Enderman");
        weapon_entities.put(EntityType.CAVE_SPIDER, ChatColor.DARK_RED + "Spider");
        weapon_entities.put(EntityType.SILVERFISH, ChatColor.GRAY + "Silverfish");
        weapon_entities.put(EntityType.BLAZE, ChatColor.GOLD + "Blaze");
        weapon_entities.put(EntityType.MAGMA_CUBE, ChatColor.RED + "Magma Cube");
        weapon_entities.put(EntityType.ENDER_DRAGON, ChatColor.DARK_PURPLE + "Ender Dragon");
        weapon_entities.put(EntityType.WITHER, ChatColor.DARK_GRAY + "Wither");
        weapon_entities.put(EntityType.BAT, ChatColor.DARK_GRAY + "Bat");
        weapon_entities.put(EntityType.WITCH, ChatColor.DARK_PURPLE + "Witch");
        weapon_entities.put(EntityType.ENDERMITE, ChatColor.DARK_PURPLE + "Endermite");
        weapon_entities.put(EntityType.GUARDIAN, ChatColor.DARK_AQUA + "Guardian");
        weapon_entities.put(EntityType.SHULKER, ChatColor.AQUA + "Shulker");
        weapon_entities.put(EntityType.PIG, ChatColor.LIGHT_PURPLE + "Pig");
        weapon_entities.put(EntityType.SHEEP, ChatColor.WHITE + "Sheep");
        weapon_entities.put(EntityType.COW, ChatColor.DARK_GRAY + "Cow");
        weapon_entities.put(EntityType.CHICKEN, ChatColor.WHITE + "Chicken");
        weapon_entities.put(EntityType.SQUID, ChatColor.DARK_GRAY + "Squid");
        weapon_entities.put(EntityType.WOLF, ChatColor.WHITE + "Wolf");
        weapon_entities.put(EntityType.MUSHROOM_COW, ChatColor.DARK_RED + "Mooshroom");
        weapon_entities.put(EntityType.SNOWMAN, ChatColor.WHITE + "Snowman");
        weapon_entities.put(EntityType.OCELOT, ChatColor.YELLOW + "Ocelot");
        weapon_entities.put(EntityType.IRON_GOLEM, ChatColor.GRAY + "Iron Golem");
        weapon_entities.put(EntityType.HORSE, ChatColor.GOLD + "Horse");
        weapon_entities.put(EntityType.RABBIT, ChatColor.WHITE + "Rabbit");
        weapon_entities.put(EntityType.POLAR_BEAR, ChatColor.WHITE + "Polar Bear");
        weapon_entities.put(EntityType.LLAMA, ChatColor.YELLOW + "Llama");
        weapon_entities.put(EntityType.PARROT, ChatColor.DARK_RED + "Parrot");
        weapon_entities.put(EntityType.VILLAGER, ChatColor.GOLD + "Villager");
        weapon_entities.put(EntityType.PLAYER, ChatColor.RED + "Player");
        weapon_entities.put(EntityType.TURTLE, ChatColor.DARK_GREEN + "Turtle");
        weapon_entities.put(EntityType.PHANTOM, ChatColor.DARK_BLUE + "Phantom");
        weapon_entities.put(EntityType.COD, ChatColor.GOLD + "Cod");
        weapon_entities.put(EntityType.SALMON, ChatColor.LIGHT_PURPLE + "Salmon");
        weapon_entities.put(EntityType.PUFFERFISH, ChatColor.YELLOW + "Puffer Fish");
        weapon_entities.put(EntityType.TROPICAL_FISH, ChatColor.RED + "Tropical Fish");
        weapon_entities.put(EntityType.DROWNED, ChatColor.DARK_AQUA + "Drowned");
        weapon_entities.put(EntityType.DOLPHIN, ChatColor.BLUE + "Dolphin");
        weapon_entities.put(EntityType.CAT, ChatColor.GOLD + "Cat");
        weapon_entities.put(EntityType.PANDA, ChatColor.WHITE + "Panda");
        weapon_entities.put(EntityType.PILLAGER, ChatColor.GRAY + "Pillager");
        weapon_entities.put(EntityType.RAVAGER, ChatColor.DARK_GRAY + "Ravager");
        weapon_entities.put(EntityType.TRADER_LLAMA, ChatColor.WHITE + "Trader Llama");
        weapon_entities.put(EntityType.WANDERING_TRADER, ChatColor.BLUE + "Wandering Trader");
        weapon_entities.put(EntityType.FOX, ChatColor.GOLD + "Fox");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if (!item.getType().isItem()) {
            return;
        }

        if (pickaxes.contains(item.getType()) && pickaxe_blocks.containsKey(event.getBlock().getType())) {
            List<String> lore = item.getItemMeta().getLore();

            boolean foundMatch = false;
            if (lore == null) {
                lore = new ArrayList<>();
            } else {
                for (int i = 0; i < lore.size(); i++) {
                    String loreText = lore.get(i);
                    String[] brokenLore = loreText.split(" ");
                    int count = Integer.parseInt(brokenLore[brokenLore.length - 1]);

                    String match = "";
                    for (int j = 0; j < brokenLore.length - 2; j++) {
                        match += brokenLore[j];
                        if (j != brokenLore.length - 3) match += " ";
                    }

                    if (pickaxe_blocks.get(event.getBlock().getType()).equals(match)) {
                        lore.set(i, match + " " + ChatColor.GRAY + "-" + ChatColor.WHITE + " " + (count + 1));
                        foundMatch = true;
                    }
                }
            }

            if (!foundMatch) {
                lore.add(pickaxe_blocks.get(event.getBlock().getType()) + " " + ChatColor.GRAY + "-" + ChatColor.WHITE + " 1");
            }

            ItemMeta im = item.getItemMeta();
            im.setLore(lore);
            item.setItemMeta(im);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        ItemStack item;
        try {
            item = event.getEntity().getKiller().getInventory().getItemInMainHand();
        } catch (Exception e) {
            return;
        }

        if (!item.getType().isItem()) {
            return;
        }

        if (weapons.contains(item.getType())) {
            List<String> lore = item.getItemMeta().getLore();

            boolean foundMatch = false;

            if (lore == null) {
                lore = new ArrayList<>();
            } else {
                for (int i = 0; i < lore.size(); i++) {
                    String loreText = lore.get(i);
                    String[] brokenLore = loreText.split(" ");
                    int count = Integer.parseInt(brokenLore[brokenLore.length - 1]);

                    String match = "";
                    for (int j = 0; j < brokenLore.length - 2; j++) {
                        match += brokenLore[j];
                        if (j != brokenLore.length - 3) match += " ";
                    }

                    if (weapon_entities.get(event.getEntityType()).equals(match)) {
                        lore.set(i, match + " " + ChatColor.GRAY + "-" + ChatColor.WHITE + " " + (count + 1));
                        foundMatch = true;
                    }
                }
            }

            if (!foundMatch) {
                lore.add(weapon_entities.get(event.getEntityType()) + " " + ChatColor.GRAY + "-" + ChatColor.WHITE + " 1");
            }

            ItemMeta im = item.getItemMeta();
            im.setLore(lore);
            item.setItemMeta(im);
        }
    }
}