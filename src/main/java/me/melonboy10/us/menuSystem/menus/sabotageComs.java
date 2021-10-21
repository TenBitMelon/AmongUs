package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class sabotageComs extends Menu {

    public sabotageComs(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static ArrayList<sabotageComs> menus = new ArrayList<>();

    @Override
    public String getMenuName() {
        return "Fix Communications";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        int slot = event.getSlot();
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            if (slot == 50) {
                if (inventory.getItem(29).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    if (inventory.getItem(30).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                        if (inventory.getItem(31).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                            if (inventory.getItem(32).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                                inventory.setItem(33, lime);
                            } else {
                                inventory.setItem(32, lime);
                            }
                        } else {
                            inventory.setItem(31, lime);
                        }
                    } else {
                        inventory.setItem(30, lime);
                    }
                } else {
                    inventory.setItem(29, lime);
                }
            } else if (slot == 48) {
                if (inventory.getItem(33).getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                    if (inventory.getItem(32).getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                        if (inventory.getItem(31).getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                            if (inventory.getItem(30).getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                                inventory.setItem(29, green);
                            } else {
                                inventory.setItem(30, green);
                            }
                        } else {
                            inventory.setItem(31, green);
                        }
                    } else {
                        inventory.setItem(32, green);
                    }
                } else {
                    inventory.setItem(33, green);
                }
            }
        }

        if (inventory.getItem(29).getType().equals(inventory.getItem(11).getType()) &&
                inventory.getItem(30).getType().equals(inventory.getItem(12).getType()) &&
                inventory.getItem(31).getType().equals(inventory.getItem(13).getType()) &&
                inventory.getItem(32).getType().equals(inventory.getItem(14).getType()) &&
                inventory.getItem(33).getType().equals(inventory.getItem(15).getType())
        ) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    for (int i = menus.size(); i > 0; i--) {
                        menus.get(i - 1).task.setComplete();
                        menus.get(i - 1).playerUtility.getOwner().closeInventory();
                    }
                    for (Player player1 : AmongUs.getPlayingPlayers()) {
                        for (Iterator<Task> iterator = AmongUs.getPlayerUtility(player1).getTasks().iterator(); iterator.hasNext(); ) {
                            Task task = iterator.next();
                            if (task.getTaskType().equals(Task.TaskType.COMMUNICATIONS_SABOTAGE)) {
                                iterator.remove();
                                task.setComplete();

                            }
                        }
                    }
                    sabotageMenu.comsComplete();
                }
            }.runTaskLater(plugin, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
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
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");

        for (int i = 1; i < 52; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 7)
                i += 2;
        }

        for (int i = 11; i < 16; i++) {
            inventory.setItem(i, green);
            inventory.setItem(i + 18, green);
        }

        for (int i = 0; i < (new Random()).nextInt(4) + 1; i++) {
            inventory.setItem(i + 11, lime);
        }

        inventory.setItem(48, white);
        inventory.setItem(49, red);
        inventory.setItem(50, white);

        setFillerGlass();

        menus.add(this);
    }
}
