package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class theButton extends Menu {

    public theButton(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static int cooldown = 0;
    static ArrayList<theButton> menus = new ArrayList<>();
    static BukkitTask runnable = null;

    public static void reset() {
        cooldown = 0;
        if(runnable != null)
            runnable.cancel();
        runnable = null;
        menus.clear();
    }

    @Override
    public String getMenuName() {
        return "Emergency Button";
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
        if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            if (playerUtility.getEmergencyMeetings() > 0) {
                playerUtility.setEmergencyMeetings(playerUtility.getEmergencyMeetings() - 1);
                AmongUs.callMeeting(playerUtility.getColor(), false);
            }
        }
    }

    public static void startCooldown() {
        runnable = new BukkitRunnable() {
            int t = AmongUs.emergencyCooldown;

            @Override
            public void run() {
                ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, ChatColor.RED + "" + cooldown);

                for (theButton menu : menus) {
                    menu.inventory.setItem(21, blue);
                    menu.inventory.setItem(22, blue);
                    menu.inventory.setItem(23, blue);
                    menu.inventory.setItem(30, blue);
                    menu.inventory.setItem(31, blue);
                    menu.inventory.setItem(32, blue);
                }

                t--;
                cooldown = t;

                if (t <= 0) {
                    for (theButton menu : menus) {
                        menu.playerUtility.getOwner().closeInventory();
                        new theButton(menu.playerUtility, menu.task).open();
                    }
                    cancel();
                }
            }
        }.runTaskTimer(AmongUs.plugin, 0, 20);
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
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
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "You have " + ChatColor.RED + playerUtility.getEmergencyMeetings() + ChatColor.WHITE + " Emergency Meetings Left");
        ItemStack blueBlank = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "");
        ItemStack darkblue = makeItem(Material.BLUE_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "You have " + ChatColor.RED + playerUtility.getEmergencyMeetings() + ChatColor.WHITE + " Emergency Meetings Left");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
        ItemStack orange = makeItem(Material.ORANGE_STAINED_GLASS_PANE, "");

        for (int i = 1; i < 53; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 7)
                i += 2;
        }

        if (sabotageMenu.checkSabotages() || cooldown > 0) {
            inventory.setItem(11, yellow);
            inventory.setItem(13, yellow);
            inventory.setItem(15, yellow);
            inventory.setItem(29, yellow);
            inventory.setItem(33, yellow);
            inventory.setItem(39, yellow);
            inventory.setItem(41, yellow);
            inventory.setItem(12, orange);
            inventory.setItem(14, orange);
            inventory.setItem(20, orange);
            inventory.setItem(24, orange);
            inventory.setItem(38, orange);
            inventory.setItem(40, orange);
            inventory.setItem(42, orange);

            inventory.setItem(21, blue);
            inventory.setItem(22, blue);
            inventory.setItem(23, blue);
            inventory.setItem(30, blue);
            inventory.setItem(31, blue);
            inventory.setItem(32, blue);
        } else {
            inventory.setItem(11, yellow);
            inventory.setItem(13, yellow);
            inventory.setItem(29, yellow);
            inventory.setItem(39, yellow);
            inventory.setItem(12, orange);
            inventory.setItem(20, orange);
            inventory.setItem(38, orange);
            inventory.setItem(40, orange);

            inventory.setItem(21, red);
            inventory.setItem(22, red);
            inventory.setItem(23, red);
            inventory.setItem(30, red);
            inventory.setItem(31, red);
            inventory.setItem(32, red);

            inventory.setItem(14, blueBlank);
            inventory.setItem(15, blueBlank);
            inventory.setItem(16, darkblue);
            inventory.setItem(24, blueBlank);
            inventory.setItem(25, darkblue);
            inventory.setItem(33, blueBlank);
            inventory.setItem(34, darkblue);
            inventory.setItem(41, blueBlank);
            inventory.setItem(42, blueBlank);
            inventory.setItem(43, darkblue);
        }

        setFillerGlass();

        menus.add(this);
    }
}
