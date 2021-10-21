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

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class chartCourse extends Menu {

    public chartCourse(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Chart Course";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "X");
        ItemStack lightBlue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");

        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            switch (event.getSlot()) {
                case 19:
                    inventory.setItem(39, white);
                    inventory.setItem(19, lightBlue);
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    break;
                case 39:
                    inventory.setItem(22, white);
                    inventory.setItem(39, lightBlue);
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    break;
                case 22:
                    inventory.setItem(15, white);
                    inventory.setItem(16, white);
                    inventory.setItem(24, white);
                    inventory.setItem(25, white);
                    inventory.setItem(22, lightBlue);
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    break;
                case 15:
                case 16:
                case 24:
                case 25:
                    inventory.setItem(15, lightBlue);
                    inventory.setItem(16, lightBlue);
                    inventory.setItem(24, lightBlue);
                    inventory.setItem(25, lightBlue);
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    break;

            }
        }

        if (inventory.getItem(15).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
            new BukkitRunnable() {
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
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "X");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, black);
            inventory.setItem(i + 45, black);
        }
        inventory.setItem(9, black);
        inventory.setItem(17, black);
        inventory.setItem(18, black);
        inventory.setItem(27, black);
        inventory.setItem(28, black);
        inventory.setItem(35, black);
        inventory.setItem(36, black);
        inventory.setItem(44, black);
        inventory.setItem(45, black);

        inventory.setItem(19, white);
        inventory.setItem(39, blue);
        inventory.setItem(22, blue);
        inventory.setItem(15, blue);
        inventory.setItem(16, blue);
        inventory.setItem(24, blue);
        inventory.setItem(25, blue);

        setFillerGlass();
    }
}
