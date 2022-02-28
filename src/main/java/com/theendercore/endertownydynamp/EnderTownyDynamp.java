package com.theendercore.endertownydynamp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnderTownyDynamp extends JavaPlugin {

    public static final String pp = "[Ender's Dynamp Towny Plugin]";
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info(pp + " pp");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
