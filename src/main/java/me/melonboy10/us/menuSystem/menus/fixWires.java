package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class fixWires extends Menu {

    private final int redSlot;
    private final int yellowSlot;
    private final int blueSlot;
    private final int magentaSlot;

    public fixWires(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);

        List<Integer> numbers = new ArrayList<Integer>() {{
            add(10);
            add(19);
            add(28);
            add(37);
        }};
        Collections.shuffle(numbers);
        redSlot = numbers.get(0);
        yellowSlot = numbers.get(1);
        blueSlot = numbers.get(2);
        magentaSlot = numbers.get(3);
    }

    @Override
    public String getMenuName() {
        return "Fix Wires";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack lastClickedItem = event.getClickedInventory().getItem(0);
        ItemStack finishedItem = event.getClickedInventory().getItem(1);
        boolean complete = false;

        if (clickedItem != null) {
            switch (clickedItem.getType()) {
                case RED_STAINED_GLASS_PANE:
                    complete = checkClickedItem(Material.RED_STAINED_GLASS_PANE, ItemFlag.HIDE_DESTROYS, lastClickedItem, finishedItem);
                    break;
                case YELLOW_STAINED_GLASS_PANE:
                    complete = checkClickedItem(Material.YELLOW_STAINED_GLASS_PANE, ItemFlag.HIDE_ATTRIBUTES, lastClickedItem, finishedItem);
                    break;
                case BLUE_STAINED_GLASS_PANE:
                    complete = checkClickedItem(Material.BLUE_STAINED_GLASS_PANE, ItemFlag.HIDE_PLACED_ON, lastClickedItem, finishedItem);
                    break;
                case MAGENTA_STAINED_GLASS_PANE:
                    complete = checkClickedItem(Material.MAGENTA_STAINED_GLASS_PANE, ItemFlag.HIDE_UNBREAKABLE, lastClickedItem, finishedItem);
                    break;
            }
        }

        if (complete) {
            Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                player.closeInventory();
                task.setComplete();
            }, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Red Wire"); //hide destroy
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW + "Yellow Wire"); //hide attributes
        ItemStack blue = makeItem(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Blue Wire"); //hide placed
        ItemStack magenta = makeItem(Material.MAGENTA_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE + "Pink Wire"); //hide unbreakable
        ItemStack lastClickedData = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
        ItemStack finishedData = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        inventory.setItem(0, lastClickedData);
        inventory.setItem(1, finishedData);

        inventory.setItem(redSlot, red);
        inventory.setItem(redSlot + 6, red);
        inventory.setItem(yellowSlot, yellow);
        inventory.setItem(yellowSlot + 6, yellow);
        inventory.setItem(blueSlot, blue);
        inventory.setItem(blueSlot + 6, blue);
        inventory.setItem(magentaSlot, magenta);
        inventory.setItem(magentaSlot + 6, magenta);

        setFillerGlass();
    }

    private boolean checkClickedItem(Material material, ItemFlag flag, ItemStack lastClickedData, ItemStack finishedData) {
        ItemMeta lastClickedMeta = lastClickedData.getItemMeta();
        ItemMeta finishedMeta = finishedData.getItemMeta();

        if (lastClickedMeta.getItemFlags().contains(flag)) {
            finishedMeta.addItemFlags(flag);
            switch (material) {
                case RED_STAINED_GLASS_PANE:
                    inventory.setItem(redSlot + 1, new ItemStack(material));
                    inventory.setItem(redSlot + 2, new ItemStack(material));
                    inventory.setItem(redSlot + 3, new ItemStack(material));
                    inventory.setItem(redSlot + 4, new ItemStack(material));
                    inventory.setItem(redSlot + 5, new ItemStack(material));
                    break;
                case YELLOW_STAINED_GLASS_PANE:
                    inventory.setItem(yellowSlot + 1, new ItemStack(material));
                    inventory.setItem(yellowSlot + 2, new ItemStack(material));
                    inventory.setItem(yellowSlot + 3, new ItemStack(material));
                    inventory.setItem(yellowSlot + 4, new ItemStack(material));
                    inventory.setItem(yellowSlot + 5, new ItemStack(material));
                    break;
                case BLUE_STAINED_GLASS_PANE:
                    inventory.setItem(blueSlot + 1, new ItemStack(material));
                    inventory.setItem(blueSlot + 2, new ItemStack(material));
                    inventory.setItem(blueSlot + 3, new ItemStack(material));
                    inventory.setItem(blueSlot + 4, new ItemStack(material));
                    inventory.setItem(blueSlot + 5, new ItemStack(material));
                    break;
                case MAGENTA_STAINED_GLASS_PANE:
                    inventory.setItem(magentaSlot + 1, new ItemStack(material));
                    inventory.setItem(magentaSlot + 2, new ItemStack(material));
                    inventory.setItem(magentaSlot + 3, new ItemStack(material));
                    inventory.setItem(magentaSlot + 4, new ItemStack(material));
                    inventory.setItem(magentaSlot + 5, new ItemStack(material));
                    break;
            }
        } else {
            lastClickedMeta.removeItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
            lastClickedMeta.addItemFlags(flag);
        }

        lastClickedData.setItemMeta(lastClickedMeta);
        finishedData.setItemMeta(finishedMeta);

        return finishedMeta.getItemFlags().contains(ItemFlag.HIDE_DESTROYS) && finishedMeta.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES) && finishedMeta.getItemFlags().contains(ItemFlag.HIDE_PLACED_ON) && finishedMeta.getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE);

    }
}
