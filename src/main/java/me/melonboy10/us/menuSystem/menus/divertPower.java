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

public class divertPower extends Menu {

    public divertPower(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Divert Power";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        assert clickedItem != null;
        if (clickedItem.getType() == Material.WHITE_STAINED_GLASS_PANE) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(31, makeItem(Material.WHITE_STAINED_GLASS_PANE, ":)"));
            ItemStack wire = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
            inventory.setItem(15, wire);
            inventory.setItem(16, wire);
            inventory.setItem(24, wire);
            inventory.setItem(32, wire);
            inventory.setItem(33, wire);
            inventory.setItem(34, wire);
            inventory.setItem(42, wire);

            new BukkitRunnable(){
                @Override
                public void run() {
                    player.closeInventory();
                    task.setComplete();
                }
            }.runTaskLater(plugin, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack resistor = makeItem(Material.WHITE_STAINED_GLASS_PANE, "Rotate");
        ItemStack litWire = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
        ItemStack wire = makeItem(Material.BROWN_STAINED_GLASS_PANE, "");

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

        inventory.setItem(11, litWire);
        inventory.setItem(19, litWire);
        inventory.setItem(20, litWire);
        inventory.setItem(29, litWire);
        inventory.setItem(30, litWire);
        inventory.setItem(37, litWire);
        inventory.setItem(38, litWire);

        inventory.setItem(15, wire);
        inventory.setItem(16, wire);
        inventory.setItem(24, wire);
        inventory.setItem(32, wire);
        inventory.setItem(33, wire);
        inventory.setItem(34, wire);
        inventory.setItem(42, wire);

        inventory.setItem(31, resistor);

        setFillerGlass();
    }
}
