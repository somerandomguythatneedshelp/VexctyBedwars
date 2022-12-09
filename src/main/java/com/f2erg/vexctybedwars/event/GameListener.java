package com.f2erg.vexctybedwars.event;

import com.f2erg.vexctybedwars.GameState;
import com.f2erg.vexctybedwars.Minigame;
import com.f2erg.vexctybedwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.ArrayList;

public class GameListener implements Listener {

    private Minigame minigame;

    public GameListener(Minigame minigame) {
        this.minigame = minigame;

    }

    private ArrayList isInWorld = new ArrayList();

    Block block;

    World world;

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {

        Arena arena = minigame.getArenaManager().getArena(e.getWorld());

        if (arena != null) {
            arena.toggleCanJoin();
        }

        isInWorld.add(world); // the world is in the array list

    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {


        if (isInWorld.contains(block)) { // if they try to break a block which is a part of the map
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
}