package me.melonboy10.us.commands;

import me.melonboy10.us.AmongUsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TabComplete implements TabCompleter {

    private AmongUsPlugin main = null;

    public TabComplete(AmongUsPlugin main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        // verify sender is a player
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;

        ArrayList<String> arguments = new ArrayList<>();

        // tab completion for /exchange command
        if (command.getName().equals("utils")) {

            // no arguments
            if (args.length == 1) {
                if (player.hasPermission("among.admin")) {
                    arguments.addAll(Arrays.asList("isplaying", "colors", "currentplayers", "playingplayers", "teams", "error", "kill", "deadplayers", "setplaying", "player", "start", "end", "speed", "stop"));
                }

                Iterator<String> iter = arguments.iterator();
                while (iter.hasNext()) {
                    String str = iter.next().toLowerCase();
                    if (!str.contains(args[0].toLowerCase())) iter.remove();
                }
            }
        }

        return arguments;

    }

}
