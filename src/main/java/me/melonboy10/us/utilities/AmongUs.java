package me.melonboy10.us.utilities;

import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.events.PlayerMove;
import me.melonboy10.us.maps.CrewMapRenderer;
import me.melonboy10.us.maps.SabotageMapRenderer;
import me.melonboy10.us.maps.TasksMapRenderer;
import me.melonboy10.us.menuSystem.menus.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static me.melonboy10.us.utilities.Task.TaskType.*;

public class AmongUs {

    public static final Plugin plugin = AmongUsPlugin.getPlugin(AmongUsPlugin.class);
    private static final HashMap<Player, PlayerUtility> playerUtilityMap = new HashMap<>(); //holds custom player info

    private static boolean playing;
    private static boolean playerMovement;
    public static int viewDistance = 10;

    public static int impostorCount = 1;
    public static boolean confirmEjects = true;
    public static int emergencyMeetings = 1;
    public static int emergencyCooldown = 20;
    public static int discussionTime = 64;
    public static int votingTime = 64;
    public static double playerSpeed = 1.5;
    public static double crewmateVisionDefault = 1;
    public static int crewmateVision = (int) (crewmateVisionDefault * viewDistance);
    public static double impostorVision = 2;
    public static int killCooldown = 20;
    public static boolean visualTasks = true;
    public static int commonTaskAmount = 2; //2
    public static int longTaskAmount = 7; //7
    public static int shortTaskAmount = 2; //7

    public static BufferedImage crewMap;
    public static BufferedImage sabotageMap;
    public static BufferedImage adminMap;

    public enum Colors {RED, ORANGE, YELLOW, GREEN, LIME, BLUE, LIGHT_BLUE, CYAN, PURPLE, MAGENTA, PINK, WHITE, LIGHT_GRAY, GRAY}
    public static HashMap<Colors, net.md_5.bungee.api.ChatColor> colorToChat = new HashMap<Colors, net.md_5.bungee.api.ChatColor>() {
        {
            put(Colors.RED, net.md_5.bungee.api.ChatColor.DARK_RED);
            put(Colors.ORANGE, net.md_5.bungee.api.ChatColor.GOLD);
            put(Colors.YELLOW, net.md_5.bungee.api.ChatColor.of("#EDED1E"));
            put(Colors.GREEN, net.md_5.bungee.api.ChatColor.DARK_GREEN);
            put(Colors.LIME, net.md_5.bungee.api.ChatColor.of("#6FE535"));
            put(Colors.BLUE, net.md_5.bungee.api.ChatColor.BLUE);
            put(Colors.LIGHT_BLUE, net.md_5.bungee.api.ChatColor.AQUA);
            put(Colors.CYAN, net.md_5.bungee.api.ChatColor.DARK_AQUA);
            put(Colors.PURPLE, net.md_5.bungee.api.ChatColor.of("#8600CB"));
            put(Colors.MAGENTA, net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
            put(Colors.PINK, net.md_5.bungee.api.ChatColor.of("#E16998"));
            put(Colors.WHITE, net.md_5.bungee.api.ChatColor.WHITE);
//            put(Colors.BLACK, net.md_5.bungee.api.ChatColor.BLACK);
            put(Colors.GRAY, net.md_5.bungee.api.ChatColor.DARK_GRAY);
            put(Colors.LIGHT_GRAY, net.md_5.bungee.api.ChatColor.GRAY);

        }
    };
    public static HashMap<Colors, Color> colorToColorCode = new HashMap<Colors, Color>() {
        {
            put(Colors.RED, Color.fromRGB(176, 46, 38));
            put(Colors.ORANGE, Color.fromRGB(231, 108, 31));
            put(Colors.YELLOW, Color.fromRGB(255, 247, 5));
            put(Colors.GREEN, Color.fromRGB(93, 124, 22));
            put(Colors.LIME, Color.fromBGR(129, 199, 31));
            put(Colors.BLUE, Color.fromRGB(60, 67, 170));
            put(Colors.LIGHT_BLUE, Color.fromRGB(58, 160, 210));
            put(Colors.CYAN, Color.fromRGB(22, 156, 156));
            put(Colors.PURPLE, Color.fromRGB(137, 50, 184));
            put(Colors.MAGENTA, Color.fromRGB(199, 78, 189));
            put(Colors.PINK, Color.fromRGB(209, 94, 104));
            put(Colors.WHITE, Color.fromRGB(249, 255, 254));
//            put(Colors.BLACK, Color.fromRGB(29, 29, 33));
            put(Colors.GRAY, Color.fromRGB(71, 79, 82));
            put(Colors.LIGHT_GRAY, Color.fromRGB(157, 157, 151));

        }
    };
    public static HashMap<Colors, DyeColor> colorToSheepColor = new HashMap<Colors, DyeColor>() {
        {
            put(Colors.RED, DyeColor.RED);
            put(Colors.ORANGE, DyeColor.ORANGE);
            put(Colors.YELLOW, DyeColor.YELLOW);
            put(Colors.GREEN, DyeColor.GREEN);
            put(Colors.LIME, DyeColor.LIME);
            put(Colors.BLUE, DyeColor.BLUE);
            put(Colors.LIGHT_BLUE, DyeColor.LIGHT_BLUE);
            put(Colors.CYAN, DyeColor.CYAN);
            put(Colors.PURPLE, DyeColor.PURPLE);
            put(Colors.MAGENTA, DyeColor.MAGENTA);
            put(Colors.PINK, DyeColor.PINK);
            put(Colors.WHITE, DyeColor.WHITE);
//            put(Colors.BLACK, DyeColor.BLACK);
            put(Colors.GRAY, DyeColor.GRAY);
            put(Colors.LIGHT_GRAY, DyeColor.LIGHT_GRAY);

        }
    };
    public static HashMap<DyeColor, Byte> colorToSheepPacketColor = new HashMap<DyeColor, Byte>() {
        {
            put(DyeColor.RED, (byte) 0x0E);
            put(DyeColor.ORANGE, (byte) 0x01);
            put(DyeColor.YELLOW, (byte) 0x04);
            put(DyeColor.GREEN, (byte) 0x0D);
            put(DyeColor.LIME, (byte) 0x05);
            put(DyeColor.BLUE, (byte) 0x0B);
            put(DyeColor.LIGHT_BLUE, (byte) 0x03);
            put(DyeColor.CYAN, (byte) 0x09);
            put(DyeColor.PURPLE, (byte) 0x0A);
            put(DyeColor.MAGENTA, (byte) 0x02);
            put(DyeColor.PINK, (byte) 0x06);
            put(DyeColor.WHITE, (byte) 0x00);
            put(DyeColor.BLACK, (byte) 0x0F);
            put(DyeColor.GRAY, (byte) 0x07);
            put(DyeColor.LIGHT_GRAY, (byte) 0x08);

        }
    };

    public enum InteractionSetType {R, RA, U, UA, K, KA}

    public enum InteractionItemType {R_U, R_UA, RA_U, RA_UA, R_U_K, R_UA_KA, R_U_KA, RA_U_K, RA_UA_K, RA_UA_KA, RA_U_KA, R_UA_K}

    private final static HashMap<InteractionItemType, ItemStack> crewReportItemMap = new HashMap<InteractionItemType, ItemStack>() {
        {
            put(InteractionItemType.R_U, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Report", 33));
            put(InteractionItemType.R_UA, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Report", 49));
            put(InteractionItemType.RA_U, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Report", 97));
            put(InteractionItemType.RA_UA, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Report", 113));
        }
    };
    private final static HashMap<InteractionItemType, ItemStack> crewUseItemMap = new HashMap<InteractionItemType, ItemStack>() {
        {
            put(InteractionItemType.R_U, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Use", 9));
            put(InteractionItemType.R_UA, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Use", 25));
            put(InteractionItemType.RA_U, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Use", 73));
            put(InteractionItemType.RA_UA, createCustomItem(Material.DIAMOND_HOE, ChatColor.WHITE + "Use", 89));
        }
    };
    private final static HashMap<InteractionItemType, ItemStack> impostorReportItemMap = new HashMap<InteractionItemType, ItemStack>() {
        {
            put(InteractionItemType.R_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 32));
            put(InteractionItemType.R_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 48));
            put(InteractionItemType.RA_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 96));
            put(InteractionItemType.RA_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 112));
            put(InteractionItemType.R_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 36));
            put(InteractionItemType.R_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 52));
            put(InteractionItemType.RA_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 100));
            put(InteractionItemType.RA_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Report", 116));

        }
    };
    private final static HashMap<InteractionItemType, ItemStack> impostorUseItemMap = new HashMap<InteractionItemType, ItemStack>() {
        {
            put(InteractionItemType.R_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 8));
            put(InteractionItemType.R_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 24));
            put(InteractionItemType.RA_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 72));
            put(InteractionItemType.RA_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 88));
            put(InteractionItemType.R_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 12));
            put(InteractionItemType.R_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 28));
            put(InteractionItemType.RA_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 76));
            put(InteractionItemType.RA_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Use", 92));
        }
    };
    private final static HashMap<InteractionItemType, ItemStack> impostorKillItemMap = new HashMap<InteractionItemType, ItemStack>() {
        {
            put(InteractionItemType.R_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 2));
            put(InteractionItemType.R_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 18));
            put(InteractionItemType.RA_U_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 66));
            put(InteractionItemType.RA_UA_K, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 82));
            put(InteractionItemType.R_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 6));
            put(InteractionItemType.R_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 22));
            put(InteractionItemType.RA_U_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 70));
            put(InteractionItemType.RA_UA_KA, createCustomItem(Material.DIAMOND_HOE, ChatColor.RED + "Kill", 86));
        }
    };

    private static final ArrayList<Player> currentPlayers = new ArrayList<>(); //players online (all players online can be over 10, will get color, anything over 12 will not get a color, and will not be added)
    private static final ArrayList<Player> playingPlayers = new ArrayList<>(); //players ingame
    private static final ArrayList<Player> impostors = new ArrayList<>(); //impostors
    private static final ArrayList<Player> deadPlayers = new ArrayList<>(); //players dead
    private final static HashMap<Colors, Player> colorToPlayerMap = new HashMap<>();   // lists all colors in use and they players attached.

    public static final World world = Bukkit.getWorld("world"); // v Spawn locations
    public static final Location[] spawnLocations = {new Location(world, -1, 20, -97), new Location(world, -2, 20, -97), new Location(world, -2, 20, -96), new Location(world, -2, 20, -95), new Location(world, -2, 20, -94), new Location(world, -2, 20, -93), new Location(world, -1, 20, -93), new Location(world, -4, 20, -97), new Location(world, -5, 20, -97), new Location(world, -5, 20, -96), new Location(world, -5, 20, -95), new Location(world, -5, 20, -94), new Location(world, -5, 20, -93), new Location(world, -4, 20, -93)};

    private static final ArrayList<Task> currentTasks = new ArrayList<>();
    private static int totalTaskCount;
    private static final HashMap<Task.TaskType, HashMap<Integer, Location>> longTaskLocations = new HashMap<Task.TaskType, HashMap<Integer, Location>>() {{
        put(Task.TaskType.CLEAR_ASTEROIDS, new HashMap<Integer, Location>() {{
            put(0, new Location(world, 19, 5, -101)); // 0 is the asteroids no other parts
        }});
        put(DIVERT_POWER, new HashMap<Integer, Location>() {{ // 0 is in electrical, all others are the divert power locations
            put(0, new Location(world, -13, 5, -82)); // in electrical / starting
            put(1, new Location(world, -31, 5, -104)); //upper engine
            put(2, new Location(world, -39, 5, -91)); //reactor
            put(3, new Location(world, -22, 5, -89)); //security
            put(4, new Location(world, -35, 5, -80)); //lower engine
            put(5, new Location(world, 15, 5, -70)); //coms
            put(6, new Location(world, 24, 5, -78)); //shields
            put(7, new Location(world, 32, 5, -91)); //nav
            put(8, new Location(world, 16, 5, -91)); //o2
            put(9, new Location(world, 122, 5, -101)); //weapons
        }});
        put(EMPTY_GARBAGE, new HashMap<Integer, Location>() {{ // 0 is caf, 1 is o2, 2 is main
            put(0, new Location(world, 10, 5, -104)); // caf
            put(1, new Location(world, 8, 5, -89)); // o2
            put(2, new Location(world, 2, 5, -65)); //storage
        }});
        put(Task.TaskType.FUEL_ENGINES, new HashMap<Integer, Location>() {{ // 0 is gather fuel, 1 is upper, 2 is lower
            put(0, new Location(world, -3, 5, -70));
            put(1, new Location(world, -32, 5, -98)); // upper
            put(2, new Location(world, -32, 5, -73)); // lower
        }});
        put(Task.TaskType.START_REACTOR, new HashMap<Integer, Location>() {{ // 0 is reactor no other parts
            put(0, new Location(world, -43, 5, -87));
        }});
        put(Task.TaskType.SUBMIT_SCAN, new HashMap<Integer, Location>() {{ // 0 is scan no other parts
            put(0, new Location(world, -13, 5, -89));
        }});
        put(Task.TaskType.UPLOAD_DATA, new HashMap<Integer, Location>() {{ // 0 is upload, all others are download locations
            put(0, new Location(world, 7, 5, -85));
            put(1, new Location(world, 7, 5, -108)); //caf
            put(2, new Location(world, -15, 5, -82)); //electrical
            put(3, new Location(world, 11, 5, -70)); //coms
            put(4, new Location(world, 34, 5, -91)); //nav
            put(5, new Location(world, 19, 5, -104)); //weapons
        }});
    }};
    private static final HashMap<Task.TaskType, Location[]> shortTaskLocations = new HashMap<Task.TaskType, Location[]>() {{
        put(Task.TaskType.ALIGN_ENGINE_OUTPUT, new Location[]{new Location(world, -35, 5, -97), new Location(world, -34, 5, -72)});
        put(Task.TaskType.CALIBRATE_DISTRIBUTOR, new Location[]{new Location(world, -9, 5, -82)});
        put(Task.TaskType.CHART_COURSE, new Location[]{new Location(world, 36, 5, -89)});
        put(Task.TaskType.CLEAN_O2_FILTER, new Location[]{new Location(world, 11, 5, -90)});
        put(Task.TaskType.PRIME_SHIELDS, new Location[]{new Location(world, 17, 5, -70)});
        put(Task.TaskType.STABILIZE_STEERING, new Location[]{new Location(world, 37, 5, -87)});
        put(Task.TaskType.UNLOCK_MANIFOLDS, new Location[]{new Location(world, -44, 5, -91)});
    }};
    private static final Location[] wiresLocations = {
            new Location(world, -8, 5, -107), //caf
            new Location(world, -29, 5, -88), //security
            new Location(world, -11, 5, -82), //electrical
            new Location(world, -3, 5, -80), //storage
            new Location(world, 28, 5, -88)}; //nav
    public static final HashMap<Task.TaskType, Location> ventLocations = new HashMap<Task.TaskType, Location>() {
        {
            put(SECURITY_VENT, new Location(world, -23, 5, -84, -180, 75)); //SECURITY_VENT,
            put(MEDBAY_VENT, new Location(world, -19, 5, -90, -180, 75)); //MEDBAY_VENT,
            put(ELECTRICAL_VENT, new Location(world, -17, 5, -82, -180, 75)); //ELECTRICAL_VENT,
            put(REACTOR_TOP_VENT, new Location(world, -41, 5, -90, -180, 75)); //REACTOR_TOP_VENT,
            put(REACTOR_BOTTOM_VENT, new Location(world, -41, 5, -84, -180, 75)); //REACTOR_BOTTOM_VENT,
            put(NAVIGATION_TOP_VENT, new Location(world, 31, 5, -90, -180, 75)); //NAVIGATION_TOP_VENT,
            put(NAVIGATION_BOTTOM_VENT, new Location(world, 31, 5, -84, -180, 75)); //NAVIGATION_BOTTOM_VENT,
            put(UPPER_ENGINE_VENT, new Location(world, -29, 5, -103, -180, 75)); //UPPER_ENGINE_VENT,
            put(LOWER_ENGINE_VENT, new Location(world, -29, 5, -71, -180, 75)); //LOWER_ENGINE_VENT,
            put(ADMIN_VENT, new Location(world, 8, 5, -79, -180, 75)); //ADMIN_VENT,
            put(CAFETERIA_VENT, new Location(world, 8, 5, -97, -180, 75)); //CAFETERIA_VENT,
            put(O2_VENT, new Location(world, 21, 5, -84, -180, 75));//O2_VENT, // in hallway
            put(WEAPONS_VENT, new Location(world, 18, 5, -104, -180, 75)); //WEAPONS_VENT,
            put(SHIELDS_VENT, new Location(world, 21, 5, -71, -180, 75)); //SHIELDS_VENT
        }
    };
    public static final HashMap<Task.TaskType, Task> ventTasks = new HashMap<>();
    public static final HashMap<PlayerUtility.Camera, Location> cameraLocations = new HashMap<PlayerUtility.Camera, Location>(){{
        put(PlayerUtility.Camera.NONE, new Location(world, -24, 20, -86, -180, 75));
        put(PlayerUtility.Camera.ADMIN, new Location(world, 0, 20, -79, -180, 75));
        put(PlayerUtility.Camera.MEDBAY, new Location(world, -16, 20, -95, -180, 75));
        put(PlayerUtility.Camera.SECURITY, new Location(world, -32, 20, -82, -180, 75));
        put(PlayerUtility.Camera.NAVIGATION, new Location(world, 24, 20, -82, -180, 75));
    }};

    private static final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private static final Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
    private static final BossBar bossBar = Bukkit.createBossBar(ChatColor.WHITE + "Tasks", BarColor.GREEN, BarStyle.SEGMENTED_6, BarFlag.DARKEN_SKY);
    public static BossBar sabotageBar;
    public static BukkitTask sabotageRunnable;
    public static sabotageMenu.sabotageType sabotageType;
    //    private static final HashMap<String, Team> teams = new HashMap<>(); //color and team

    public static void setUpForServer() {
        currentPlayers.clear();
        playingPlayers.clear();
        playing = false;
        playerMovement = false;
        colorToPlayerMap.clear();
//        teams.clear();
        deadPlayers.clear();
        currentTasks.forEach((Task::remove));
        currentTasks.clear();
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setDamageAmount(0);
        world.getWorldBorder().setSize(256);
        world.getWorldBorder().setWarningDistance(1);

        try {
            crewMap = ImageIO.read(new URL("https://www.dropbox.com/s/2wlwa96yc8s620z/crewMap.png?dl=1"));
            sabotageMap = ImageIO.read(new URL("https://www.dropbox.com/s/dd38b7382bu14p6/sabotageMap.png?dl=1"));
            adminMap = ImageIO.read(new URL("https://www.dropbox.com/s/bzpmi2bptj1o4jg/adminMap.png?dl=1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            setUpPlayer(p);
        }
    }

    public static void stopForServer() {
        endGame();
    }

    public static void setUpPlayer(Player p) { //color setup
        Colors color = null;
        for (int i = Colors.values().length; i > 0; i--) {
            Colors newColor = Colors.values()[new Random().nextInt(Colors.values().length)];
            if (!colorToPlayerMap.containsKey(newColor)) {
                color = newColor;

                currentPlayers.add(p);
                setPlayerColor(color, p);

                break;
            }
        }
        if (color == null) {
            p.sendMessage(ChatColor.RED + "You have not joined the game! (too many players)");
        }
    }

    public static void startGame() {
        if (!playing) {

            playing = true;
            playerMovement = false;
            world.getBlockAt(-33, 5, 23).setType(Material.REDSTONE_BLOCK);

            if (currentPlayers.size() > 10) {
                for (int p = 0; p < 10; p++) {
                    playingPlayers.add(currentPlayers.get(p));
                }
            } else {
                playingPlayers.addAll(currentPlayers);
            }

            ArrayList<Player> randomPlayer = new ArrayList<>(playingPlayers);
            Collections.shuffle(randomPlayer);
            for (int i = 0; i < impostorCount; i++) {
                getPlayerUtility(randomPlayer.get(i)).setImpostor(true);
                getPlayerUtility(randomPlayer.get(i)).setInteractionType(InteractionItemType.R_U_K);
                impostors.add(randomPlayer.get(i));
            }

            for (int i = 0; i < playingPlayers.size(); i++) {
                Player player = playingPlayers.get(i);

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 10, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 255, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 9999999, 200, true, false));
                sendTitleText("│", "SHHHHHHH!", player);
                player.playSound(player.getLocation(), "minecraft:shhhhhhh", 1, 1);
                int finalI = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (getPlayerUtility(playingPlayers.get(finalI)).isImpostor()) {
                            sendTitleText(ChatColor.RED + "Impostor", "There is " + ChatColor.RED + impostorCount + ChatColor.WHITE + " impostors among us.", playingPlayers.get(finalI));
                        } else {
                            sendTitleText(ChatColor.AQUA + "Crewmate", "There is " + ChatColor.RED + impostorCount + ChatColor.WHITE + " impostors among us.", playingPlayers.get(finalI));
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                playerMovement = true;

                                player.teleport(new Location(world, spawnLocations[finalI].getX() + 0.5, 20, spawnLocations[finalI].getZ() + 0.5, -180, 75), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                                player.setGameMode(GameMode.ADVENTURE);
                                player.setFoodLevel(6);
                                player.setWalkSpeed((float) (0.2 * playerSpeed));
                                getPlayerUtility(player).setEmergencyMeetings(emergencyMeetings);

                                Sheep newSheep = (Sheep) world.spawnEntity(new Location(world, spawnLocations[finalI].getX() + 0.5, 4, spawnLocations[finalI].getZ() - 4.5), EntityType.SHEEP);
                                newSheep.setSheared(false);
                                newSheep.setColor(colorToSheepColor.get(getPlayerUtility(player).getColor()));
                                newSheep.setGravity(false);
                                newSheep.setAI(false);
                                newSheep.setInvulnerable(true);
                                newSheep.setCustomName(player.getName());
                                newSheep.setCustomNameVisible(true);
                                getPlayerUtility(player).setSheep(newSheep);

                                player.getInventory().clear();
                                updateInventoryItems(player);

                                ItemStack crewMap = new ItemStack(Material.FILLED_MAP, 1);
                                MapMeta crewMapItemMeta = (MapMeta) crewMap.getItemMeta();
                                MapView crewView = Bukkit.createMap(world);
                                crewView.setScale(MapView.Scale.FARTHEST);
                                crewView.setUnlimitedTracking(false);
                                crewView.getRenderers().clear();
                                crewView.addRenderer(new CrewMapRenderer());
                                crewMapItemMeta.setCustomModelData(1);
                                crewMapItemMeta.setMapView(crewView);
                                crewMapItemMeta.setColor(Color.fromRGB(95, 95, 255));
                                crewMapItemMeta.setDisplayName(ChatColor.WHITE + "Tasks");
                                crewMap.setItemMeta(crewMapItemMeta);
                                player.getInventory().setItem(8, crewMap);

                                ItemStack taskMap = new ItemStack(Material.FILLED_MAP, 1);
                                MapMeta taskMapItemMeta = (MapMeta) taskMap.getItemMeta();
                                MapView taskMapView = Bukkit.createMap(world);
                                taskMapView.setScale(MapView.Scale.FARTHEST);
                                taskMapView.setUnlimitedTracking(false);
                                taskMapView.getRenderers().clear();
                                taskMapView.addRenderer(new TasksMapRenderer());
                                taskMapItemMeta.setCustomModelData(1);
                                taskMapItemMeta.setMapView(taskMapView);
                                taskMapItemMeta.setColor(Color.fromRGB(95, 95, 255));
                                taskMapItemMeta.setDisplayName(ChatColor.WHITE + "Tasks");
                                taskMap.setItemMeta(taskMapItemMeta);
                                player.getInventory().setItemInOffHand(taskMap);
                                if (getPlayerUtility(player).isImpostor()) {
                                    ItemStack impostorMap = new ItemStack(Material.FILLED_MAP, 1);
                                    MapMeta impostorMapMeta = (MapMeta) impostorMap.getItemMeta();
                                    MapView impostorView = Bukkit.createMap(world);
                                    impostorView.setScale(MapView.Scale.FARTHEST);
                                    impostorView.setUnlimitedTracking(false);
                                    impostorView.getRenderers().clear();
                                    impostorView.addRenderer(new SabotageMapRenderer());
                                    impostorMapMeta.setCustomModelData(1);
                                    impostorMapMeta.setMapView(impostorView);
                                    impostorMapMeta.setColor(Color.fromRGB(179, 6, 0));
                                    impostorMapMeta.setDisplayName(ChatColor.RED + "Sabotage");
                                    impostorMap.setItemMeta(impostorMapMeta);
                                    player.getInventory().setItem(7, impostorMap);

                                    player.setCooldown(Material.DIAMOND_HOE, 20 * AmongUs.killCooldown);
                                    player.setCooldown(Material.FILLED_MAP, 20 * AmongUs.killCooldown);
                                }

                                new Task(Task.TaskType.BUTTON, new Location(world, 0.5, 5, -99.5), player);
                                new Task(SECURITY_CAMERA_VIEW, new Location(world, -23.5, 5, -91.5), player);
                                new Task(ADMIN_PANEL, new Location(world, 9, 5, -81), player);

                                for (int k = -57; k < 50; k++) {
                                    for (int j = -122; j < -50; j++) {
                                        player.sendBlockChange(new Location(world, k, 7, j), Material.BLACK_STAINED_GLASS.createBlockData());
                                    }
                                }

                            }
                        }.runTaskLater(plugin, 110);
                    }
                }.runTaskLater(plugin, 70);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    createTasks();
                    updateBossBar();
                    theButton.startCooldown();
                }
            }.runTaskLater(plugin, 185);
        }
    }

    public static void endGame() {
        if (playing) {
            for (Player p : playingPlayers) {
                p.teleport(new Location(world, 0.5, 5, 0.5), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                p.removePotionEffect(PotionEffectType.JUMP);
                p.removePotionEffect(PotionEffectType.SLOW);
                p.setWalkSpeed((float) (0.2 * playerSpeed));

                p.getInventory().clear();

                getPlayerUtility(p).getSheep().remove();
                getPlayerUtility(p).setSheep(null);

                getPlayerUtility(p).getTasks().forEach((Task::remove));
                getPlayerUtility(p).clearTasks();

                getPlayerUtility(p).setDead(false);
                getPlayerUtility(p).setImpostor(false);
                getPlayerUtility(p).setInVent(false);
                getPlayerUtility(p).setInCamera(false);
                getPlayerUtility(p).setCamera(PlayerUtility.Camera.NONE);

                Colors color = getPlayerUtility(p).getColor();
                ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta leatherMeta = (LeatherArmorMeta) chestplate.getItemMeta();
                leatherMeta.setDisplayName(AmongUs.colorToChat.get(color) + color.name().toUpperCase());
                leatherMeta.setColor(AmongUs.colorToColorCode.get(color));

                chestplate.setItemMeta(leatherMeta);
                leggings.setItemMeta(leatherMeta);
                boots.setItemMeta(leatherMeta);

                p.getInventory().clear();
                p.getInventory().setChestplate(chestplate);
                p.getInventory().setLeggings(leggings);
                p.getInventory().setBoots(boots);
            }

            Bukkit.getScheduler().getPendingTasks().forEach(BukkitTask::cancel);
            playing = false;
            playerMovement = false;
            crewmateVision = (int) (crewmateVisionDefault * viewDistance);
            playingPlayers.clear();
            impostors.clear();
            deadPlayers.clear();
            currentTasks.forEach(Task::remove);
            currentTasks.clear();
            totalTaskCount = 0;
            ventTasks.values().forEach(Task::remove);
            ventTasks.clear();
            bossBar.removeAll();
            sabotageBar = null;
            sabotageRunnable = null;

            meetingRoom.reset();
            sabotageLights.reset();
            sabotageMenu.reset();
            sabotageO2.reset();
            sabotageReactor.reset();
            theButton.reset();

            BlockData data = world.getBlockAt(-41, 5, 19).getBlockData();
            world.getBlockAt(23, 5, -70).setBlockData(data);
            world.getBlockAt(23, 4, -70).setBlockData(data);
            world.getBlockAt(25, 5, -72).setBlockData(data);
            world.getBlockAt(25, 4, -72).setBlockData(data);
        }
    }

    public static PlayerUtility getPlayerUtility(Player p) {
        PlayerUtility playerUtility;
        if (!(playerUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them
            //This player doesn't. Make one for them add add it to the hashmap
            playerUtility = new PlayerUtility(p);
            playerUtilityMap.put(p, playerUtility);

            return playerUtility;
        } else {
            return playerUtilityMap.get(p); //Return the object by using the provided player
        }
    }

//    public static void createAndJoinTeam(Player player, String color) {
//        Team team = scoreboard.registerNewTeam(color);
//        ChatColor chatColor = colorToChat.get(color);
//        team.setPrefix(chatColor + "[" + color.toUpperCase() + "] ");
//        team.setColor(chatColor);
//        team.addPlayer(player);
//        team.setDisplayName(color.toUpperCase());
//
//        teams.put(color, team);
//    }

//    public static HashMap<String, Team> getTeams() {
//        return teams;
//    }

    public static ArrayList<Player> getDeadPlayers() {
        return deadPlayers;
    }

    public static boolean canPlayerMove() {
        return playerMovement;
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static void setPlaying(boolean condition) {
        playing = condition;
    }

    public static ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    public static ArrayList<Player> getPlayingPlayers() {
        return playingPlayers;
    }

    public static void setPlayerColor(Colors color, Player player) {
        Colors oldColor = getPlayerUtility(player).getColor();

        if (currentPlayers.contains(player) && !colorToPlayerMap.containsKey(color)) {
            colorToPlayerMap.remove(oldColor);
            colorToPlayerMap.put(color, player);
            getPlayerUtility(player).setColor(color);
            customizationMenu.takenColors.remove(oldColor);
            customizationMenu.takenColors.add(color);

            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) chestplate.getItemMeta();
            leatherMeta.setDisplayName(AmongUs.colorToChat.get(color) + color.name().toUpperCase());
            leatherMeta.setColor(AmongUs.colorToColorCode.get(color));

            chestplate.setItemMeta(leatherMeta);
            leggings.setItemMeta(leatherMeta);
            boots.setItemMeta(leatherMeta);

            player.getInventory().clear();
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
        }
    }

    public static void sendTitleText(String title, String subtitle, Player... players) {
        if (title.length() > subtitle.length()) {
            final int[] count = {title.length()};

            new BukkitRunnable() {
                final Player[] playerList = players;

                public void run() {
                    if (count[0] >= 0) {
                        Arrays.asList(playerList).forEach((p) -> p.sendTitle(title.substring(0, Math.abs(count[0] - title.length())), subtitle, 0, 40, 0)); //change to playing players
                        count[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 2);
        } else {
            final int[] count = {subtitle.length()};

            new BukkitRunnable() {
                final Player[] playerList = players;

                public void run() {
                    if (count[0] >= 0) {
                        Arrays.asList(playerList).forEach((p) -> p.sendTitle(title, subtitle.substring(0, Math.abs(count[0] - subtitle.length())), 0, 40, 0)); //change to playing players
                        count[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 2);
        }
    }

    public static void sendTitleText(String title, String subtitle, Player player, int timer, int charsAtATime) {
        if (title.length() > subtitle.length()) {
            final int[] count = {title.length()};

            new BukkitRunnable() {
                public void run() {
                    if (count[0] >= 0) {
                        for (int i = 0; i < charsAtATime; i++) {
                            player.sendTitle(title.substring(0, Math.abs(count[0] - title.length())), subtitle, 0, 40, 0); //change to playing players
                        }
                        count[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, timer);
        } else {
            final int[] count = {subtitle.length()};

            new BukkitRunnable() {
                public void run() {
                    if (count[0] >= 0) {
                        for (int i = 0; i < charsAtATime; i++) {
                            player.sendTitle(title, subtitle.substring(0, Math.abs(count[0] - subtitle.length())), 0, 40, 0); //change to playing players
                        }
                        count[0]--;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, timer);
        }
    }

    public static ItemStack createCustomItem(Material material, String displayName, int customModelData, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + displayName);
        meta.setCustomModelData(customModelData);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ArrayList<Task> getTasks() {
        return currentTasks;
    }

    private static void createTasks() { //tasks are per player
        int currentTaskCount = 0;

        for (Player player : playingPlayers) {
            if (!getPlayerUtility(player).isImpostor()) {

                ArrayList<Integer> commonTasks = new ArrayList<Integer>() {{
                    add(0);
                    add(1);
                }};
                Collections.shuffle(commonTasks);

                if (commonTaskAmount == 1 && commonTasks.get(0) == 0) {
                    //WIRES
                    ArrayList<Location> wires = new ArrayList<>(Arrays.asList(wiresLocations));
                    Collections.shuffle(wires);

                    Task wires1 = new Task(Task.TaskType.FIX_WIRES, wires.get(0), player);
                    Task wires2 = new Task(Task.TaskType.FIX_WIRES, wires.get(1), player, wires1);
                    Task wires3 = new Task(Task.TaskType.FIX_WIRES, wires.get(2), player, wires2);

                    currentTasks.add(wires1);
                    currentTasks.add(wires2);
                    currentTasks.add(wires3);
                    currentTaskCount += 3;
                } else if (commonTaskAmount == 1 && commonTasks.get(0) == 1) {
                    //SWIPE
                    Task cardSwipe = new Task(Task.TaskType.CARD_SWIPE, new Location(world, 13, 4.5, -80.00), player, null);
                    currentTasks.add(cardSwipe);
                    currentTaskCount++;
                } else if (commonTaskAmount == 2) {
                    //WIRES
                    ArrayList<Location> wires = new ArrayList<>(Arrays.asList(wiresLocations));
                    Collections.shuffle(wires);

                    Task wires1 = new Task(Task.TaskType.FIX_WIRES, wires.get(0), player);
                    Task wires2 = new Task(Task.TaskType.FIX_WIRES, wires.get(1), player, wires1);
                    Task wires3 = new Task(Task.TaskType.FIX_WIRES, wires.get(2), player, wires2);

                    currentTasks.add(wires1);
                    currentTasks.add(wires2);
                    currentTasks.add(wires3);
                    currentTaskCount += 3;

                    //SWIPE
                    Task cardSwipe = new Task(Task.TaskType.CARD_SWIPE, new Location(world, 13, 4.5, -80.00), player);
                    currentTasks.add(cardSwipe);
                    currentTaskCount++;
                }

                ArrayList<Task.TaskType> shortTasksTypes = new ArrayList<Task.TaskType>(shortTaskLocations.keySet());
                Collections.shuffle(shortTasksTypes);

                for (int i = 0; i < shortTaskAmount; i++) {
                    if (shortTasksTypes.get(i).equals(Task.TaskType.ALIGN_ENGINE_OUTPUT)) {
                        Task shortTask1 = new Task(Task.TaskType.ALIGN_ENGINE_OUTPUT, shortTaskLocations.get(Task.TaskType.ALIGN_ENGINE_OUTPUT)[0], player);
                        Task shortTask2 = new Task(Task.TaskType.ALIGN_ENGINE_OUTPUT, shortTaskLocations.get(Task.TaskType.ALIGN_ENGINE_OUTPUT)[1], player);

                        currentTasks.add(shortTask1);
                        currentTasks.add(shortTask2);

                        currentTaskCount += 2;
                    } else {
                        Task shortTask = new Task(shortTasksTypes.get(i), shortTaskLocations.get(shortTasksTypes.get(i))[0], player);

                        currentTasks.add(shortTask);

                        currentTaskCount++;
                    }
                }

                ArrayList<Task.TaskType> longTaskTypes = new ArrayList<Task.TaskType>(longTaskLocations.keySet());
                Collections.shuffle(longTaskTypes);

                for (int i = 0; i < longTaskAmount; i++) {
                    HashMap<Integer, Location> longTaskLocationMap = new HashMap<Integer, Location>(longTaskLocations.get(longTaskTypes.get(i)));

                    if (longTaskLocationMap.size() > 1) {
                        switch (longTaskTypes.get(i)) {
                            case DIVERT_POWER:
                                Task divertPowerMain = new Task(Task.TaskType.DIVERT_POWER_MAIN, longTaskLocationMap.get(0), player);
                                Task divertPower = new Task(DIVERT_POWER, longTaskLocationMap.get((new Random()).nextInt(longTaskLocationMap.size() - 1) + 1), player, divertPowerMain);

                                currentTasks.add(divertPowerMain);
                                currentTasks.add(divertPower);

                                currentTaskCount += 2;
                                break;
                            case EMPTY_GARBAGE:
                                Task emptyGarbage = new Task(EMPTY_GARBAGE, longTaskLocationMap.get((new Random()).nextInt(longTaskLocationMap.size() - 1)), player);
                                Task emptyGarbageMain = new Task(Task.TaskType.EMPTY_GARBAGE_MAIN, longTaskLocationMap.get(2), player, emptyGarbage);

                                currentTasks.add(emptyGarbage);
                                currentTasks.add(emptyGarbageMain);

                                currentTaskCount += 2;
                                break;
                            case FUEL_ENGINES:
                                Task fuel = new Task(Task.TaskType.GATHER_FUEL, longTaskLocationMap.get(0), player);
                                Task fill = new Task(Task.TaskType.FUEL_ENGINES, longTaskLocationMap.get(1), player, fuel);
                                Task fuel2 = new Task(Task.TaskType.GATHER_FUEL, longTaskLocationMap.get(0), player, fill);
                                Task fill2 = new Task(Task.TaskType.FUEL_ENGINES, longTaskLocationMap.get(2), player, fuel2);

                                currentTasks.add(fuel);
                                currentTasks.add(fill);
                                currentTasks.add(fuel2);
                                currentTasks.add(fill2);

                                currentTaskCount += 4;
                                break;
                            case UPLOAD_DATA:
                                Task download = new Task(Task.TaskType.DOWNLOAD_DATA, longTaskLocationMap.get((new Random()).nextInt(longTaskLocationMap.size() - 1) + 1), player);
                                Task upload = new Task(Task.TaskType.UPLOAD_DATA, longTaskLocationMap.get(0), player, download);

                                currentTasks.add(download);
                                currentTasks.add(upload);

                                currentTaskCount += 2;
                                break;
                        }
                    } else {
                        Task longTask = new Task(longTaskTypes.get(i), longTaskLocationMap.get(0), player);
                        currentTasks.add(longTask);
                        currentTaskCount++;
                    }
                }

                for (Player impostor : impostors) {
                    Task playerTask = new Task(PLAYER, player.getLocation(), impostor, getPlayerUtility(player).getSheep());
                }

            } else {

                for (Task.TaskType taskType : ventLocations.keySet()) {
                    Task vent = new Task(taskType, ventLocations.get(taskType), player);
                    ventTasks.put(taskType, vent);
                }
            }
//        for(int i = 0; i < taskLocationMap.size(); i++) {
//            for(Player player : Bukkit.getOnlinePlayers()) {
//                Task task = new Task((Task.TaskType) taskLocationMap.values().toArray()[i],(Location) taskLocationMap.keySet().toArray()[i], player);
//                currentTasks.add(task);
//                currentTaskCount++;
//            }
//        }

        }
        totalTaskCount = currentTaskCount;
        bossBar.setProgress(0);
    }

    public static void updateBossBar() {
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        bossBar.setVisible(true);
        bossBar.setProgress(((double) totalTaskCount - (double) currentTasks.size()) / (double) totalTaskCount);
    }

    public static void removeTask(Task task) {
        currentTasks.remove(task);
    }

    public static void updateInteraction(Player p, InteractionSetType type) {
        InteractionItemType interactionType = getPlayerUtility(p).getInteractionType();

        String rFinder = interactionType.toString().substring(0, 1).replace("_", "");
        String uFinder = (interactionType.toString() + "_").substring(2, 4).replace("_", "").replace("K", "");
        String kFinder = "";
        if (getPlayerUtility(p).isImpostor())
            kFinder = "K" + (interactionType.toString().substring(5).replace("_", "").replace("K", ""));
        if (type.toString().contains("U")) {
            if (getPlayerUtility(p).isImpostor()) {
                if (type.toString().contains("A")) {
                    interactionType = InteractionItemType.valueOf(rFinder + "_UA_" + kFinder);
                } else {
                    interactionType = InteractionItemType.valueOf(rFinder + "_U_" + kFinder);
                }
            } else {
                if (type.toString().contains("A")) {
                    interactionType = InteractionItemType.valueOf(rFinder + "_UA");
                } else {
                    interactionType = InteractionItemType.valueOf(rFinder + "_U");
                }
            }
        } else if (type.toString().contains("R") && !getPlayerUtility(p).isDead()) {
            if (getPlayerUtility(p).isImpostor()) {
                String U_K = uFinder + "_" + kFinder;
                if (type.toString().contains("A")) {
                    interactionType = InteractionItemType.valueOf("RA_" + U_K);
                } else {
                    interactionType = InteractionItemType.valueOf("R_" + U_K);
                }
            } else {
                if (type.toString().contains("A")) {
                    interactionType = InteractionItemType.valueOf("RA_" + uFinder);
                } else {
                    interactionType = InteractionItemType.valueOf("R_" + uFinder);
                }
            }
        } else if (type.toString().contains("K") && getPlayerUtility(p).isImpostor() && !getPlayerUtility(p).isDead()) {
            String R_U = rFinder + "_" + uFinder;
            if (type.toString().contains("A")) {
                interactionType = InteractionItemType.valueOf(R_U + "_KA");
            } else {
                interactionType = InteractionItemType.valueOf(R_U + "_K");
            }
        }
        getPlayerUtility(p).setInteractionType(interactionType);
        updateInventoryItems(p);
    }

    private static void updateInventoryItems(Player p) {
        ItemStack barrier = createCustomItem(Material.BARRIER, "", 1);
        ItemStack vent = createCustomItem(Material.DIAMOND_HOE, ChatColor.GOLD + "Vent", 120);
        p.getInventory().setItem(2, barrier);
        p.getInventory().setItem(3, barrier);
        p.getInventory().setItem(4, barrier);
        p.getInventory().setItem(5, barrier);
        p.getInventory().setItem(6, barrier);
        if (getPlayerUtility(p).isImpostor()) {
            p.getInventory().setItem(0, impostorReportItemMap.get(getPlayerUtility(p).getInteractionType()));
            p.getInventory().setItem(1, impostorUseItemMap.get(getPlayerUtility(p).getInteractionType()));
            p.getInventory().setItem(2, impostorKillItemMap.get(getPlayerUtility(p).getInteractionType()));
            if (getPlayerUtility(p).isInVent()) {
                p.getInventory().setItem(3, vent);
            }
        } else {
            p.getInventory().setItem(0, crewReportItemMap.get(getPlayerUtility(p).getInteractionType()));
            p.getInventory().setItem(1, crewUseItemMap.get(getPlayerUtility(p).getInteractionType()));
            p.getInventory().setItem(7, barrier);
        }
    }

    public static void callMeeting(Colors color, boolean body) {
        if (sabotageType != null) {
            if (sabotageType.equals(sabotageMenu.sabotageType.sabotageReactor)) {
                sabotageMenu.reactorComplete();
            } else if (sabotageType.equals(sabotageMenu.sabotageType.sabotageO2)) {
                sabotageMenu.o2Complete();
            }
        }

        for (int i = 0; i < playingPlayers.size(); i++) {
            Player player = playingPlayers.get(i);

            if(body) {
                player.playSound(player.getLocation(), "minecraft:report_body", 1, 1);
            } else {
                player.playSound(player.getLocation(), "minecraft:e_meeting", 1, 1);
            }

            for (Iterator<Task> iterator = getPlayerUtility(player).getTasks().iterator(); iterator.hasNext(); ) {
                Task task = iterator.next();
                if (task.getTaskType().equals(BODY) || task.getTaskType().equals(O2_SABOTAGE_1) || task.getTaskType().equals(O2_SABOTAGE_2) || task.getTaskType().equals(REACTOR_SABOTAGE)) {
                    iterator.remove();
                    task.setComplete();
                }
            }

            for (int k = -57; k < 50; k++) {
                for (int j = -122; j < -50; j++) {
                    player.sendBlockChange(new Location(world, k, 7, j), Material.BLACK_STAINED_GLASS.createBlockData());
                }
            }

            player.teleport(new Location(world, spawnLocations[i].getX() + 0.5, 20, spawnLocations[i].getZ() + 0.5, -180, 75), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            getPlayerUtility(player).getSheep().teleport(new Location(world, spawnLocations[i].getX() + 0.5, 4, spawnLocations[i].getZ() - 4.5));
            PlayerMove.checkAroundPlayer(player, new Location(world, 0.5, 5, -99.5));

            player.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));

            if(body) {
                player.sendTitle(AmongUs.colorToChat.get(color) + "\uE012", "", 0, 30, 0);
            } else {
                player.sendTitle(AmongUs.colorToChat.get(color) + "\uE011", "", 0, 30, 0);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setHelmet(new ItemStack(Material.AIR));
                    new meetingRoom(getPlayerUtility(player), null).open();
                }
            }.runTaskLater(AmongUs.plugin, 30);
        }
    }

    public static void eject(Player ejected) {
        if (ejected != null) {
            getPlayerUtility(ejected).kill(null);
            for (Player player : playingPlayers) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 10, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 255, false, false));

                if (confirmEjects) {
                    if (getPlayerUtility(ejected).isImpostor()) {
                        sendTitleText(colorToChat.get(getPlayerUtility(ejected).getColor()) + "\uE013", ejected.getName() + "was An Impostor", player);
                    } else {
                        sendTitleText(colorToChat.get(getPlayerUtility(ejected).getColor()) + "\uE013", ejected.getName() + "was not An Impostor", player);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sendTitleText(colorToChat.get(getPlayerUtility(ejected).getColor()) + "\uE013", impostors.size() + " impostors remain", player);
                        }
                    }.runTaskLater(plugin, 80);
                } else {
                    sendTitleText(colorToChat.get(getPlayerUtility(ejected).getColor()) + "\uE013", ejected.getName() + "was not An Impostor", player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            checkWinConditions();
                        }
                    }.runTaskLater(plugin, 80);
                }
            }
        } else {
            for (Player player : playingPlayers) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 10, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 255, false, false));

                sendTitleText("", "No one was ejected.", player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendTitleText("", ChatColor.RED + "" + impostors.size() + ChatColor.WHITE + " impostors remain", player);
                    }
                }.runTaskLater(plugin, 80);
            }
        }
    }

    public static void sabotage(sabotageMenu.sabotageType type) {
        sabotageType = type;
        if (type.toString().contains("door")) {
            switch (type) {
                case doorMedbay:
                    cloneDoorNorth(-17, -96);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-17, -96);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorStorage:
                    cloneDoorNorth(-1, -81);
                    cloneDoorEast(4, -75);
                    cloneDoorEast(-8, -70);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-1, -81);
                            cloneDoorEastAir(4, -75);
                            cloneDoorEastAir(-8, -70);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorSecurity:
                    cloneDoorEast(-27, -88);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorEastAir(-27, -88);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorCafeteria:
                    cloneDoorNorth(-1, -89);
                    cloneDoorEast(-11, -101);
                    cloneDoorEast(11, -101);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-1, -89);
                            cloneDoorEastAir(-11, -101);
                            cloneDoorEastAir(11, -101);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorElectrical:
                    cloneDoorNorth(-17, -71);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-17, -71);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorLowerEngine:
                    cloneDoorNorth(-33, -81);
                    cloneDoorEast(-27, -76);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-33, -81);
                            cloneDoorEastAir(-27, -76);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
                case doorUpperEngine:
                    cloneDoorNorth(-33, -95);
                    cloneDoorEast(-27, -101);

                    new BukkitRunnable() {
                        public void run() {
                            cloneDoorNorthAir(-33, -95);
                            cloneDoorEastAir(-27, -101);
                        }
                    }.runTaskLater(plugin, 20 * 10);
                    break;
            }
        } else {
            switch (type) {
                case sabotageReactor:
                    sabotageBar = Bukkit.createBossBar("Reactor Meltdown", BarColor.RED, BarStyle.SOLID);
                    for (Player player : playingPlayers) {
                        Task reactorHandSlotThingScanner1 = new Task(REACTOR_SABOTAGE, new Location(world, -41, 5, -94), player);
                        Task reactorHandSlotThingScanner2 = new Task(REACTOR_SABOTAGE, new Location(world, -41, 5, -80), player);

                        sabotageBar.addPlayer(player);
                    }

                    sabotageBar.setProgress(1);

                    sabotageRunnable = new BukkitRunnable() {
                        int t = 30;

                        @Override
                        public void run() {
                            sabotageBar.setProgress((double) t / 30);
                            if (t % 2 == 0) {
                                world.getWorldBorder().setSize(0);
                                playingPlayers.forEach(player -> player.playSound(player.getLocation(), "minecraft:meltdown", 1, 1));
                            } else {
                                world.getWorldBorder().setSize(256);
                            }

                            t--;

                            if (t <= 0) {
                                impostorWin();
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);

                    break;
                case sabotageO2:
                    sabotageBar = Bukkit.createBossBar("Oxygen Depleting", BarColor.RED, BarStyle.SOLID);
                    for (Player player : playingPlayers) {
                        Task o2topkeypadthing1 = new Task(O2_SABOTAGE_1, new Location(world, 13, 5, -91), player);
                        Task o2topkeypadthing2 = new Task(O2_SABOTAGE_2, new Location(world, 15, 5, -85), player);

                        sabotageBar.addPlayer(player);

                    }

                    sabotageRunnable = new BukkitRunnable() {
                        int t = 30;


                        @Override
                        public void run() {
                            sabotageBar.setProgress((double) t / 30);
                            if (t % 2 == 0) {
                                world.getWorldBorder().setSize(0);
                                playingPlayers.forEach(player -> player.playSound(player.getLocation(), "minecraft:meltdown", 1, 1));
                            } else {
                                world.getWorldBorder().setSize(256);
                            }

                            t--;

                            if (t <= 0) {
                                impostorWin();
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                    break;
                case sabotageLights:
                    sabotageBar = Bukkit.createBossBar("Fix Lights", BarColor.RED, BarStyle.SOLID);
                    for (Player player : playingPlayers) {
                        Task lightsPanel = new Task(LIGHTS_SABOTAGE, new Location(world, -16, 5, -76), player);
                        sabotageBar.addPlayer(player);
                    }

                    sabotageRunnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            crewmateVision--;
                            sabotageBar.setProgress((double) crewmateVision / (double) crewmateVisionDefault);

                            if (crewmateVision <= 5) {
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                    break;
                case sabotageComs:
                    sabotageBar = Bukkit.createBossBar("Fix Lights", BarColor.RED, BarStyle.SOLID);
                    sabotageBar.setProgress(0);
                    for (Player player : playingPlayers) {
                        Task comSpinningMatcherThing = new Task(COMMUNICATIONS_SABOTAGE, new Location(world, 11, 5, -63), player);
                        sabotageBar.addPlayer(player);
                        player.playSound(player.getLocation(), "minecraft:comms_down", 1, 1);
                    }
                    break;
            }
        }
    }

    public static void resetSabotage() {
        world.getWorldBorder().setSize(256);
        sabotageRunnable = null;
        if (sabotageBar != null)
            sabotageBar.removeAll();
        sabotageBar = null;

        crewmateVision = (int) (crewmateVisionDefault * viewDistance);
    }

    private static void cloneDoorNorth(int x, int z) {
        Location northDoor = new Location(world, -38, 7, 13);
        for (int i = 0; i < 3; i++) {
            world.getBlockAt(x + i, 20, z + 5).setType(Material.BARRIER);
            world.getBlockAt(x + i, 21, z + 5).setType(Material.BARRIER);
            for (int j = 0; j < 3; j++) {
                world.getBlockAt(x + i, 6 - j, z).setBlockData(world.getBlockAt(northDoor.getBlockX() + i, northDoor.getBlockY() - j, northDoor.getBlockZ()).getBlockData());
            }
        }
    }

    private static void cloneDoorNorthAir(int x, int z) {
        Location northDoor = new Location(world, -44, 7, 13);
        for (int i = 0; i < 3; i++) {
            world.getBlockAt(x + i, 20, z + 5).setType(Material.AIR);
            world.getBlockAt(x + i, 21, z + 5).setType(Material.AIR);
            for (int j = 0; j < 3; j++) {
                world.getBlockAt(x + i, 6 - j, z).setBlockData(world.getBlockAt(northDoor.getBlockX() + i, northDoor.getBlockY() - j, northDoor.getBlockZ()).getBlockData());
            }
        }
    }

    private static void cloneDoorEast(int x, int z) {
        Location northDoor = new Location(world, -39, 7, 14);
        for (int i = 0; i < 3; i++) {
            world.getBlockAt(x, 20, z + i + 5).setType(Material.BARRIER);
            world.getBlockAt(x, 21, z + i + 5).setType(Material.BARRIER);
            for (int j = 0; j < 3; j++) {
                world.getBlockAt(x, 6 - j, z + i).setBlockData(world.getBlockAt(northDoor.getBlockX(), northDoor.getBlockY() - j, northDoor.getBlockZ() + i).getBlockData());
            }
        }
    }

    private static void cloneDoorEastAir(int x, int z) {
        Location northDoor = new Location(world, -41, 7, 14);
        for (int i = 0; i < 3; i++) {
            world.getBlockAt(x, 20, z + i + 5).setType(Material.AIR);
            world.getBlockAt(x, 21, z + i + 5).setType(Material.AIR);
            for (int j = 0; j < 3; j++) {
                world.getBlockAt(x, 6 - j, z + i).setBlockData(world.getBlockAt(northDoor.getBlockX(), northDoor.getBlockY() - j, northDoor.getBlockZ() + i).getBlockData());
            }
        }
    }

    public static void checkWinConditions() {
        int livingImpostors = 0;
        int livingPlayers = 0;

        for (Player player : playingPlayers) {
            if (getPlayerUtility(player).isDead()) {
                livingPlayers++;
                if (getPlayerUtility(player).isImpostor())
                    livingImpostors++;
            }
        }
        livingPlayers -= livingImpostors;

        if (bossBar.getProgress() == 1) {
            crewmateWin();
        } else if (deadPlayers.size() == playingPlayers.size() - impostorCount) {
            impostorWin();
        } else if (livingImpostors >= livingPlayers) {
            impostorWin();
        }
    }

    private static void crewmateWin() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> impostors = new ArrayList<>();
        for (Player player : playingPlayers) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 100, true, false));
            if (getPlayerUtility(player).isImpostor())
                impostors.add(player);
            else
                players.add(player);
        }
        endGame();

        players.forEach(player -> sendTitleText(ChatColor.BLUE + "Victory" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "", colorToChat.get(getPlayerUtility(player).getColor()) + "\uE011", player));
        impostors.forEach(player -> sendTitleText(ChatColor.RED + "Defeat" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "", colorToChat.get(getPlayerUtility(player).getColor()) + "\uE011", player));
        playingPlayers.forEach(player -> player.playSound(player.getLocation(), "minecraft:crewmate_win", 1, 1));
    }

    private static void impostorWin() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> impostors = new ArrayList<>();
        for (Player player : playingPlayers) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 100, true, false));
            if (getPlayerUtility(player).isImpostor())
                impostors.add(player);
            else
                players.add(player);
        }
        endGame();

        impostors.forEach(player -> sendTitleText(ChatColor.BLUE + "Victory" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "", colorToChat.get(getPlayerUtility(player).getColor()) + "\uE011", player));
        players.forEach(player -> sendTitleText(ChatColor.RED + "Defeat" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "" + ChatColor.WHITE + "", colorToChat.get(getPlayerUtility(player).getColor()) + "\uE011", player));
        playingPlayers.forEach(player -> player.playSound(player.getLocation(), "minecraft:impostor_win", 1, 1));
    }

    public static void enterVent(Player player, Task.TaskType taskType) {
        PlayerUtility playerUtility = getPlayerUtility(player);
        if (playerUtility.isImpostor() && !playerUtility.isInVent()) {
            playerUtility.getSheep().teleport(ventLocations.get(taskType).clone().add(0, -5, 0));
            player.teleport(ventLocations.get(taskType).clone().add(0, 15, 5));
            getPlayerUtility(player).setInVent(true);
            player.setWalkSpeed(0);
            updateInventoryItems(player);
        }
    }

    public static void exitVent(Player player, Task.TaskType taskType) {
        PlayerUtility playerUtility = getPlayerUtility(player);
        if (playerUtility.isImpostor() && playerUtility.isInVent()) {
            playerUtility.getSheep().teleport(ventLocations.get(taskType).clone().add(0, -1, 0));
            getPlayerUtility(player).setInVent(false);
            player.setWalkSpeed((float) (0.2 * playerSpeed));
            getPlayerUtility(player).removeSelected();
            updateInteraction(player, InteractionSetType.U);
        }
    }

    public static void enterCamera(Player player) {
        PlayerUtility playerUtility = getPlayerUtility(player);
        switch (playerUtility.getCamera()) {
            case NONE:
                player.playSound(player.getLocation(), "minecraft:camera", 1, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 10, true, false));
                player.teleport(cameraLocations.get(PlayerUtility.Camera.MEDBAY), PlayerTeleportEvent.TeleportCause.PLUGIN);
                getPlayerUtility(player).setInCamera(true);
                getPlayerUtility(player).setCamera(PlayerUtility.Camera.MEDBAY);
                PlayerMove.checkAroundPlayer(player, cameraLocations.get(PlayerUtility.Camera.MEDBAY));
                updateCamera(PlayerUtility.Camera.MEDBAY);
                break;
            case ADMIN:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 10, true, false));
                player.teleport(cameraLocations.get(PlayerUtility.Camera.SECURITY), PlayerTeleportEvent.TeleportCause.PLUGIN);
                getPlayerUtility(player).setInCamera(true);
                getPlayerUtility(player).setCamera(PlayerUtility.Camera.SECURITY);
                PlayerMove.checkAroundPlayer(player, cameraLocations.get(PlayerUtility.Camera.SECURITY));
                updateCamera(PlayerUtility.Camera.SECURITY);
                break;
            case MEDBAY:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 10, true, false));
                player.teleport(cameraLocations.get(PlayerUtility.Camera.NAVIGATION), PlayerTeleportEvent.TeleportCause.PLUGIN);
                getPlayerUtility(player).setInCamera(true);
                getPlayerUtility(player).setCamera(PlayerUtility.Camera.NAVIGATION);
                PlayerMove.checkAroundPlayer(player, cameraLocations.get(PlayerUtility.Camera.NAVIGATION));
                updateCamera(PlayerUtility.Camera.NAVIGATION);
                break;
            case SECURITY:
                player.playSound(player.getLocation(), "minecraft:camera", 1, 1);
                player.removePotionEffect(PotionEffectType.SLOW);
                player.teleport(cameraLocations.get(PlayerUtility.Camera.NONE), PlayerTeleportEvent.TeleportCause.PLUGIN);
                getPlayerUtility(player).setInCamera(false);
                getPlayerUtility(player).setCamera(PlayerUtility.Camera.NONE);
                PlayerMove.checkAroundPlayer(player, cameraLocations.get(PlayerUtility.Camera.NONE));
                updateCamera(PlayerUtility.Camera.NONE);
                break;
            case NAVIGATION:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 10, true, false));
                player.teleport(cameraLocations.get(PlayerUtility.Camera.ADMIN), PlayerTeleportEvent.TeleportCause.PLUGIN);
                getPlayerUtility(player).setInCamera(true);
                getPlayerUtility(player).setCamera(PlayerUtility.Camera.ADMIN);
                PlayerMove.checkAroundPlayer(player, cameraLocations.get(PlayerUtility.Camera.ADMIN));
                updateCamera(PlayerUtility.Camera.ADMIN);
                break;
        }
    }

    private static void updateCamera(PlayerUtility.Camera camera) {
        Block lever = world.getBlockAt(-37, 5, 19);
        Block cameraOff = world.getBlockAt(-37, 6, 19);
        Block torch = world.getBlockAt(-35, 5, 19);
        Block cameraOn = world.getBlockAt(-35, 6, 19);

        world.getBlockAt(-14, 5, -101).setBlockData(lever.getBlockData());
        world.getBlockAt(-14, 6, -101).setBlockData(cameraOff.getBlockData());
        world.getBlockAt(29, 5, -88).setBlockData(lever.getBlockData());
        world.getBlockAt(29, 6, -88).setBlockData(cameraOff.getBlockData());
        world.getBlockAt(2, 5, -85).setBlockData(lever.getBlockData());
        world.getBlockAt(2, 6, -85).setBlockData(cameraOff.getBlockData());
        world.getBlockAt(-28, 5, -88).setBlockData(lever.getBlockData());
        world.getBlockAt(-28, 6, -88).setBlockData(cameraOff.getBlockData());

        switch (camera) {
            case MEDBAY:
                world.getBlockAt(-14, 5, -101).setBlockData(torch.getBlockData());
                world.getBlockAt(-14, 6, -101).setBlockData(cameraOn.getBlockData());
                break;
            case NAVIGATION:
                world.getBlockAt(29, 5, -88).setBlockData(torch.getBlockData());
                world.getBlockAt(29, 6, -88).setBlockData(cameraOn.getBlockData());
                break;
            case ADMIN:
                world.getBlockAt(2, 5, -85).setBlockData(torch.getBlockData());
                world.getBlockAt(2, 6, -85).setBlockData(cameraOn.getBlockData());
                break;
            case SECURITY:
                world.getBlockAt(-28, 5, -88).setBlockData(torch.getBlockData());
                world.getBlockAt(-28, 6, -88).setBlockData(cameraOn.getBlockData());
                break;
        }
    }
}
