package me.melonboy10.us.menuSystem.menus;

import com.sun.media.sound.RIFFReader;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class alignEngineOutput extends Menu {

    public alignEngineOutput(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Align Engine Output";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, " ");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(25, fillerGlass);

            new BukkitRunnable() {
                @Override
                public void run() {
                    inventory.setItem(14, green);
                    inventory.setItem(23, green);
                    inventory.setItem(32, green);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            inventory.setItem(13, green);
                            inventory.setItem(22, green);
                            inventory.setItem(31, green);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    inventory.setItem(12, green);
                                    inventory.setItem(21, green);
                                    inventory.setItem(30, green);

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            inventory.setItem(20, green);

                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    inventory.setItem(18, green);

                                                    new BukkitRunnable() {
                                                        @Override
                                                        public void run() {
                                                            player.closeInventory();
                                                            task.setComplete();
                                                        }
                                                    }.runTaskLater(plugin, 4);
                                                }
                                            }.runTaskLater(plugin, 4);
                                        }
                                    }.runTaskLater(plugin, 4);
                                }
                            }.runTaskLater(plugin, 4);
                        }
                    }.runTaskLater(plugin, 4);
                }
            }.runTaskLater(plugin, 4);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "X");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, black);
            inventory.setItem(i + 36, black);
        }

        inventory.setItem(15, black);
        inventory.setItem(17, black);
        inventory.setItem(24, black);
        inventory.setItem(26, black);
        inventory.setItem(33, black);
        inventory.setItem(35, black);

        inventory.setItem(25, white);

        inventory.setItem(18, red);
        inventory.setItem(20, red);
        for (int i = 12; i < 33; i++) {
            inventory.setItem(i, red);
            if (i % 9 == 5)
                i += 6;
        }

        setFillerGlass();
    }
}
