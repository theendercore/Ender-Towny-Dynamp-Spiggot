package com.theendercore.endertownydynamp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnderTownyDynamp extends JavaPlugin {

    public static final String pp = "[Ender's Dynamp Towny Plugin]";
    final PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(pp + " pp");
        getCommand("penis").setExecutor(new PenisCommand());

        String townyVersion = pm.getPlugin("Towny").getDescription().getVersion();
        Bukkit.getLogger().info(pp + " Towny Version : " + townyVersion);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
