package com.theendercore.endertownydynamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;

import static com.theendercore.endertownydynamp.EnderTownyDynamp.pp;

public class PenisCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("player only!");
            return true;
        }
        Player player = (Player) sender;

        if ((cmd.getName().equalsIgnoreCase("penis")) && (player.getName().equals("TheEnderCore"))) {

            Bukkit.getLogger().info(pp + " Penis Comand");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String filePath = "pp.json";
            Towny Penis = new Towny("pp", 45, "Cock");
            FileWriter writer = null;
            try {
                writer = new FileWriter(filePath);
                writer.write(gson.toJson(Penis));
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

            player.sendMessage("pp");

            return true;
        } else {
            player.sendMessage("You are not TheEnderCore!\nYou are  :" + player.getName());
        }

        return true;
    }
}
