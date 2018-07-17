package com.jmistri.rtsmp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class ZombieVillagerSpawner implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {

        //Get spawner from event
        CreatureSpawner spawner = event.getSpawner();

        //Check if the spawner is a zombie spawner
        if(spawner.getSpawnedType().equals(EntityType.ZOMBIE) ){

            //Get a random number between 0 and 19 for 5% chance
            int rand = (int)(Math.random()*20);
            if(rand == 0) {
                Entity spawnee = event.getEntity();

                //Get the location of the zombie
                Location loc = spawnee.getLocation();

                //Remove the zombie
                spawnee.remove();

                //Get the world
                World w = spawner.getWorld();

                //Spawn a zombie villager
                w.spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
            }
        }
    }
}
