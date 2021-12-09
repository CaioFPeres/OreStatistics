import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import static org.bukkit.Material.*;

import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkDeletion implements Listener {

    private Plugin plugin;
    private BiomeData[] biomeData;


    public ChunkDeletion(Plugin plugin){

        this.plugin = plugin;

        biomeData = new BiomeData[81];

        for(int i = 0; i < 81; i++){
            biomeData[i] = new BiomeData();
        }

    }


    // getServer().getConsoleSender().sendMessage("X: " + chunk.getX() + "z: " + chunk.getZ());
    // Bukkit.broadcastMessage("X: " + chunk.getX() + "z: " + chunk.getZ());


    // coal == 0, copper == 1, Lapis Lazuli == 2, Iron == 3, gold == 4, redstone == 5, diamond == 6, emerald == 7
    // make a enum goddammit


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){

        if (event.isNewChunk()) {

            double chanceThread = Math.random();

            Chunk chunk = event.getChunk();

            if(chanceThread >= 0.5) {

                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {

                        for (int h = 0; h < 255; h++) {
                            for (int i = 0; i < 16; i++) {
                                for (int j = 0; j < 16; j++) {

                                    Block block = chunk.getWorld().getBlockAt((chunk.getX() * 15) + i, h, (chunk.getZ() * 15) + j);

                                    int biome = block.getBiome().ordinal();

                                    updateOreCount(block, h, biome);


                                }

                            }

                        }

                    }
                };


                runnable.runTask(plugin);

            }
            else {

                BukkitRunnable runnable2 = new BukkitRunnable() {
                    @Override
                    public void run() {

                        for (int h = 0; h < 255; h++) {
                            for (int i = 0; i < 16; i++) {
                                for (int j = 0; j < 16; j++) {

                                    Block block = chunk.getWorld().getBlockAt((chunk.getX() * 15) + i, h, (chunk.getZ() * 15) + j);

                                    int biome = block.getBiome().ordinal();

                                    updateOreCount(block, h, biome);


                                }

                            }

                        }

                    }
                };

                runnable2.runTask(plugin);

            }


        }

    }


    void updateOreCount(Block block, int h, int biome){

        Material currentBlock = block.getType();

        if(currentBlock == DEEPSLATE_COAL_ORE || currentBlock == COAL_ORE){
            biomeData[biome].getOre(0).addOreCount(h); //coal
        }
        else if(currentBlock == DEEPSLATE_COPPER_ORE || currentBlock == COPPER_ORE){
            biomeData[biome].getOre(1).addOreCount(h); //copper
        }
        else if(currentBlock == DEEPSLATE_LAPIS_ORE || currentBlock == LAPIS_ORE){
            biomeData[biome].getOre(2).addOreCount(h); //lapis
        }
        else if(currentBlock == DEEPSLATE_IRON_ORE || currentBlock == IRON_ORE){
            biomeData[biome].getOre(3).addOreCount(h); //iron
        }
        else if(currentBlock == DEEPSLATE_GOLD_ORE || currentBlock == GOLD_ORE){
            biomeData[biome].getOre(4).addOreCount(h); //gold
        }
        else if(currentBlock == DEEPSLATE_REDSTONE_ORE || currentBlock == REDSTONE_ORE){
            biomeData[biome].getOre(5).addOreCount(h); //redstone
        }
        else if(currentBlock == DEEPSLATE_DIAMOND_ORE || currentBlock == DIAMOND_ORE){
            biomeData[biome].getOre(6).addOreCount(h); //diamond
        }
        else if(currentBlock == DEEPSLATE_EMERALD_ORE || currentBlock == EMERALD_ORE){
            biomeData[biome].getOre(7).addOreCount(h); //emerald
        }
        else{
            block.setType(AIR);
        }

        biomeData[biome].addBlockCount();

    }


    public BiomeData getBiomeData(int biome){
        return biomeData[biome];
    }


    public HighestCount[] get3HighestFromAllBiomes(int ore){

        HighestCount[] biggest = new HighestCount[3];

        for(int i = 0; i < 3; i++){
            biggest[i] = new HighestCount();
        }

        for(int i = 0; i < 81; i++){
            for(int h = 0; h < 255; h++) {

                if (biggest[0].getAmount() < biomeData[i].getOre(ore).getAmountAt(h)) {

                    biggest[2].setAmount(biggest[1].getAmount());
                    biggest[2].setHeight(biggest[1].getHeight());
                    biggest[2].setBiome(biggest[1].getBiome());

                    biggest[1].setAmount(biggest[0].getAmount());
                    biggest[1].setHeight(biggest[0].getHeight());
                    biggest[1].setBiome(biggest[0].getBiome());

                    biggest[0].setAmount(biomeData[i].getOre(ore).getAmountAt(h));
                    biggest[0].setHeight(h);
                    biggest[0].setBiome(i);

                } else if (biggest[1].getAmount() < biomeData[i].getOre(ore).getAmountAt(h)) {

                    biggest[2].setAmount(biggest[1].getAmount());
                    biggest[2].setHeight(biggest[1].getHeight());
                    biggest[2].setBiome(biggest[1].getBiome());

                    biggest[1].setAmount(biomeData[i].getOre(ore).getAmountAt(h));
                    biggest[1].setHeight(h);
                    biggest[1].setBiome(i);

                } else if (biggest[2].getAmount() < biomeData[i].getOre(ore).getAmountAt(h)) {

                    biggest[2].setAmount(biomeData[i].getOre(ore).getAmountAt(h));
                    biggest[2].setHeight(h);
                    biggest[2].setBiome(i);

                }

            }
        }

        return biggest;

    }



}