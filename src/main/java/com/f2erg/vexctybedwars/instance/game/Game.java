package com.f2erg.vexctybedwars.instance.game;

import com.f2erg.vexctybedwars.GameState;
import com.f2erg.vexctybedwars.instance.Arena;
import org.bukkit.ChatColor;

public class Game {

    private Arena arena;

    public static boolean hasStarted;

    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        Game.hasStarted = true;
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "Your objective is to destroy the others teams mob spawner, if your mob spawner is broken, then you have one life and one chance to break the other teams mob spawner.");

    }
}