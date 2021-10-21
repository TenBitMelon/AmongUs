package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class submitScan extends Menu {

    public submitScan(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;
    Slime slime;

    @Override
    public String getMenuName() {
        return "Submit Scan";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem.getType().equals(Material.OAK_SIGN)) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);

            inventory.setItem(22, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "Scanning ..."));
            slime = (Slime) player.getWorld().spawnEntity(AmongUs.getPlayerUtility(player).getSheep().getLocation(), EntityType.SLIME);
            slime.setSize(2);
            slime.setGravity(false);
            slime.setAI(false);

            runnable = new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t++;

                    int spot = (t) * (6) / (20) + 1;
                    inventory.setItem(spot + 36, makeItem(Material.LIME_STAINED_GLASS_PANE, String.valueOf((double) t / 2)));

                    for (int i = 0; i < 9; i++) {
                        Random rand = new Random();
                        inventory.setItem(i, makeItem(Material.BLACK_STAINED_GLASS_PANE, String.valueOf(i)));
                        for (int j = 0; j < rand.nextInt(62); j++) {
                            inventory.addItem(makeItem(Material.BLACK_STAINED_GLASS_PANE, String.valueOf(i)));
                        }
                    }

                    if (slime.getLocation().getY() >= 6) {
                        slime.teleport(slime.getLocation().add(0, -2, 0));
                    } else {
                        slime.teleport(slime.getLocation().add(0, 0.2, 0));
                    }

                    if (t >= 20) {
                        cancel();

                        Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                            player.closeInventory();
                            task.setComplete();
                        }, 10);
                    }
                }

                @Override
                public void cancel() {
                    slime.remove();
                    super.cancel();
                }
            }.runTaskTimer(AmongUs.plugin, 1, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        runnable.cancel();
        slime.remove();
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack middle = makeItem(Material.OAK_SIGN, "Start Scan");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, black);
        }
        for (int i = 37; i < 44; i++) {
            inventory.setItem(i, black);
        }
        inventory.setItem(22, middle);

        setFillerGlass();
    }
}
