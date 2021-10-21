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

public class emptyGarbage extends Menu {

    public emptyGarbage(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;
    boolean active;

    @Override
    public String getMenuName() {
        return "Empty Garbage";
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
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE) && !active) {
            active = true;

            inventory.setItem(15, black);
            inventory.setItem(16, black);
            inventory.setItem(17, black);
            inventory.setItem(25, black);

            inventory.setItem(34, white);
            inventory.setItem(42, white);
            inventory.setItem(43, white);
            inventory.setItem(44, white);

            runnable = new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t++;

                    for (int i = 41; i > 18; i--) {
                        inventory.setItem(i, inventory.getItem(i - 9));
                        if (i % 9 == 8)
                            i -= 3;
                    }

                    if (t >= 4) {
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
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, " ");
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, " ");

        for (int i = 6; i < 54; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 0)
                i += 5;
        }

        for (int i = 0; i < 8; i++) {
            inventory.setItem(i, black);
        }

        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, black);
        }

        inventory.setItem(15, white);
        inventory.setItem(16, white);
        inventory.setItem(17, white);
        inventory.setItem(25, white);

        inventory.setItem(19, red);
        inventory.setItem(22, green);
        inventory.setItem(28, green);
        inventory.setItem(29, red);
        inventory.setItem(30, green);
        inventory.setItem(31, red);
        inventory.setItem(37, green);
        inventory.setItem(38, red);
        inventory.setItem(39, green);
        inventory.setItem(40, red);
        inventory.setItem(41, red);

        setFillerGlass();
    }
}
