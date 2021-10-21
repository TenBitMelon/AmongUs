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

public class stabilizeSteering extends Menu {

    public stabilizeSteering(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Stabilize Steering";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack top = makeItem(Material.WHITE_STAINED_GLASS_PANE, "|");
        ItemStack side = makeItem(Material.WHITE_STAINED_GLASS_PANE, "-");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            inventory.setItem(4, top);
            inventory.setItem(13, top);
            inventory.setItem(31, top);
            inventory.setItem(40, top);
            inventory.setItem(20, side);
            inventory.setItem(21, side);
            inventory.setItem(23, side);
            inventory.setItem(24, side);

            Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
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
        ItemStack screen = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");
        ItemStack middle = makeItem(Material.WHITE_STAINED_GLASS_PANE, "X");

        for (int i = 2; i < 42; i++) {
            inventory.setItem(i, screen);
            if (i % 9 == 6)
                i += 4;
        }
        inventory.setItem(22, middle);
        inventory.setItem(2, fillerGlass);
        inventory.setItem(6, fillerGlass);
        inventory.setItem(38, fillerGlass);
        setFillerGlass();
    }
}
