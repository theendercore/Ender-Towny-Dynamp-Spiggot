package com.theendercore.endertownydynamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class EnderTownyDynamp extends JavaPlugin {

    public static final String pp = "[Ender's Dynamp Towny Plugin]";
    private static final Version requiredTownyVersion = Version.fromString("0.97.5.0");
    public static String townyVersion;
    public static TownyUniverse townyUniverse = TownyUniverse.getInstance();

    boolean stop;


    public static void sendData() {

        Collection<Town> towns = townyUniverse.getTowns();
        if (towns == null) {
            Bukkit.getLogger().info(pp + " No towns");
            return;
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

                cox.add(new SendTown(town.getName(), "No Nation", mayor.getName(), resList.toString(), size));
            }
        }
        dumpJson(cox, gson, "./plugins/EnderTownyDynamp/pp.json");

    }


    public static void dumpJson(List<SendTown> sendTown, Gson gson, String filePath) {
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

    @Override
    public void onEnable() {
        File theDir = new File("./plugins/EnderTownyDynamp");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        final PluginManager pm = getServer().getPluginManager();
        Plugin p = pm.getPlugin("Towny");

        townyVersion = pm.getPlugin("Towny").getDescription().getVersion();
        if (p == null) {
            Bukkit.getLogger().warning("Cannot find Towny!");
            return;
        }

        getLogger().info("Towny version " + townyVersion + " found.");

        if (!townyVersionCheck(townyVersion)) {
            getLogger().severe("Towny version does not meet required version: " + requiredTownyVersion);
            disable();
            return;
        }

        getCommand("penis").setExecutor(new PenisCommand());

        stop = false;
        getServer().getScheduler().runTaskTimerAsynchronously(this, new EnderTownyUpdate(), 40, 200);
    }

    private boolean townyVersionCheck(String version) {
        return Version.fromString(version).compareTo(requiredTownyVersion) >= 0;
    }

    private void disable() {
        getLogger().severe("Ender's Dynamp Towny Disabled.");
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onDisable() {
        stop = true;
    }

    private class EnderTownyUpdate implements Runnable {
        public void run() {
            if (!stop) {
                if (TownyUniverse.getInstance().getDataSource() == null) {
                    stop = true;
                } else {
                    sendData();
                }
            }
        }
    }
}
