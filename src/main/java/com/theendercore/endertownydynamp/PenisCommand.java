package com.theendercore.endertownydynamp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.theendercore.endertownydynamp.EnderTownyDynamp.pp;

public class PenisCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("penis")) {

            Bukkit.getLogger().info(pp + " Penis Comand");















            return true;
        }

        return true;
    }
}
