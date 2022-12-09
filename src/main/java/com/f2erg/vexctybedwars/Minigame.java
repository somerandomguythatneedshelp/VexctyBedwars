package com.f2erg.vexctybedwars;

import com.f2erg.vexctybedwars.commands.ArenaCommand;
import com.f2erg.vexctybedwars.event.ConnectListener;
import com.f2erg.vexctybedwars.event.GameListener;
import com.f2erg.vexctybedwars.manager.ArenaManager;
import com.f2erg.vexctybedwars.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigame extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public ArenaManager getArenaManager() { return arenaManager; }
//    public LangManager getLangManager() { return langManager; }

    public void registerCommands() {
        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    // TODO: NPCS
    // TODO: MISC

}
