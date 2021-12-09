import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class OreStatistics extends JavaPlugin{


    @Override
    public void onEnable() {

        ChunkDeletion chunkD = new ChunkDeletion(this);

        getServer().getPluginManager().registerEvents(chunkD, this);

        this.getCommand("coal").setExecutor(new OreCommands(chunkD));
        this.getCommand("copper").setExecutor(new OreCommands(chunkD));
        this.getCommand("lapis").setExecutor(new OreCommands(chunkD));
        this.getCommand("iron").setExecutor(new OreCommands(chunkD));
        this.getCommand("gold").setExecutor(new OreCommands(chunkD));
        this.getCommand("redstone").setExecutor(new OreCommands(chunkD));
        this.getCommand("diamond").setExecutor(new OreCommands(chunkD));
        this.getCommand("emerald").setExecutor(new OreCommands(chunkD));
        this.getCommand("save").setExecutor(new OreCommands(chunkD));


        // this makes player keep teleporting to get new chunks to load

        //final array so it dont get the same world over and over which is kinda slow
        final World[] world = new World[1];
        final List<Player>[] playerList = new List[]{null};

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                if(world[0] == null)
                    world[0] = getServer().getWorld("world");

                if(playerList[0] == null)
                    playerList[0] = world[0].getPlayers();

                if(!playerList[0].isEmpty()) {
                    Location current = playerList[0].get(0).getLocation();
                    Location newLocation = new Location(current.getWorld(), current.getX() + 15, current.getY(), current.getZ());
                    playerList[0].get(0).teleport(newLocation);
                }

                // just to check if its working
                Bukkit.broadcastMessage("loop");

            }

        };

        runnable.runTaskTimer(this, 0, 2400);

    }

    @Override
    public void onDisable(){
        getLogger().info("Disabled");
        HandlerList.unregisterAll(this);
    }


}
