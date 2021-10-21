package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class customizationMenu extends Menu {

    public customizationMenu(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    public static ArrayList<AmongUs.Colors> takenColors = new ArrayList<>();
    static ArrayList<customizationMenu> menus = new ArrayList<>();

    @Override
    public String getMenuName() {
        return "Color Select";
    }

    @Override
    public int getSlots() {
        return 54 - 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        player.playSound(player.getLocation(), "minecraft:generic", 1, 1);

        switch (clickedItem.getType()) {
            case RED_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.RED, player);
                break;
            case ORANGE_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.ORANGE, player);
                break;
            case YELLOW_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.YELLOW, player);
                break;
            case GREEN_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.GREEN, player);
                break;
            case LIME_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.LIME, player);
                break;
            case LIGHT_BLUE_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.LIGHT_BLUE, player);
                break;
            case BLUE_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.BLUE, player);
                break;
            case CYAN_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.CYAN, player);
                break;
            case MAGENTA_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.MAGENTA, player);
                break;
            case PURPLE_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.PURPLE, player);
                break;
            case PINK_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.PINK, player);
                break;
            case WHITE_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.WHITE, player);
                break;
            case LIGHT_GRAY_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.LIGHT_GRAY, player);
                break;
            case GRAY_STAINED_GLASS_PANE:
                AmongUs.setPlayerColor(AmongUs.Colors.GRAY, player);
                break;
        }

        for (customizationMenu menu : menus) {
            menu.updateGUI();
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
    }

    @Override
    public void setMenuItems() {
        updateGUI();

        menus.add(this);
    }

    void updateGUI() {
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "RED");
        ItemStack orange = makeItem(Material.ORANGE_STAINED_GLASS_PANE, "ORANGE");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "YELLOW");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "GREEN");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "LIME");
        ItemStack light_blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "LIGHT BLUE");
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "BLUE");
        ItemStack cyan = makeItem(Material.CYAN_STAINED_GLASS_PANE, "CYAN");
        ItemStack magenta = makeItem(Material.MAGENTA_STAINED_GLASS_PANE, "MAGENTA");
        ItemStack purple = makeItem(Material.PURPLE_STAINED_GLASS_PANE, "PURPLE");
        ItemStack pink = makeItem(Material.PINK_STAINED_GLASS_PANE, "PINK");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "WHITE");
        ItemStack light_gray = makeItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "LIGHT GRAY");
        ItemStack gray = makeItem(Material.GRAY_STAINED_GLASS_PANE, "GRAY");
        ItemStack taken = makeItem(Material.BARRIER, "Taken");

        if(takenColors.contains(AmongUs.Colors.RED)) {
            inventory.setItem(2, taken);
        } else {
            inventory.setItem(2, red);
        }
        if(takenColors.contains(AmongUs.Colors.ORANGE)) {
            inventory.setItem(3, taken);
        } else {
            inventory.setItem(3, orange);
        }
        if(takenColors.contains(AmongUs.Colors.YELLOW)) {
            inventory.setItem(4, taken);
        } else {
            inventory.setItem(4, yellow);
        }
        if(takenColors.contains(AmongUs.Colors.GREEN)) {
            inventory.setItem(5, taken);
        } else {
            inventory.setItem(5, green);
        }
        if(takenColors.contains(AmongUs.Colors.LIME)) {
            inventory.setItem(6, taken);
        } else {
            inventory.setItem(6, lime);
        }
        if(takenColors.contains(AmongUs.Colors.LIGHT_BLUE)) {
            inventory.setItem(11, taken);
        } else {
            inventory.setItem(11, light_blue);
        }
        if(takenColors.contains(AmongUs.Colors.BLUE)) {
            inventory.setItem(12, taken);
        } else {
            inventory.setItem(12, blue);
        }
        if(takenColors.contains(AmongUs.Colors.CYAN)) {
            inventory.setItem(13, taken);
        } else {
            inventory.setItem(13, cyan);
        }
        if(takenColors.contains(AmongUs.Colors.MAGENTA)) {
            inventory.setItem(14, taken);
        } else {
            inventory.setItem(14, magenta);
        }
        if(takenColors.contains(AmongUs.Colors.PURPLE)) {
            inventory.setItem(15, taken);
        } else {
            inventory.setItem(15, purple);
        }
        if(takenColors.contains(AmongUs.Colors.PINK)) {
            inventory.setItem(20, taken);
        } else {
            inventory.setItem(20, pink);
        }
        if(takenColors.contains(AmongUs.Colors.WHITE)) {
            inventory.setItem(21, taken);
        } else {
            inventory.setItem(21, white);
        }
        if(takenColors.contains(AmongUs.Colors.LIGHT_GRAY)) {
            inventory.setItem(22, taken);
        } else {
            inventory.setItem(22, light_gray);
        }
        if(takenColors.contains(AmongUs.Colors.GRAY)) {
            inventory.setItem(23, taken);
        } else {
            inventory.setItem(23, gray);
        }
    }
}
