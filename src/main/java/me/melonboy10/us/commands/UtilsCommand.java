package me.melonboy10.us.commands;

import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.utilities.AmongUs;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UtilsCommand implements CommandExecutor {

    private AmongUsPlugin main = null;
    private final Plugin plugin = AmongUsPlugin.getPlugin(AmongUsPlugin.class);

    public UtilsCommand(AmongUsPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("among.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args[0] == null) {
            sender.sendMessage(ChatColor.RED + "You need to specify what command to run. dummy");
        }

        switch (args[0]) {
            case "isplaying":
                isPlaying((Player) sender);
                break;
            case "colors":
                diaplayColors((Player) sender);
                break;
            case "currentplayers":
                displayCurrentPlayers((Player) sender);
                break;
            case "playingplayers":
                displayPlayingPlayers((Player) sender);
                break;
            case "teams":
//                if(args.length > 1) displayTeams((Player) sender, args[1]);
//                else displayTeams((Player) sender);
                break;
            case "deadplayers":
                displayDeadPlayers((Player) sender);
                break;
            case "setplaying":
                setPlaying((Player) sender);
                break;
            case "player":
                if (args.length > 1) displayPlayer((Player) sender, args[1]);
                else displayPlayer((Player) sender, null);
                break;
            case "start":
                AmongUs.startGame();
                break;
            case "stop":
            case "end":
                AmongUs.endGame();
                break;
            case "speed":
                if (args.length > 1) setSpeed((Player) sender, args[1]);
                else setSpeed((Player) sender, "1");
                break;
            case "kill":
                AmongUs.getPlayerUtility(Bukkit.getPlayer(args[1])).kill(((Player) sender));
                break;
        }
        return true;
    }

    public void setSpeed(Player player, String arg) {
        player.sendMessage(ChatColor.GRAY + "Speed now set at " + arg);
//        AmongUs.setWalkSpeed(Integer.parseInt(arg));
    }

    private void error(Player sender, String arg) {
        if (arg != null) {
            sender.sendMessage(ChatColor.GRAY + "You have sent an error to " + arg + ".");

            Player p = Bukkit.getPlayer(arg);
            final int[] loopCount = {50};

            p.sendMessage(ChatColor.RED + "A fatal error has occurred. Please report this.");
            new BukkitRunnable() {
                public void run() {
                    if (loopCount[0] >= 0) {
                        p.sendMessage(ChatColor.RED + "Loop count error: fatal flaw: server corrupted: " + loopCount[0]);
                        p.sendTitle(ChatColor.RED + "error", String.valueOf(loopCount[0]), 0, 2, 0);
                        loopCount[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 2);
        } else {
            sender.sendMessage(ChatColor.GRAY + "You have sent an error to yourself.");
            final int[] loopCount = {50};

            sender.sendMessage(ChatColor.RED + "A fatal error has occurred. Please report this.");
            new BukkitRunnable() {
                public void run() {
                    if (loopCount[0] >= 0) {
                        sender.sendMessage(ChatColor.RED + "Loop count error: fatal flaw: server corrupted: " + loopCount[0]);
                        sender.sendTitle(ChatColor.RED + "error", String.valueOf(loopCount[0]), 0, 2, 0);
                        loopCount[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 2);
        }
    }

    private void isPlaying(Player sender) {
        if (AmongUs.isPlaying()) {
            sender.sendMessage(ChatColor.GRAY + "There is currently a game going." + ChatColor.DARK_GRAY + " (true)");
        } else {
            sender.sendMessage(ChatColor.GRAY + "There is no game currently going." + ChatColor.DARK_GRAY + " (false)");
        }
    }

    private void setPlaying(Player sender) {
        if (AmongUs.isPlaying()) {
            AmongUs.setPlaying(false);
            sender.sendMessage(ChatColor.GRAY + "There is no longer a game going.");
        } else {
            AmongUs.setPlaying(true);
            sender.sendMessage(ChatColor.GRAY + "There is now a game going.");
        }
    }

    private void displayDeadPlayers(Player sender) {
        sender.sendMessage(ChatColor.GRAY + "Dead Players: ");

        if (AmongUs.isPlaying()) {
            for (Player p : AmongUs.getDeadPlayers()) {
                sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + p.getDisplayName());
            }
        } else {
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "No game currently being played.");
        }
    }

    private void diaplayColors(Player sender) {

        sender.sendMessage(ChatColor.RED + "C" + ChatColor.GOLD + "o" + ChatColor.YELLOW + "l" + ChatColor.GREEN + "o" + ChatColor.AQUA + "r" + ChatColor.LIGHT_PURPLE + "s" + ChatColor.GRAY + ":");
        for (AmongUs.Colors color : AmongUs.Colors.values()) {
            TextComponent messageComponent = new TextComponent(ChatColor.DARK_GRAY + " > " + AmongUs.colorToChat.get(color) + color.toString());
            messageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/utils teams " + color));

            sender.spigot().sendMessage(messageComponent);
        }
    }

    private void displayCurrentPlayers(Player sender) {
        sender.sendMessage(ChatColor.GRAY + "Players: (NOTE: These are not all players online, but all that can be put into a game.)");
        for (Player p : AmongUs.getCurrentPlayers()) {
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + p.getDisplayName());
        }
    }

    private void displayPlayingPlayers(Player sender) {
        sender.sendMessage(ChatColor.GRAY + "Players in Game: ");

        if (AmongUs.isPlaying()) {
            for (Object p : AmongUs.getPlayingPlayers()) {
                sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + p.toString());
            }
        } else {
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "No game currently being played.");
        }
    }

//    private void displayTeams(Player sender) {
//        ArrayList<Team> teams = new ArrayList<>();
//
//        for(Object color : AmongUs.colors) {
//            Team team = AmongUs.getTeams().get(color);
//            if(team != null) teams.add(team);
//        }
//
//        sender.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------");
//        for(Team team : teams) {
//            sender.sendMessage(team.getColor() + team.getDisplayName());
//            for(OfflinePlayer p : team.getPlayers()) {
//                TextComponent messageComponent = new TextComponent(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + p.getName());
//                messageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/utils player " + p.getName()));
//
//                sender.spigot().sendMessage(messageComponent);
//            }
//        }
//    }

//    private void displayTeams(Player sender, String color) {
//        if(AmongUs.colors.contains(color)) {
//            if(AmongUs.getTeams().containsKey(color)) {
//                Team team = AmongUs.getTeams().get(color);
//
//                sender.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------");
//                sender.sendMessage(team.getColor() + team.getDisplayName());
//                for (OfflinePlayer p : team.getPlayers()) {
//
//                    TextComponent messageComponent = new TextComponent(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + p.getName());
//                    messageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/utils player " + p.getName()));
//
//                    sender.spigot().sendMessage(messageComponent);
//                }
//            }
//        }
//    }

    private void displayPlayer(Player sender, String player) {
        if (player != null) {

            sender.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------");
            sender.sendMessage(ChatColor.GRAY + player);

            Player p = Bukkit.getPlayer(player);

            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "Color: " + AmongUs.getPlayerUtility(p).getColor());
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "Dead: " + AmongUs.getPlayerUtility(p).isDead());
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "Impostor: " + AmongUs.getPlayerUtility(p).isImpostor());
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "InVent: " + AmongUs.getPlayerUtility(p).isInVent());
            sender.sendMessage(ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + "InCamera: " + AmongUs.getPlayerUtility(p).isInCamera());

        } else {
//            displayTeams(sender);
        }
    }
}
