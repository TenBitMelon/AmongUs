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

public class gatherFuel extends Menu {

    public gatherFuel(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;

    @Override
    public String getMenuName() {
        return "Fuel Engine";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, "Filling ...");

        if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            inventory.setItem(43, green);

            runnable = new BukkitRunnable() {
                int t = 9;

                public void run() {
                    t--;

                    int spot = (t) * (3) / (8) + 1;
                    inventory.setItem(spot * 9 + 2, makeItem(Material.YELLOW_STAINED_GLASS_PANE, String.valueOf((double) t / 2)));
                    inventory.setItem(spot * 9 + 3, makeItem(Material.YELLOW_STAINED_GLASS_PANE, String.valueOf((double) t / 2)));
                    inventory.setItem(spot * 9 + 4, makeItem(Material.YELLOW_STAINED_GLASS_PANE, String.valueOf((double) t / 2)));

                    if (t <= 0) {
                        cancel();
                        Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                            player.closeInventory();
                            task.setComplete();
                        }, 10);
                    }
                }
            }.runTaskTimer(AmongUs.plugin, 1, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        runnable.cancel();
    }

    @Override
    public void setMenuItems() {
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "Fill");
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");

        inventory.setItem(43, red);

        for (int i = 1; i < 5; i++) {
            inventory.setItem(i, black);
        }
        for (int i = 46; i < 51; i++) {
            inventory.setItem(i, black);
        }
        for (int i = 1; i < 46; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 1)
                i += 8;
        }
        for (int i = 5; i < 51; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 5)
                i += 8;
        }

        setFillerGlass();

    }
}
