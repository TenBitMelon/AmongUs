package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class emptyGarbageMain extends Menu {

    public emptyGarbageMain(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    BukkitTask runnable;
    boolean active;
    static ArrayList<ItemStack> itemList = new ArrayList<ItemStack>() {{
        add(makeItem(Material.STONE, ""));
        add(makeItem(Material.JUNGLE_PLANKS, ""));
        add(makeItem(Material.RED_BANNER, ""));
        add(makeItem(Material.ENDER_CHEST, ""));
        add(makeItem(Material.BROWN_GLAZED_TERRACOTTA, ""));
        add(makeItem(Material.CHEST, ""));
        add(makeItem(Material.BLACKSTONE_STAIRS, ""));
        add(makeItem(Material.BLACK_GLAZED_TERRACOTTA, ""));
        add(makeItem(Material.NOTE_BLOCK, ""));
        add(makeItem(Material.STICKY_PISTON, ""));
        add(makeItem(Material.COMPOSTER, ""));
        add(makeItem(Material.DEAD_BRAIN_CORAL, ""));
        add(makeItem(Material.PACKED_ICE, ""));
        add(makeItem(Material.LEVER, ""));
        add(makeItem(Material.GLOWSTONE, ""));
        add(makeItem(Material.SMITHING_TABLE, ""));
        add(makeItem(Material.ARMOR_STAND, ""));
        add(makeItem(Material.LODESTONE, ""));
        add(makeItem(Material.HOPPER, ""));
        add(makeItem(Material.BROWN_MUSHROOM_BLOCK, ""));
        add(makeItem(Material.CRIMSON_TRAPDOOR, ""));
        add(makeItem(Material.WARPED_FENCE, ""));
        add(makeItem(Material.REDSTONE_LAMP, ""));
        add(makeItem(Material.END_PORTAL_FRAME, ""));
        add(makeItem(Material.BRICK_STAIRS, ""));
        add(makeItem(Material.STONE_SLAB, ""));
        add(makeItem(Material.MUSHROOM_STEM, ""));
        add(makeItem(Material.FURNACE, ""));
        add(makeItem(Material.SMOOTH_STONE_SLAB, ""));
        add(makeItem(Material.OBSERVER, ""));
        add(makeItem(Material.PRISMARINE_BRICK_SLAB, ""));
        add(makeItem(Material.BARREL, ""));
        add(makeItem(Material.BEE_NEST, ""));
        add(makeItem(Material.PAINTING, ""));
        add(makeItem(Material.WHITE_BED, ""));
        add(makeItem(Material.IRON_TRAPDOOR, ""));
        add(makeItem(Material.DARK_PRISMARINE_SLAB, ""));
        add(makeItem(Material.PRISMARINE_WALL, ""));
        add(makeItem(Material.SHULKER_BOX, ""));
        add(makeItem(Material.PURPUR_PILLAR, ""));
    }};

    @Override
    public String getMenuName() {
        return "Empty Garbage";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");

        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE) && !active) {
            active = true;

            inventory.setItem(15, black);
            inventory.setItem(16, black);
            inventory.setItem(17, black);
            inventory.setItem(25, black);

            inventory.setItem(34, white);
            inventory.setItem(42, white);
            inventory.setItem(43, white);
            inventory.setItem(44, white);

            runnable = new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t++;

                    for (int i = 41; i > 18; i--) {
                        inventory.setItem(i, inventory.getItem(i - 9));
                        if (i % 9 == 8)
                            i -= 3;
                    }

                    if (t >= 4) {
                        cancel();

                        World world = AmongUs.world;
                        Collections.shuffle(itemList);

                        for (int i = 0; i < 30; i++) {
                            Item item = world.dropItemNaturally(new Location(world, 3.00, 5.5, -61.00), itemList.get(i));
                            itemList.get(i).setAmount((new Random()).nextInt(60));
                            item.setGravity(false);
                            item.setTicksLived(5900);
                            item.setVelocity(new Vector(((double) (new Random()).nextInt(30) - 15) / 100, 0, (double) (new Random()).nextInt(50) / 1000));
                        }

                        Bukkit.getScheduler().runTaskLater(AmongUs.plugin, () -> {
                            player.closeInventory();
                            task.setComplete();
                        }, 10);
                    }
                }
            }.runTaskTimer(AmongUs.plugin, 1, 10);
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        runnable.cancel();
    }

    @Override
    public void setMenuItems() {
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
        ItemStack red = makeItem(Material.RED_STAINED_GLASS_PANE, " ");
        ItemStack green = makeItem(Material.LIME_STAINED_GLASS_PANE, " ");
        ItemStack blue = makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");

        for (int i = 6; i < 54; i++) {
            inventory.setItem(i, black);
            if (i % 9 == 0)
                i += 5;
        }

        for (int i = 0; i < 8; i++) {
            inventory.setItem(i, black);
        }

        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, black);
        }

        inventory.setItem(15, white);
        inventory.setItem(16, white);
        inventory.setItem(17, white);
        inventory.setItem(25, white);

        inventory.setItem(19, red);
        inventory.setItem(22, green);
        inventory.setItem(28, blue);
        inventory.setItem(29, red);
        inventory.setItem(30, green);
        inventory.setItem(31, red);
        inventory.setItem(37, green);
        inventory.setItem(38, red);
        inventory.setItem(39, green);
        inventory.setItem(40, blue);
        inventory.setItem(41, red);

        setFillerGlass();
    }
}
