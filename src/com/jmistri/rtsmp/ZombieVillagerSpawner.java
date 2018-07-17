package com.jmistri.rtsmp;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class ZombieVillagerSpawner implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        if (((CreatureSpawner) event.getSpawner().getBlock().getState()).getSpawnedType().equals(EntityType.ZOMBIE) && Math.random() < 0.05) {
            event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ZOMBIE_VILLAGER);
            event.setCancelled(true);
        }
    }

}
