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

import java.util.ArrayList;
import java.util.Random;

public class startReactor extends Menu {

    public startReactor(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    int completed = 0;

    @Override
    public String getMenuName() {
        return "Start Reactor";
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
        ArrayList<Integer> slots = new ArrayList<Integer>() {{
            add(14);
            add(15);
            add(16);
            add(23);
            add(24);
            add(25);
            add(32);
            add(33);
            add(34);
        }};

        if (slots.contains(slot)) {
            assert clickedItem != null;
            if (clickedItem.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                inventory.setItem(slot, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, ""));
            } else {
                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                inventory.setItem(slot, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            }
        }

        if (
                inventory.getItem(10).getType().equals(inventory.getItem(14).getType()) &&
                        inventory.getItem(11).getType().equals(inventory.getItem(15).getType()) &&
                        inventory.getItem(12).getType().equals(inventory.getItem(16).getType()) &&
                        inventory.getItem(19).getType().equals(inventory.getItem(23).getType()) &&
                        inventory.getItem(20).getType().equals(inventory.getItem(24).getType()) &&
                        inventory.getItem(21).getType().equals(inventory.getItem(25).getType()) &&
                        inventory.getItem(28).getType().equals(inventory.getItem(32).getType()) &&
                        inventory.getItem(29).getType().equals(inventory.getItem(33).getType()) &&
                        inventory.getItem(30).getType().equals(inventory.getItem(34).getType())
        ) {
            completed++;
            inventory.setItem(completed + 1, makeItem(Material.BLUE_STAINED_GLASS_PANE, " "));

            if (completed < 5) {
                for (int i = 10; i < 31; i++) {

                    Random rand = new Random();
                    if (rand.nextInt(2) == 1) {
                        inventory.setItem(i, makeItem(Material.BLACK_STAINED_GLASS_PANE, " "));
                    } else {
                        inventory.setItem(i, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " "));
                    }

                    if (i % 9 == 3) {
                        i = i + 6;
                    }
                }
            } else {
                Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                    player.closeInventory();
                    task.setComplete();
                }, 10);
            }
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
        ItemStack screen = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack litScreen = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");

        for (int i = 10; i < 31; i++) {

            Random rand = new Random();
            if (rand.nextInt(2) == 1) {
                inventory.setItem(i, screen);
            } else {
                inventory.setItem(i, litScreen);
            }

            if (i % 9 == 3) {
                i = i + 6;
            }
        }

        for (int i = 14; i < 35; i++) {
            inventory.setItem(i, screen);
            if (i % 9 == 7) {
                i = i + 6;
            }
        }

        setFillerGlass();

    }
}
