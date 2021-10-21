package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class calibrateDistributor extends Menu {

    public calibrateDistributor(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;

    @Override
    public String getMenuName() {
        return "Calibrate Distributor";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        int slot = event.getSlot();
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "");
        ItemStack light_blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");

        switch (slot) {
            case 14:
            case 15:
            case 16:
                if(inventory.getItem(11).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    runnable.cancel();
                    runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(inventory.getItem(19).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(19, light_blue);
                                inventory.setItem(20, white);
                            } else if(inventory.getItem(20).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(20, light_blue);
                                inventory.setItem(21, white);
                            } else if(inventory.getItem(21).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(21, light_blue);
                                inventory.setItem(19, white);
                            } else {
                                inventory.setItem(19, white);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 15);
                } else {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    runnable.cancel();
                    runnable = new BukkitRunnable(){
                        @Override
                        public void run() {
                            if(inventory.getItem(10).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(10, yellow);
                                inventory.setItem(11, white);
                            } else if(inventory.getItem(11).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(11, yellow);
                                inventory.setItem(12, white);
                            } else if(inventory.getItem(12).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(12, yellow);
                                inventory.setItem(10, white);
                            } else {
                                inventory.setItem(10, white);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                }
            case 23:
            case 24:
            case 25:
                if(inventory.getItem(20).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    runnable.cancel();
                    runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(inventory.getItem(28).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(28, blue);
                                inventory.setItem(29, white);
                            } else if(inventory.getItem(29).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(29, blue);
                                inventory.setItem(30, white);
                            } else if(inventory.getItem(30).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(30, blue);
                                inventory.setItem(28, white);
                            } else {
                                inventory.setItem(28, white);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 10);
                } else {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    runnable.cancel();
                    runnable = new BukkitRunnable(){
                        @Override
                        public void run() {
                            if(inventory.getItem(10).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(10, yellow);
                                inventory.setItem(11, white);
                            } else if(inventory.getItem(11).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(11, yellow);
                                inventory.setItem(12, white);
                            } else if(inventory.getItem(12).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                                inventory.setItem(12, yellow);
                                inventory.setItem(10, white);
                            } else {
                                inventory.setItem(10, white);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                }
            case 32:
            case 33:
            case 34:
                runnable.cancel();
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        player.closeInventory();
                        task.setComplete();
                    }
                }.runTaskLater(plugin, 4);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "");
        ItemStack light_blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "|");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, black);
            inventory.setItem(i + 36, black);
        }

        inventory.setItem(9, black);
        inventory.setItem(17, black);
        inventory.setItem(18, black);
        inventory.setItem(26, black);
        inventory.setItem(27, black);
        inventory.setItem(35, black);

        inventory.setItem(2, white);
        inventory.setItem(38, white);

        for (int i = 1; i < 4; i++) {
            inventory.setItem(i + 9, yellow);
            inventory.setItem(i + 9 + 4, yellow);
            inventory.setItem(i + 18, light_blue);
            inventory.setItem(i + 18 + 4, light_blue);
            inventory.setItem(i + 27, blue);
            inventory.setItem(i + 27 + 4, blue);
        }

        runnable = new BukkitRunnable(){
            @Override
            public void run() {
                if(inventory.getItem(10).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                    inventory.setItem(10, yellow);
                    inventory.setItem(11, white);
                } else if(inventory.getItem(11).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                    inventory.setItem(11, yellow);
                    inventory.setItem(12, white);
                } else if(inventory.getItem(12).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                    inventory.setItem(12, yellow);
                    inventory.setItem(10, white);
                } else {
                    inventory.setItem(10, white);
                }
            }
        }.runTaskTimer(plugin, 0, 20);

        setFillerGlass();
    }
}
