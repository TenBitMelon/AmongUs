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

import java.util.Iterator;

public class sabotageO2 extends Menu {

    public sabotageO2(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static int combo;
    static boolean Done1 = false;
    static boolean Done2 = false;
    int clickCount = 0;

    public static void reset() {
        combo = 0;
        Done1 = false;
        Done2 = false;
    }

    @Override
    public String getMenuName() {
        return "Fix Oxygen";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            if (clickedItem.getAmount() > 1) {
                if (clickCount < 3) {
                    blue.setAmount(clickedItem.getAmount());
                    inventory.setItem(clickCount + 3, blue);
                    clickCount++;

                    if (inventory.getItem(3).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) && inventory.getItem(4).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) && inventory.getItem(5).getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)) {
                        if (combo == (inventory.getItem(3).getAmount() * 100) + (inventory.getItem(4).getAmount() * 10) + (inventory.getItem(5).getAmount())) {
                            if (task.getTaskType().equals(Task.TaskType.O2_SABOTAGE_1)) {
                                Done1 = true;
                                player.closeInventory();
                                for (Player player1 : AmongUs.getPlayingPlayers()) {
                                    for (Iterator<Task> iterator = AmongUs.getPlayerUtility(player1).getTasks().iterator(); iterator.hasNext(); ) {
                                        Task task = iterator.next();
                                        if (task.getTaskType().equals(Task.TaskType.O2_SABOTAGE_1)) {
                                            iterator.remove();
                                            task.setComplete();
                                        }
                                    }
                                }
                            } else if (task.getTaskType().equals(Task.TaskType.O2_SABOTAGE_2)) {
                                Done2 = true;
                                player.closeInventory();
                                for (Player player1 : AmongUs.getPlayingPlayers()) {
                                    for (Iterator<Task> iterator = AmongUs.getPlayerUtility(player1).getTasks().iterator(); iterator.hasNext(); ) {
                                        Task task = iterator.next();
                                        if (task.getTaskType().equals(Task.TaskType.O2_SABOTAGE_2)) {
                                            iterator.remove();
                                            task.setComplete();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            clickCount = 0;
            inventory.setItem(3, black);
            inventory.setItem(4, black);
            inventory.setItem(5, black);
        }

        if (Done1 && Done2) {
            player.closeInventory();
            sabotageMenu.o2Complete();
            task.setComplete();
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
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, String.valueOf(combo));

        for (int i = 2; i < 52; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 6)
                i += 4;
        }

        inventory.setItem(12, AmongUs.createCustomItem(Material.GRAY_STAINED_GLASS_PANE, "1", 2));
        inventory.setItem(13, makeItem(Material.GRAY_STAINED_GLASS_PANE, "2", 2));
        inventory.setItem(14, makeItem(Material.GRAY_STAINED_GLASS_PANE, "3", 3));
        inventory.setItem(21, makeItem(Material.GRAY_STAINED_GLASS_PANE, "4", 4));
        inventory.setItem(22, makeItem(Material.GRAY_STAINED_GLASS_PANE, "5", 5));
        inventory.setItem(23, makeItem(Material.GRAY_STAINED_GLASS_PANE, "6", 6));
        inventory.setItem(30, makeItem(Material.GRAY_STAINED_GLASS_PANE, "7", 7));
        inventory.setItem(31, makeItem(Material.GRAY_STAINED_GLASS_PANE, "8", 8));
        inventory.setItem(32, makeItem(Material.GRAY_STAINED_GLASS_PANE, "9", 9));
        inventory.setItem(40, AmongUs.createCustomItem(Material.GRAY_STAINED_GLASS_PANE, "0", 1));

        inventory.setItem(15, yellow);
        inventory.setItem(16, yellow);
        inventory.setItem(24, yellow);
        inventory.setItem(25, yellow);

        inventory.setItem(39, red);
        inventory.setItem(41, green);

        setFillerGlass();
    }

    public static void generateCombo() {
        do {
            combo = (int) Math.floor(Math.random() * (999 - 100 + 1) + 100);
        } while (String.valueOf(combo).contains("0") || String.valueOf(combo).contains("1"));
    }
}
