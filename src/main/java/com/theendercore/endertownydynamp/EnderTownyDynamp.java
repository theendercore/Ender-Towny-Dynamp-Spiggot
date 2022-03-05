package com.theendercore.endertownydynamp;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EnderTownyDynamp extends JavaPlugin {

    public static final String pp = "[Ender's Dynamp Towny Plugin]";
    private static final Version requiredTownyVersion = Version.fromString("0.97.5.0");
    public static String townyVersion;
    public static TownyUniverse townyUniverse = TownyUniverse.getInstance();

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
        // Plugin shutdown logic
    }
}
