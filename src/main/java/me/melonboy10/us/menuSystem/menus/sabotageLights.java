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

import java.util.*;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class sabotageLights extends Menu {

    public sabotageLights(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static HashMap<Integer, Integer> lightsOn = new HashMap<>();
    static ArrayList<sabotageLights> menus = new ArrayList<>();

    public static void reset() {
        lightsOn.clear();
        menus.clear();
    }

    @Override
    public String getMenuName() {
        return "Lights";
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
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "oon");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "oof");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(slot, lime);

            lightsOn.put(slot, 0);
            updateGui();
        } else if (clickedItem.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(slot, green);

            lightsOn.remove(slot);
            updateGui();
        }

        if (inventory.getItem(38).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                inventory.getItem(39).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                inventory.getItem(40).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                inventory.getItem(41).getType().equals(Material.LIME_STAINED_GLASS_PANE) &&
                inventory.getItem(42).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            new BukkitRunnable(){
                @Override
                public void run() {
                    for (int i = menus.size(); i > 0; i--) {
                        menus.get(i - 1).task.setComplete();
                        menus.get(i - 1).playerUtility.getOwner().closeInventory();
                    }
                    for (Player player1 : AmongUs.getPlayingPlayers()) {
                        for (Iterator<Task> iterator = AmongUs.getPlayerUtility(player1).getTasks().iterator(); iterator.hasNext(); ) {
                            Task task = iterator.next();
                            if (task.getTaskType().equals(Task.TaskType.LIGHTS_SABOTAGE)) {
                                iterator.remove();
                                task.setComplete();
                            }
                        }
                    }
                    sabotageMenu.lightsComplete();
                }
            }.runTaskLater(plugin, 10);

        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "");
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "");

        for (int i = 1; i < 52; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 7)
                i += 2;
        }

        for (int i = 11; i < 16; i++) {
            inventory.setItem(i, yellow);
            inventory.setItem(i + 18, white);
            inventory.setItem(i + 27, green);
        }

        if (lightsOn.size() == 0) {
            ArrayList<Integer> switches = new ArrayList<Integer>() {{
                add(38);
                add(39);
                add(40);
                add(41);
                add(42);
            }};
            Collections.shuffle(switches);

            for (int i = 0; i < (new Random()).nextInt(4); i++) {
                lightsOn.put(switches.get(i), 0);
            }
        }

        for (int i : lightsOn.keySet()) {
            inventory.setItem(i, lime);
        }

        setFillerGlass();

        menus.add(this);
    }

    void updateGui() {
        ItemStack lime = makeItem(Material.LIME_STAINED_GLASS_PANE, "oon");
        ItemStack green = makeItem(Material.GREEN_STAINED_GLASS_PANE, "oof");

        for (sabotageLights menu : menus) {
            menu.inventory.setItem(38, green);
            menu.inventory.setItem(39, green);
            menu.inventory.setItem(40, green);
            menu.inventory.setItem(41, green);
            menu.inventory.setItem(42, green);
            for (int i : lightsOn.keySet()) {
                menu.inventory.setItem(i, lime);
            }
        }
    }
}
