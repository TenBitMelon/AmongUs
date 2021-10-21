package me.melonboy10.us.events;

import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.utilities.AmongUs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class PlayerJoin implements Listener {

    private AmongUsPlugin main = null;

    public PlayerJoin(AmongUsPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void PlayerJoined(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        AmongUs.setUpPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player1.getLocation(), "minecraft:spawn", 1, 1));

        for (int k = -47; k < 39; k++) {
            for (int j = -111; j < -61; j++) {
                player.sendBlockChange(new Location(AmongUs.world, k, 7, j), Material.BLACK_STAINED_GLASS.createBlockData());
            }
        }
    }

    @EventHandler
    public void PlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        AmongUs.getPlayingPlayers().remove(player);
        AmongUs.getCurrentPlayers().remove(player);
    }

    @EventHandler
    public void PlayerLeavePig(VehicleExitEvent event) {
        if(event.getVehicle() instanceof Pig) {
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player1.getLocation(), "minecraft:spawn", 1, 1));
        }
    }
}
