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

import java.util.Random;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class cardSwipe extends Menu {

    public cardSwipe(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Card Swipe";
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
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "Card");
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack pink = makeItem(Material.PINK_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");

        switch (slot) {
            case 29:
            case 30:
            case 31:
            case 38:
            case 39:
            case 40:
                inventory.setItem(32, white);
                inventory.setItem(33, white);
                inventory.setItem(34, white);
                inventory.setItem(41, white);
                inventory.setItem(42, white);
                inventory.setItem(43, white);
                inventory.setItem(29, black);
                inventory.setItem(30, black);
                inventory.setItem(31, black);
                inventory.setItem(38, black);
                inventory.setItem(39, black);
                inventory.setItem(40, black);

                if ((new Random()).nextInt(3) == 1) {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    inventory.setItem(23, lime);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            task.setComplete();
                        }
                    }.runTaskLater(plugin, 4);
                } else {
                    player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                    inventory.setItem(24, pink);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            inventory.setItem(24, red);
                            inventory.setItem(29, white);
                            inventory.setItem(30, white);
                            inventory.setItem(31, white);
                            inventory.setItem(38, white);
                            inventory.setItem(39, white);
                            inventory.setItem(40, white);
                            inventory.setItem(32, black);
                            inventory.setItem(33, black);
                            inventory.setItem(34, black);
                            inventory.setItem(41, black);
                            inventory.setItem(42, black);
                            inventory.setItem(43, black);
                        }
                    }.runTaskLater(plugin,20);
                }
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "Card");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "");
        ItemStack light_blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");

        for (int i = 1; i < 53; i++) {
            inventory.setItem(i, black);
            if(i % 9 == 7)
                i += 2;
        }

        for (int i = 11; i < 17; i++) {
            inventory.setItem(i, light_blue);
            inventory.setItem(i + 9, blue);
        }

        inventory.setItem(23, green);
        inventory.setItem(24, red);

        inventory.setItem(29, white);
        inventory.setItem(30, white);
        inventory.setItem(31, white);
        inventory.setItem(38, white);
        inventory.setItem(39, white);
        inventory.setItem(40, white);

        setFillerGlass();
    }
}
