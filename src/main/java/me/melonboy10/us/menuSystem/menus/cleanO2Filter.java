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

public class cleanO2Filter extends Menu {

    public cleanO2Filter(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Clean 02 Filter";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");

        switch (slot) {
            case 13:
            case 14:
            case 22:
                inventory.setItem(13, blue);
                inventory.setItem(14, blue);
                inventory.setItem(22, blue);
                inventory.setItem(19, lime);
                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                break;
            case 25:
            case 34:
            case 35:
                inventory.setItem(25, blue);
                inventory.setItem(34, blue);
                inventory.setItem(35, blue);
                inventory.setItem(28, lime);
                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                break;
            case 41:
            case 50:
            case 51:
                inventory.setItem(41, blue);
                inventory.setItem(50, blue);
                inventory.setItem(51, blue);
                inventory.setItem(37, lime);
                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                break;
        }

        if (inventory.getItem(13).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) && inventory.getItem(25).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) && inventory.getItem(41).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.closeInventory();
                    task.setComplete();
                }
            }.runTaskLater(plugin,10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "<-");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "im lef");
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");

        for (int i = 0; i < 6; i++) {
            inventory.setItem(i * 9, black);
            inventory.setItem(i * 9 + 1, black);
            inventory.setItem(i * 9 + 2, black);
        }
        inventory.setItem(20, white);
        inventory.setItem(29, white);

        inventory.setItem(13, green);
        inventory.setItem(14, green);
        inventory.setItem(22, green);
        inventory.setItem(25, green);
        inventory.setItem(34, green);
        inventory.setItem(35, green);
        inventory.setItem(41, green);
        inventory.setItem(50, green);
        inventory.setItem(51, green);

        inventory.setItem(10, lime);

        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, blue);
            }
        }

        setFillerGlass();
    }
}
