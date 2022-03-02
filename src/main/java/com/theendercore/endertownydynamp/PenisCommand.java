package com.theendercore.endertownydynamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

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

            for (Town town : towns) {

                player.sendMessage(town.getName());
                dumpJson(town);

            }

            return true;

        } else {
            player.sendMessage("You are not TheEnderCore!\nYou are  :" + player.getName());
        }

        return true;
    }

    private void dumpJson(Town town) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filePath = "pp.json";
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(gson.toJson(town));
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
