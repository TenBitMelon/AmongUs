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

public class uploadData extends Menu {

    public uploadData(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;

    @Override
    public String getMenuName() {
        return "Upload Data";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        assert clickedItem != null;
        if (clickedItem.getType() == Material.OAK_SIGN) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);

            ItemStack blankBlack = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
            inventory.setItem(28, blankBlack);
            inventory.setItem(29, blankBlack);
            inventory.setItem(30, blankBlack);
            inventory.setItem(31, blankBlack);
            inventory.setItem(32, blankBlack);
            inventory.setItem(33, blankBlack);
            inventory.setItem(34, blankBlack);

            runnable = new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t++;

                    int spot = (t) * (6) / (17) + 1;
                    inventory.setItem(spot + 27, makeItem(Material.WHITE_STAINED_GLASS_PANE, String.valueOf((double) t / 2)));

                    if (inventory.getItem(14).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
                        inventory.setItem(12, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "bzz"));
                        inventory.setItem(13, fillerGlass);
                        inventory.setItem(14, fillerGlass);
                    } else if (inventory.getItem(12).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
                        inventory.setItem(13, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "zap"));
                        inventory.setItem(14, fillerGlass);
                        inventory.setItem(12, fillerGlass);
                    } else if (inventory.getItem(13).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
                        inventory.setItem(14, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "lel"));
                        inventory.setItem(12, fillerGlass);
                        inventory.setItem(13, fillerGlass);
                    } else
                        inventory.setItem(12, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "bzz"));

                    if (t >= 17) {
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

        ItemStack folder = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "folder :)");
        ItemStack startDownload = makeItem(Material.OAK_SIGN, "Start Upload");

        inventory.setItem(10, folder);
        inventory.setItem(11, folder);
        inventory.setItem(15, folder);
        inventory.setItem(16, folder);
        inventory.setItem(31, startDownload);
        setFillerGlass();

    }
}
