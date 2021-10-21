package me.melonboy10.us.menuSystem;

import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends me.melonboy10.us.menuSystem.Menu {

    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(boolean close) {
        if (page != 0) {
            inventory.setItem(48, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Left"));
        } else {
            inventory.setItem(48, fillerGlass);
        }

        if (close) {
            inventory.setItem(49, makeItem(Material.BARRIER, ChatColor.RED + "Close"));
        } else {
            inventory.setItem(49, makeItem(Material.ARROW, ChatColor.GREEN + "Go Back"));
        }

        inventory.setItem(50, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Right"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.fillerGlass);
            }
        }

        inventory.setItem(17, super.fillerGlass);
        inventory.setItem(18, super.fillerGlass);
        inventory.setItem(26, super.fillerGlass);
        inventory.setItem(27, super.fillerGlass);
        inventory.setItem(35, super.fillerGlass);
        inventory.setItem(36, super.fillerGlass);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.fillerGlass);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}