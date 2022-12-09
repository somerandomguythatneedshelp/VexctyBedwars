package com.f2erg.vexctybedwars.commands;

import com.f2erg.vexctybedwars.GameState;
import com.f2erg.vexctybedwars.Minigame;
import com.f2erg.vexctybedwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private Minigame minigame;

    public ArenaCommand(Minigame minigame) {
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.GREEN + "These are the Available arenas:");
                for (Arena arena : minigame.getArenaManager().getArenas()) {
                    player.sendMessage(ChatColor.GREEN + "- " + arena.getId() + "(" + arena.getState().name() + ").");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                Arena arena = minigame.getArenaManager().getArena(player);
                if (arena != null) {
                    player.sendMessage(ChatColor.RED + "You have left, do /arena join to join back and play again!");
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a arena.");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                if (minigame.getArenaManager().getArena(player) != null) {
                    player.sendMessage(ChatColor.RED + "You are already playing in an arena.");
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "The id you entered does not exist! Use /arena list and use on of those ids.");
                    return false;
                }


                if (id >= 0 && id < minigame.getArenaManager().getArenas().size()) {
                    Arena arena = minigame.getArenaManager().getArena(id);
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                        if (arena.canJoin()) {
                            player.sendMessage(ChatColor.GREEN + "You are playing in Arena " + id + ".");
                            arena.addPlayer(player);
                        } else {
                            player.sendMessage(ChatColor.RED + "You cannot join this arena as the map is still loading.");
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "You can not join this arena.");
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "The id you entered does not exist! Use /arena list and use one of those ids.");
                }


            } else {
                player.sendMessage(ChatColor.RED + "Sorry But the command you used does not exist! Please use either the following:");
                player.sendMessage(ChatColor.GREEN + "- /arena join <id>");
                player.sendMessage(ChatColor.GREEN + "- /arena leave");
                player.sendMessage(ChatColor.GREEN + "- /arena list");

            }

        }
        return false;
    }
}
