package com.theendercore.endertownydynamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.theendercore.endertownydynamp.EnderTownyDynamp.*;

public class PenisCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("player only!");
            return true;
        }
        Player player = (Player) sender;

        if ((cmd.getName().equalsIgnoreCase("penis")) && (player.getName().equals("TheEnderCore"))) {

            Bukkit.getLogger().info(pp + " Penis Command");

            player.sendMessage(townyVersion);

//            for latter
//            player.sendMessage(String.valueOf(Coord.getCellSize()));

            Collection<Town> towns = townyUniverse.getTowns();

            if (towns == null) {
                player.sendMessage("No towns");
                return true;
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<SendTown> cox = new ArrayList<>();
            for (Town town : towns) {

                Collection<TownBlock> townblocks = town.getTownBlocks();
                List<SendTown.Bloxs> size = new ArrayList<>();
                Nation townNation;
                Resident mayor = town.getMayor();
                List<Resident> residents = town.getResidents();
                StringBuilder resList = null;

                for (Resident rez : residents) {
                    if (resList == null) {
                        resList = new StringBuilder(rez.getName());
                    } else {
                        resList.append(", ").append(rez.getName());
                    }

                }

                for (TownBlock tblock : townblocks) {
                    size.add(new SendTown.Bloxs(tblock.getX(), tblock.getZ(), tblock.isOutpost()));
                }

                if (town.hasNation()) {
                    try {
                        townNation = town.getNation();
                        cox.add(new SendTown(town.getName(), townNation.getName(), mayor.getName(), resList.toString(), size));
                    } catch (NotRegisteredException e) {
                        e.printStackTrace();
                    }
                } else {

                    cox.add(new SendTown(town.getName(),  "No Nation", mayor.getName(), resList.toString(), size));
                }
            }
            for (SendTown notCox : cox) {
                player.sendMessage(gson.toJson(notCox));
                player.sendMessage("-------------------------------------Iteration------------------------------------");
            }
            dumpJson(cox, gson, "./plugins/EnderTownyDynamp/pp.json");
            return true;

        } else {
            player.sendMessage("You are not TheEnderCore!\nYou are  :" + player.getName());
        }

        return true;
    }

    private void dumpJson(List<SendTown> sendTown, Gson gson, String filePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(gson.toJson(sendTown));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
