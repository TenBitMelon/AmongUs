package me.melonboy10.us.menuSystem;

import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {

    protected PlayerUtility playerUtility;
    protected Inventory inventory;
    protected ItemStack fillerGlass = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    protected Task task;

    public Menu(PlayerUtility playerUtility, Task task) {
        this.playerUtility = playerUtility;
        this.task = task;
    }

        public abstract String getMenuName();

        public abstract int getSlots();

        public abstract void handleMenu(InventoryClickEvent event);

        public abstract void closeMenu(InventoryCloseEvent event);

        public abstract void setMenuItems();

        public void open() {

            inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

            this.setMenuItems();

            playerUtility.getOwner().openInventory(inventory);
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }

        public void setFillerGlass() {
            for (int i = 0; i < getSlots(); i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, fillerGlass);
                }
            }
        }

    public static ItemStack makeItem(Material material, String name, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + name);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack makeItem(Material material, int amount, String name, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + name);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        item.setAmount(amount);

        return item;
    }

    public static ItemStack makeItem(Material material, String name, int amount) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + name);

        item.setItemMeta(itemMeta);

        item.setAmount(amount);

        return item;
    }

    public static ItemStack makeItem(Material material, String name, int custommodeldata, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + name);
        itemMeta.setCustomModelData(custommodeldata);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static void reset() {

    }

}
