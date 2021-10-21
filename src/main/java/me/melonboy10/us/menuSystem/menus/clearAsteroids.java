package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class clearAsteroids extends Menu {

    public clearAsteroids(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    int count = 0;
    BukkitTask runnable;

    @Override
    public String getMenuName() {
        return "Clear Asteroids";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack greenFiller = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack counter = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "Asteroids:");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(event.getSlot(), greenFiller);
            inventory.addItem(counter);
            count++;

            if (AmongUs.visualTasks) {
                World world = AmongUs.world;
                BlockData redData = world.getBlockAt(-43, 5, 19).getBlockData();
                BlockData grayData = world.getBlockAt(-44, 5, 19).getBlockData();
                world.getBlockAt(29, 5, -98).setBlockData(redData);
                world.getBlockAt(29, 5, -99).setBlockData(redData);
                world.getBlockAt(30, 5, -98).setType(Material.AIR);
                world.getBlockAt(30, 5, -99).setType(Material.AIR);
                world.spawnParticle(Particle.EXPLOSION_LARGE, 30, 5, -98, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        world.getBlockAt(29, 5, -98).setBlockData(grayData);
                        world.getBlockAt(29, 5, -99).setBlockData(grayData);
                        world.getBlockAt(30, 5, -98).setBlockData(redData);
                        world.getBlockAt(30, 5, -99).setBlockData(redData);

                        world.spawnParticle(Particle.EXPLOSION_HUGE, 40, 5, -98, 1);
                    }
                }.runTaskLater(plugin, 20);
            }
        }

        if (count == 19) {
            runnable.cancel();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.closeInventory();
                task.setComplete();
            }, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        /*
            Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                player.closeInventory();
                task.setComplete();
            }, 10);

         */
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack asteroid = makeItem(Material.GREEN_STAINED_GLASS_PANE, "X");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "Asteroids:");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, black);
            inventory.setItem(i + 45, black);
        }

        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, green);
            }
        }

        inventory.setItem(49, yellow);

        runnable = new BukkitRunnable(){
            int t = 0;
            @Override
            public void run() {
                t++;

                for (int i = 0; i < 54; i++) {
                    if (inventory.getItem(i).getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                        if (i % 9 == 0) {
                            inventory.setItem(i, green);
                        } else {
                            inventory.setItem(i, green);
                            inventory.setItem(i - 1, asteroid);
                        }
                    }
                }

                if (t % 4 == 0) {
                    Random rand = new Random();
                    inventory.setItem((rand.nextInt(4) + 1) * 9 + 8, asteroid);
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }
}
