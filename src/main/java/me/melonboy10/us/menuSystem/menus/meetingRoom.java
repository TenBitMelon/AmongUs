package me.melonboy10.us.menuSystem.menus;

import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.nio.file.AccessMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static me.melonboy10.us.utilities.AmongUs.discussionTime;
import static me.melonboy10.us.utilities.AmongUs.world;

public class meetingRoom extends Menu {


    public meetingRoom(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    static final int[] playerHeadSlots = {1, 5, 10, 14, 19, 23, 28, 32, 37, 41, 46};
    HashMap<AmongUs.Colors, Integer> glassColor = new HashMap<AmongUs.Colors, Integer>() {{
        put(AmongUs.Colors.RED, 0);
        put(AmongUs.Colors.ORANGE, 1);
        put(AmongUs.Colors.YELLOW, 2);
        put(AmongUs.Colors.GREEN, 3);
        put(AmongUs.Colors.LIME, 4);
        put(AmongUs.Colors.BLUE, 5);
        put(AmongUs.Colors.LIGHT_BLUE, 6);
        put(AmongUs.Colors.CYAN, 7);
        put(AmongUs.Colors.PURPLE, 8);
        put(AmongUs.Colors.MAGENTA, 9);
        put(AmongUs.Colors.PINK, 10);
        put(AmongUs.Colors.WHITE, 11);
        put(AmongUs.Colors.GRAY, 12);
        put(AmongUs.Colors.LIGHT_GRAY, 13);
//        put(AmongUs.Colors.BLACK, Material.BLACK_STAINED_GLASS);
    }};
    static int timeLeft = -1;
    static int discTimeLeft = -1;
    static ArrayList<meetingRoom> menus = new ArrayList<>();
    static HashMap<Integer, Integer> votedSlots = new HashMap<>(); //who voted to who they voted for
    static HashMap<Integer, Integer> mostVoted = new HashMap<Integer, Integer>() {{
        put(1, 0);
        put(5, 0);
        put(10, 0);
        put(14, 0);
        put(19, 0);
        put(23, 0);
        put(28, 0);
        put(32, 0);
        put(37, 0);
        put(41, 0);
        put(46, 0);
    }}; //slot to vote count
    static boolean active;
    static BukkitTask runnable;
    int playerSlot;

    public static void reset() {
        active = false;
        if(runnable != null)
            runnable.cancel();
        runnable = null;
    }

    @Override
    public String getMenuName() {
        return "Meeting Room";
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
        player.playSound(player.getLocation(), "minecraft:vote", 1, 1);

        if (clickedItem != null) {
            switch (slot) {
                case 1:
                case 2:
                case 3:
                    voteButton(clickedItem, 0);
                    break;
                case 5:
                case 6:
                case 7:
                    voteButton(clickedItem, 1);
                    break;
                case 10:
                case 11:
                case 12:
                    voteButton(clickedItem, 2);
                    break;
                case 14:
                case 15:
                case 16:
                    voteButton(clickedItem, 3);
                    break;
                case 19:
                case 20:
                case 21:
                    voteButton(clickedItem, 4);
                    break;
                case 23:
                case 24:
                case 25:
                    voteButton(clickedItem, 5);
                    break;
                case 28:
                case 29:
                case 30:
                    voteButton(clickedItem, 6);
                    break;
                case 32:
                case 33:
                case 34:
                    voteButton(clickedItem, 7);
                    break;
                case 37:
                case 38:
                case 39:
                    voteButton(clickedItem, 8);
                    break;
                case 41:
                case 42:
                case 43:
                    voteButton(clickedItem, 9);
                    break;
                case 46:
                case 47:
                case 48:
                    voteButton(clickedItem, 10);
                    break;
            }
        }
    }

    void voteButton(ItemStack clickedItem, int slot) {
        ItemStack vote = makeItem(Material.LIME_STAINED_GLASS_PANE, "Vote");
        ItemStack cancel = makeItem(Material.RED_STAINED_GLASS_PANE, "Cancel");
        ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
        ItemStack gray = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");

        for (int playerHeadSlot : playerHeadSlots) {
            if (inventory.getItem(playerHeadSlot + 2).getType().equals(Material.WHITE_STAINED_GLASS_PANE) || inventory.getItem(playerHeadSlot + 1).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                inventory.setItem(playerHeadSlot + 1, white);
                inventory.setItem(playerHeadSlot + 2, white);
            }
        }
        inventory.setItem(47, gray);
        inventory.setItem(48, gray);

        if (clickedItem.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            inventory.setItem(playerSlot + 1, white);
            inventory.setItem(playerSlot + 2, AmongUs.createCustomItem(Material.PINK_STAINED_GLASS_PANE, "Voted", slot));
            votedSlots.put(playerSlot, playerHeadSlots[slot]);
            mostVoted.put(playerHeadSlots[slot], mostVoted.get(playerHeadSlots[slot]) + 1);
            updateInventories();
        } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            if (slot == 10) {
                inventory.setItem(47, gray);
                inventory.setItem(48, gray);
            } else {
                inventory.setItem(playerHeadSlots[slot] + 1, white);
                inventory.setItem(playerHeadSlots[slot] + 2, white);
            }
        } else if (!inventory.getItem(playerSlot + 2).getType().equals(Material.PINK_STAINED_GLASS_PANE) && !inventory.getItem(playerSlot + 2).getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
            inventory.setItem(playerHeadSlots[slot] + 1, vote);
            inventory.setItem(playerHeadSlots[slot] + 2, cancel);
        }
    }

    static void proceed() {
        ItemStack clock = AmongUs.createCustomItem(Material.CLOCK, ChatColor.GOLD + "Time Left", 0);

        runnable.cancel();
        timeLeft = votedSlots.size() + 2;
        runnable = new BukkitRunnable() {
            int t = timeLeft;

            @Override
            public void run() {
                for (meetingRoom room : menus) {
                    clock.setAmount(t);
                    room.inventory.setItem(52, clock);

                    for (Integer vote : mostVoted.keySet()) {
                        ItemStack item = room.inventory.getItem(vote);
//                        item.setAmount(mostVoted.get(vote));
//                        ItemMeta itemMeta = item.getItemMeta();
//                        itemMeta.setLore(Collections.singletonList(ChatColor.RED + "Votes: " + mostVoted.get(vote)));
//                        item.setItemMeta(itemMeta);

                        room.inventory.setItem(vote, item);
                    }
//                    for (Integer vote : mostVoted.keySet()) {
//                        ItemStack item = room.inventory.getItem(vote);
//                        ItemMeta itemMeta = item.getItemMeta();
//                        item.setAmount(mostVoted.get(vote));
//                        itemMeta.setLore(Collections.singletonList("Votes: " + item.getAmount()));
//                        item.setItemMeta(itemMeta);
//                        if(item.getAmount() == 0)
//                            item.setAmount(1);
//
//                        room.inventory.setItem(vote, item);
//                    }


                }

                if (timeLeft == t) {
                    timeLeft--;
                }
                t--;

                if (t <= 0) {
                    int highestPlayer = 0;
                    int secondPlayer = 0;

                    for (Map.Entry<Integer, Integer> player : mostVoted.entrySet()) {
                        if (player.getValue() > highestPlayer) {
                            highestPlayer = player.getKey();
                        }
                    }
                    for (Map.Entry<Integer, Integer> player : mostVoted.entrySet()) {
                        if (player.getKey() != highestPlayer) {
                            if (player.getValue() > secondPlayer) {
                                secondPlayer = player.getKey();
                            }
                        }
                    }

                    if (highestPlayer == 0) {
                        AmongUs.eject(null);
                    } else if (mostVoted.get(highestPlayer).equals(mostVoted.get(secondPlayer))) { //skip tie
                        AmongUs.eject(null);
                    } else if (highestPlayer == 46) { //skip via button
                        AmongUs.eject(null);
                    } else { //ejects player
                        ItemStack playerItem = menus.get(0).inventory.getItem(highestPlayer);
                        String playerName = playerItem.getItemMeta().getDisplayName().substring(2);
                        Player player = Bukkit.getPlayer(playerName);
                        AmongUs.eject(player);
                    }

                    for (Player player : AmongUs.getPlayingPlayers()) {
                        if(AmongUs.getPlayerUtility(player).isImpostor()) {
                            player.setCooldown(Material.DIAMOND_HOE, 20 * AmongUs.killCooldown + 160);
                            player.setCooldown(Material.FILLED_MAP, 20 * AmongUs.killCooldown + 160);
                        }
                    }

                    theButton.startCooldown();
                    cancel();
                }

            }

            @Override
            public void cancel() {

                active = false;
                for (int i = menus.size(); i > 0; i--) {
                    menus.get(i - 1).playerUtility.getOwner().closeInventory();
                }

                menus.clear();
                timeLeft = -1;
                votedSlots = new HashMap<>();
                mostVoted = new HashMap<Integer, Integer>() {{
                    put(1, 0);
                    put(5, 0);
                    put(10, 0);
                    put(14, 0);
                    put(19, 0);
                    put(23, 0);
                    put(28, 0);
                    put(32, 0);
                    put(37, 0);
                    put(41, 0);
                    put(46, 0);
                }};
                runnable = null;
                super.cancel();
            }
        }.runTaskTimer(AmongUs.plugin, 18, 20);
    }

    static void updateInventories() {
        final int[] playerHeadSlots = {1, 5, 10, 14, 19, 23, 28, 32, 37, 41};

        for (meetingRoom room : menus) {
            for (int slot : votedSlots.keySet()) {
                room.inventory.setItem(slot + 2, new ItemStack(Material.PINK_STAINED_GLASS_PANE));
            }
        }

        boolean voteComplete = true;
        for (meetingRoom room : menus) {
            for (int votedSlot : playerHeadSlots) {
                if (voteComplete) {
                    if (!room.inventory.getItem(votedSlot + 2).getType().equals(Material.PINK_STAINED_GLASS_PANE) && !room.inventory.getItem(votedSlot + 2).getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                        voteComplete = false;
                    }
                }
            }
        }

        if (voteComplete)
            proceed();

    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
        if (active) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new meetingRoom(AmongUs.getPlayerUtility((Player) event.getPlayer()), null).open();
                }
            }.runTaskLater(AmongUs.plugin, 2);
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < 5; i++) {
            inventory.setItem((i * 9) + 1, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            inventory.setItem((i * 9) + 2, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            inventory.setItem((i * 9) + 3, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            inventory.setItem((i * 9) + 5, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            inventory.setItem((i * 9) + 6, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
            inventory.setItem((i * 9) + 7, makeItem(Material.BLACK_STAINED_GLASS_PANE, ""));
        }
        for (int i = 0; i < AmongUs.getPlayingPlayers().size(); i++) {
            Player player = AmongUs.getPlayingPlayers().get(i);
            PlayerUtility playerUtility = AmongUs.getPlayerUtility(player);
            ItemStack white = makeItem(Material.WHITE_STAINED_GLASS_PANE, "");
            ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
            ItemStack item = ((Chest) world.getBlockAt(-48, 6, 22).getState()).getBlockInventory().getItem(glassColor.get(playerUtility.getColor()));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(AmongUs.colorToChat.get(playerUtility.getColor()) + playerUtility.getOwner().getDisplayName());
            item.setItemMeta(itemMeta);
            inventory.setItem(playerHeadSlots[i], item);
            if (playerUtility.isDead()) {
                inventory.setItem(playerHeadSlots[i] + 1, black);
                inventory.setItem(playerHeadSlots[i] + 2, black);
            } else {
                inventory.setItem(playerHeadSlots[i] + 1, white);
                inventory.setItem(playerHeadSlots[i] + 2, white);
            }

            if (this.playerUtility.getColor().equals(playerUtility.getColor()))
                playerSlot = playerHeadSlots[i];
        }

        ItemStack voted = makeItem(Material.PINK_STAINED_GLASS_PANE, "Voted");
        for (int slot : votedSlots.keySet()) {
            inventory.setItem(slot + 2, voted);
        }

        ItemStack clock = makeItem(Material.CLOCK, "Time Left");
        ItemStack skip = makeItem(Material.BARRIER, "Skip Vote");

        inventory.setItem(52, clock);
        inventory.setItem(46, skip);
        setFillerGlass();

        menus.add(this);

        if (timeLeft == -1) {
            timeLeft = AmongUs.votingTime;
            discTimeLeft = discussionTime;
        }

        if (runnable == null) {
            startRunnable();
        }

    }

    static void startRunnable() {
        ItemStack clock = makeItem(Material.CLOCK, ChatColor.GOLD + "Time Left");
        ItemStack clock2 = makeItem(Material.CLOCK, ChatColor.GOLD + "Discussion Time Left");
        ItemStack fillerGlass = makeItem(Material.GRAY_STAINED_GLASS_PANE, "");
        active = true;
        runnable = new BukkitRunnable() {
            int t = timeLeft;
            int t2 = discTimeLeft;

            @Override
            public void run() {
                for (meetingRoom room : menus) {
                    clock2.setAmount(t2);
                    ItemMeta itemMeta = clock2.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.WHITE + "Discussion Time Left: " + t2);
                    clock2.setItemMeta(itemMeta);
                    room.inventory.setItem(51, clock2);
                    clock.setAmount(t);
                    ItemMeta itemMeta2 = clock2.getItemMeta();
                    itemMeta2.setDisplayName(ChatColor.WHITE + "Time Left: " + t);
                    clock.setItemMeta(itemMeta);
                    room.inventory.setItem(52, clock);
                    if(t2 <= 0)
                        room.inventory.setItem(51, fillerGlass);
                }
                if (t <= 0 && t2 <= 0) {
                    proceed();
                    cancel();
                }
                timeLeft = t;
                discTimeLeft = t2;

                if(t2 > 0)
                    t2--;
                else
                    t--;
            }
        }.runTaskTimer(AmongUs.plugin, 18, 20);
    }
}
