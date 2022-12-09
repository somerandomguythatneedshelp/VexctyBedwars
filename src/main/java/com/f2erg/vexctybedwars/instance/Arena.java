package com.f2erg.vexctybedwars.instance;

import com.f2erg.vexctybedwars.GameState;
import com.f2erg.vexctybedwars.Minigame;
import com.f2erg.vexctybedwars.instance.game.Game;
import com.f2erg.vexctybedwars.manager.ConfigManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private Minigame minigame;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;
    private boolean canJoin;

    public Arena(Minigame minigame, int id, Location spawn){
        this.minigame = minigame;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(minigame, this);
        this.game = new Game(this);
        this.canJoin = true;
    }

    /*GAME */

    public void start(){
        game.start();
    }

    public void reset(){
        if (state == GameState.LIVE) {
            Game.hasStarted = false;
            this.canJoin = false;
            Location loc = ConfigManager.getLobbySpawn();
            for (UUID uuid: players){
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(loc);
            }
            players.clear();

            String worldName = spawn.getWorld().getName();
            Bukkit.unloadWorld(spawn.getWorld(), false);
            World world = Bukkit.createWorld(new WorldCreator(worldName));
            world.setAutoSave(false);

        }
        Game.hasStarted = false;
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /*TOOLS */

    public void sendMessage(String message){
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    /*PLAYERS */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "There is not enough players. Countdown stopped.");
            reset();
            return;
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.GREEN + "YOU WON!!");
            reset();
        }
    }

    /*INFO */

    public int getId(){
        return id;
    }

    public World getWorld() { return spawn.getWorld(); }

    public GameState getState(){
        return state;
    }

    public List<UUID> getPlayers(){
        return players;
    }

    public Game getGame() {
        return game;
    }

    public void setState(GameState state){
        this.state = state;
    }


    public boolean canJoin() { return canJoin; }
    public void toggleCanJoin() { this.canJoin = !this.canJoin; }



}
