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

import java.util.ArrayList;
import java.util.Collections;

public class unlockManifolds extends Menu {

    public unlockManifolds(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    int lastClicked = 0;
    ItemStack[] order;

    @Override
    public String getMenuName() {
        return "Unlock Manifolds";
    }

    @Override
    public int getSlots() {
        return 18;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        int slot = event.getSlot();

        if (clickedItem.getAmount() == lastClicked + 1) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(slot, makeItem(Material.LIME_STAINED_GLASS_PANE, String.valueOf(lastClicked + 1)));
            for (int i = 0; i < lastClicked; i++) {
                inventory.addItem(makeItem(Material.LIME_STAINED_GLASS_PANE, String.valueOf(lastClicked + 1)));
            }
            lastClicked++;
        } else {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setContents(order);
            lastClicked = 0;
        }

        if (
                inventory.getItem(2).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(3).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(4).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(5).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(6).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(11).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(12).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(13).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(14).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                        inventory.getItem(15).getType().equals(Material.LIME_STAINED_GLASS_PANE)
        ) {
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
        ItemStack one = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "1");
        one.setAmount(1);
        ItemStack two = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "2");
        two.setAmount(2);
        ItemStack three = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "3");
        three.setAmount(3);
        ItemStack four = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "4");
        four.setAmount(4);
        ItemStack five = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "5");
        five.setAmount(5);
        ItemStack six = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "6");
        six.setAmount(6);
        ItemStack seven = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "7");
        seven.setAmount(7);
        ItemStack eight = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "8");
        eight.setAmount(8);
        ItemStack nine = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "9");
        nine.setAmount(9);
        ItemStack ten = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "10");
        ten.setAmount(10);

        ArrayList<ItemStack> items = new ArrayList<ItemStack>() {{
            add(one);
            add(two);
            add(three);
            add(four);
            add(five);
            add(six);
            add(seven);
            add(eight);
            add(nine);
            add(ten);
        }};
        Collections.shuffle(items);

        inventory.setItem(0, fillerGlass);
        inventory.setItem(1, fillerGlass);
        inventory.setItem(7, fillerGlass);
        inventory.setItem(8, fillerGlass);
        inventory.setItem(9, fillerGlass);
        inventory.setItem(10, fillerGlass);
        inventory.setItem(16, fillerGlass);
        inventory.setItem(17, fillerGlass);

        for (ItemStack item : items) {
            inventory.addItem(item);
        }

        order = inventory.getContents();
    }
}
