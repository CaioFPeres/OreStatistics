import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.*;


public class OreCommands implements CommandExecutor {

    private ChunkDeletion chunkDeletionClass;

    OreCommands(ChunkDeletion chunkDeletionClass) {
        this.chunkDeletionClass = chunkDeletionClass;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        if (s.equals("coal")) {

            sendStatistic(s, strings, 0);

        } else if (s.equals("copper")) {

            sendStatistic(s, strings, 1);

        } else if (s.equals("lapis")) {

            sendStatistic(s, strings, 2);

        } else if (s.equals("iron")) {

            sendStatistic(s, strings, 3);

        } else if (s.equals("gold")) {

            sendStatistic(s, strings, 4);

        } else if (s.equals("redstone")) {

            sendStatistic(s, strings, 5);

        } else if (s.equals("diamond")) {

            sendStatistic(s, strings, 6);

        } else if (s.equals("emerald")) {

            sendStatistic(s, strings, 7);

        } else if (s.equals("save")) {

            HighestCount biggest = new HighestCount();

            String filename = "filename.csv";

            File arquivo = new File(filename);

            FileWriter fw = null;

            if (arquivo.exists()) {
                try {
                    fw = new FileWriter(filename, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    arquivo.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    fw = new FileWriter(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            try {
                fw.write("Biome" + "," + "Coal" + "," + "Copper" + "," + "Lapis" + "," + "Iron" + "," + "Gold" + "," + "Redstone" + "," + "Diamond" + "," + "Emerald" + "," + "TotalBlocks\n");
            } catch (IOException e) {
                e.printStackTrace();
            }



            for (int i = 0; i < 81; i++) {

                try {
                    fw.write(i + ",");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int j = 0; j < 8; j++) {
                    for (int h = 0; h < 255; h++) {

                        if (biggest.getAmount() < chunkDeletionClass.getBiomeData(i).getOre(j).getAmountAt(h)){
                            biggest.setAmount(chunkDeletionClass.getBiomeData(i).getOre(j).getAmountAt(h));
                            biggest.setHeight(h);
                        }

                    }

                    try {
                        fw.write(biggest.getHeight() + " - " + biggest.getAmount() + ",");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    biggest.setAmount(0);
                    biggest.setHeight(0);

                }


                try {
                    fw.write(chunkDeletionClass.getBiomeData(i).getBlockCount() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return true;
    }



    boolean sendStatistic(String s, String[] args, int ore) {

        Biome[] biome = Biome.values();

        if (args.length > 1) {
            Bukkit.broadcastMessage("Amount of " + s + " at Y = " + args[1] + " in biome " + biome[Integer.parseInt(args[0])] + ": " + chunkDeletionClass.getBiomeData(Integer.parseInt(args[0])).getOre(ore).getAmountAt(Integer.parseInt(args[1])));
        } else if (args.length > 0) {

            HighestCount[] highest = chunkDeletionClass.getBiomeData(Integer.parseInt(args[0])).getOre(ore).get3HighestAmountIndex();

            Bukkit.broadcastMessage("3 highest amounts of " + s + " at:\n" + highest[0].getHeight() + " with " + highest[0].getAmount());
            Bukkit.broadcastMessage(highest[1].getHeight() + " with " + highest[1].getAmount());
            Bukkit.broadcastMessage(highest[2].getHeight() + " with " + highest[2].getAmount());
            Bukkit.broadcastMessage("in " + biome[Integer.parseInt(args[0])]);

        }
        else{

            HighestCount[] highest = chunkDeletionClass.get3HighestFromAllBiomes(ore);

            Bukkit.broadcastMessage("3 highest amounts of " + s + " at:\n" + highest[0].getHeight() + " with " + highest[0].getAmount() + " in " + biome[highest[0].getBiome()]);
            Bukkit.broadcastMessage(highest[1].getHeight() + " with " + highest[1].getAmount() + " in " + biome[highest[1].getBiome()]);
            Bukkit.broadcastMessage(highest[2].getHeight() + " with " + highest[2].getAmount() + " in " + biome[highest[2].getBiome()]);

        }

        return true;
    }

}