package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class primeShields extends Menu {

    public primeShields(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Prime Shields";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (Objects.requireNonNull(clickedItem).getType() == Material.RED_STAINED_GLASS_PANE) {
            player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
            inventory.setItem(event.getSlot(), makeItem(Material.WHITE_STAINED_GLASS_PANE, ""));
        }

        if (inventory.getItem(12).getType().equals(Material.WHITE_STAINED_GLASS_PANE) && inventory.getItem(14).getType().equals(Material.WHITE_STAINED_GLASS_PANE) && inventory.getItem(22).getType().equals(Material.WHITE_STAINED_GLASS_PANE) && inventory.getItem(30).getType().equals(Material.WHITE_STAINED_GLASS_PANE) && inventory.getItem(32).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                if (AmongUs.visualTasks) {
                    World world = AmongUs.world;
                    BlockData data = world.getBlockAt(-39, 5, 19).getBlockData();
                    world.getBlockAt(23, 5, -70).setBlockData(data);
                    world.getBlockAt(23, 4, -70).setBlockData(data);
                    world.getBlockAt(25, 5, -72).setBlockData(data);
                    world.getBlockAt(25, 4, -72).setBlockData(data);
                }
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
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, "X");

        List<Integer> numbers = new ArrayList<Integer>() {{
            add(12);
            add(14);
            add(22);
            add(30);
            add(32);
        }};
        Collections.shuffle(numbers);

        inventory.setItem(numbers.get(0), white);
        inventory.setItem(numbers.get(1), white);
        inventory.setItem(numbers.get(2), red);
        inventory.setItem(numbers.get(3), red);
        inventory.setItem(numbers.get(4), red);

        setFillerGlass();
    }
}
