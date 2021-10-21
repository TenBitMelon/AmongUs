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

import java.util.Random;

public class inspectSample extends Menu {

    public inspectSample(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    boolean active;
    boolean found;
    BukkitTask runnable;
    int anomaly;

    @Override
    public String getMenuName() {
        return "Inspect Sample";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, " ");
        ItemStack pink = makeItem(Material.PINK_STAINED_GLASS_PANE, " ");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, " ");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, " ");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, " ");

        if (clickedItem.getType().equals(Material.LIME_STAINED_GLASS_PANE) && !active) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            active = true;
            Random rand = new Random();
            anomaly = rand.nextInt(3);
            inventory.setItem(52, black);

            runnable = new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t++;

                    if (t == 1) {
                        inventory.setItem(11, black);
                        inventory.setItem(38, blue);
                    } else if (t == 4) {
                        inventory.setItem(11, fillerGlass);
                        inventory.setItem(13, black);
                        inventory.setItem(40, blue);
                    } else if (t == 7) {
                        inventory.setItem(13, fillerGlass);
                        inventory.setItem(15, black);
                        inventory.setItem(42, blue);
                    } else if (t == 10) {
                        inventory.setItem(15, fillerGlass);
                    } else if (t == 18) {
                        inventory.setItem(anomaly * 2 + 38, pink);
                    } else if (t == 24) {
                        inventory.setItem(anomaly * 2 + 38, red);
                        inventory.setItem(47, green);
                        inventory.setItem(49, green);
                        inventory.setItem(51, green);
                        inventory.setItem(anomaly * 2 + 47, lime);

                        found = true;
                    }


                    if (t >= 25) {
                        cancel();
                    }
                }
            }.runTaskTimer(AmongUs.plugin, 1, 10);
        }

        if (found && clickedItem.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                player.closeInventory();
                task.setComplete();
            }, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        runnable.cancel();
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, " ");

        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, black);
            if (i > 5 && i % 9 == 0 && i < 45)
                i += 7;
        }

        inventory.setItem(29, white);
        inventory.setItem(38, white);
        inventory.setItem(31, white);
        inventory.setItem(40, white);
        inventory.setItem(33, white);
        inventory.setItem(42, white);

        inventory.setItem(52, lime);

        setFillerGlass();
    }
}
