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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;

public class sabotageReactor extends Menu {

    public sabotageReactor(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static boolean button1;
    static boolean button2;

    static ArrayList<sabotageReactor> menus = new ArrayList<>();

    public static void reset() {
        button1 = false;
        button2 = false;

        menus.clear();
    }

    @Override
    public String getMenuName() {
        return "Fix Reactor";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, ":)");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, ":(");

        if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            for (int i = 12; i < 42; i++) {
                inventory.setItem(i, blue);
                if (i % 9 == 5)
                    i += 6;
            }
            if (button1) {
                button2 = true;
            } else {
                button1 = true;
            }
            player.sendMessage(String.valueOf(menus.size()));
            player.sendMessage(String.valueOf(button2));

            if (button2) {
                for (int i = menus.size(); i > 0; i--) {
                    menus.get(i - 1).task.setComplete();
                    menus.get(i - 1).playerUtility.getOwner().closeInventory();
                }

                for (Player player1 : AmongUs.getPlayingPlayers()) {
                    for (Iterator<Task> iterator = AmongUs.getPlayerUtility(player1).getTasks().iterator(); iterator.hasNext(); ) {
                        Task task = iterator.next();
                        if (task.getTaskType().equals(Task.TaskType.REACTOR_SABOTAGE)) {
                            iterator.remove();
                            task.setComplete();
                        }
                    }
                }

                button1 = false;
                button2 = false;
                sabotageMenu.reactorComplete();
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!button1) {
                            button2 = false;
                        } else {
                            button1 = false;
                        }

                        for (int i = 12; i < 42; i++) {
                            inventory.setItem(i, red);
                            if (i % 9 == 5)
                                i += 6;
                        }
                    }
                }.runTaskLater(AmongUs.plugin, 20);
            }
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");

        for (int i = 2; i < 52; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 6)
                i += 4;
        }

        for (int i = 12; i < 42; i++) {
            inventory.setItem(i, red);
            if (i % 9 == 5)
                i += 6;
        }

        setFillerGlass();

        menus.add(this);
    }
}
