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

import java.util.Random;

import static me.melonboy10.us.utilities.AmongUs.plugin;

public class divertPowerMain extends Menu {

    public divertPowerMain(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Divert Power";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack yellow = makeItem(Material.YELLOW_STAINED_GLASS_PANE, "zap");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.PINK_STAINED_GLASS_PANE)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(event.getSlot() - 9, yellow);
            inventory.setItem(event.getSlot() - 18, yellow);
            inventory.setItem(event.getSlot() - 27, yellow);
            inventory.setItem(event.getSlot() - 36, yellow);

            new BukkitRunnable(){
                @Override
                public void run() {
                    player.closeInventory();
                    task.setComplete();
                }
            }.runTaskLater(plugin, 10);
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
        ItemStack brown = makeItem(Material.BROWN_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "");
        ItemStack pink = makeItem(Material.PINK_STAINED_GLASS_PANE, "");
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");

        for (int i = 1; i < 53; i++) {
            inventory.setItem(i, black);
            if(i % 9 == 7)
                i += 2;
        }

        for (int i = 2; i < 7; i++) {
            inventory.setItem(i, brown);
            inventory.setItem(i + 9, brown);
            inventory.setItem(i + 18, brown);
            inventory.setItem(i + 27, brown);
            inventory.setItem(i + 36, red);
        }

        int rand = new Random().nextInt(5);
        inventory.setItem(rand + 41, pink);

    }
}
