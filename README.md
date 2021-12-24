# RTSMP Plugin
## Features
- **`/add` command** - add anyone to the whitelist
- **`/day` command** - if you are in a bed, but others aren't, you can execute `/day` to start a 10-second counter. If no one executes `/night` by the end of the countdown, the time will automatically turn to day
- **`/ping` command** - view your ping to the server
- **`/tps` command** - to see if the server is lagging
- **Name ping** - if you type someone's name and they are online, it will turn their name orange in the chat and send them a sound to notify them
- **Silk touch spawner** - if you mine a spawner with a silk touch pickaxe, it will drop an item
- **XP bottles/buckets** - hold onto XP to redeem later, good for AFK grinders
- **Zombie villager spawners** - zombie spawners have a small chance of spawning zombie villagers

## Dependencies/Resources
- Spigot
- [Item NBT API](https://www.spigotmc.org/resources/nbt-api.7939/) - must be installed on server in plugin directory - NOT shaded into jar

## NMS
The TPS command requires NMS access, and since we are using Maven for dependency management, you must run BuildTools on your local machine, which will allow you to import the Spigot API and the Spigot library as shown in the pom.xml. Running BuildTools on your local machine will automatically add Spigot's full library to your IDE's library, allowing you to use NMS.
