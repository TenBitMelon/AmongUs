package me.melonboy10.us.events;

import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.maps.AdminMapRenderer;
import me.melonboy10.us.menuSystem.Menu;
import me.melonboy10.us.menuSystem.menus.*;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.Objects;

import static me.melonboy10.us.utilities.AmongUs.*;

public class ClickEvents implements Listener {

    private AmongUsPlugin main = null;

    public ClickEvents(AmongUsPlugin main) {
        this.main = main;
    }

    ArrayList<Integer> reportSelected = new ArrayList<Integer>() {{
        add(96);
        add(97);
        add(100);
        add(112);
        add(113);
        add(116);
    }};
    ArrayList<Integer> useSelected = new ArrayList<Integer>() {{
        add(24);
        add(25);
        add(28);
        add(88);
        add(89);
        add(92);
    }};
    ArrayList<Integer> killSelected = new ArrayList<Integer>() {{
        add(6);
        add(22);
        add(70);
        add(86);
    }};

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        PlayerUtility playerUtility = getPlayerUtility(player);
        AmongUs.InteractionItemType itemType = playerUtility.getInteractionType();
        Task selected = playerUtility.getSelected();

        assert item != null;
        if (AmongUs.isPlaying()) {
            assert item.getType() != Material.AIR;
            if (Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
                if (item.getType().equals(Material.DIAMOND_HOE) && AmongUs.isPlaying()) {
                    if (reportSelected.contains(Objects.requireNonNull(item.getItemMeta()).getCustomModelData())) {
                        if (itemType == InteractionItemType.RA_U || itemType == InteractionItemType.RA_UA || itemType == InteractionItemType.RA_U_K || itemType == InteractionItemType.RA_UA_K || itemType == InteractionItemType.RA_U_KA || itemType == InteractionItemType.RA_UA_KA) {
                            if (selected.getTaskType().equals(Task.TaskType.BODY)) {
                                player.playSound(player.getLocation(), "minecraft:report_body", 1, 1);
                                AmongUs.updateInteraction(player, AmongUs.InteractionSetType.R);
                                AmongUs.callMeeting(AmongUs.getPlayerUtility(Bukkit.getPlayer(Objects.requireNonNull(selected.getArmorStand().getCustomName()))).getColor(), true);
                                selected.remove();
                                playerUtility.removeTask(selected);
                                playerUtility.removeSelected();
                            }
                        }
                    } else if (useSelected.contains(Objects.requireNonNull(item.getItemMeta()).getCustomModelData())) {
                        if (itemType == InteractionItemType.R_UA || itemType == InteractionItemType.RA_UA || itemType == InteractionItemType.RA_UA_KA || itemType == InteractionItemType.RA_UA_K || itemType == InteractionItemType.R_UA_KA || itemType == InteractionItemType.R_UA_K) {
                            if (selected != null) {
                                player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                                if (selected.getTaskType().toString().toLowerCase().contains("vent")) {
                                    if (playerUtility.isInVent()) {
                                        AmongUs.exitVent(player, selected.getTaskType());
                                    }
                                    else {
                                        AmongUs.enterVent(player, selected.getTaskType());
                                        useVent(player, playerUtility, selected.getTaskType());
                                    }
                                } else if(selected.getTaskType().toString().toLowerCase().contains("camera")) {
                                    enterCamera(player);
                                }
                                else {
                                    switch (selected.getTaskType()) {
                                        case BUTTON:
                                            new theButton(playerUtility, selected).open();
                                            break;
                                        case O2_SABOTAGE_1:
                                        case O2_SABOTAGE_2:
                                            new sabotageO2(playerUtility, selected).open();
                                            break;
                                        case LIGHTS_SABOTAGE:
                                            new sabotageLights(playerUtility, selected).open();
                                            break;
                                        case REACTOR_SABOTAGE:
                                            new sabotageReactor(playerUtility, selected).open();
                                            break;
                                        case COMMUNICATIONS_SABOTAGE:
                                            new sabotageComs(playerUtility, selected).open();
                                            break;
                                        case FIX_WIRES:
                                            new fixWires(playerUtility, selected).open();
                                            break;
                                        case DOWNLOAD_DATA:
                                            new downloadData(playerUtility, selected).open();
                                            break;
                                        case DIVERT_POWER:
                                            new divertPower(playerUtility, selected).open();
                                            break;
                                        case DIVERT_POWER_MAIN:
                                            new divertPowerMain(playerUtility, selected).open();
                                            break;
                                        case CALIBRATE_DISTRIBUTOR:
                                            new calibrateDistributor(playerUtility, selected).open();
                                            break;
                                        case UPLOAD_DATA:
                                            new uploadData(playerUtility, selected).open();
                                            break;
                                        case PRIME_SHIELDS:
                                            new primeShields(playerUtility, selected).open();
                                            break;
                                        case CLEAR_ASTEROIDS:
                                            new clearAsteroids(playerUtility, selected).open();
                                            break;
                                        case CHART_COURSE:
                                            new chartCourse(playerUtility, selected).open();
                                            break;
                                        case START_REACTOR:
                                            new startReactor(playerUtility, selected).open();
                                            break;
                                        case UNLOCK_MANIFOLDS:
                                            new unlockManifolds(playerUtility, selected).open();
                                            break;
                                        case SUBMIT_SCAN:
                                            new submitScan(playerUtility, selected).open();
                                            break;
                                        case CARD_SWIPE:
                                            new cardSwipe(playerUtility, selected).open();
                                            break;
                                        case CLEAN_O2_FILTER:
                                            new cleanO2Filter(playerUtility, selected).open();
                                            break;
                                        case STABILIZE_STEERING:
                                            new stabilizeSteering(playerUtility, selected).open();
                                            break;
                                        case ALIGN_ENGINE_OUTPUT:
                                            new alignEngineOutput(playerUtility, selected).open();
                                            break;
                                        case EMPTY_GARBAGE:
                                            new emptyGarbage(playerUtility, selected).open();
                                            break;
                                        case EMPTY_GARBAGE_MAIN:
                                            new emptyGarbageMain(playerUtility, selected).open();
                                            break;
                                        case GATHER_FUEL:
                                            new gatherFuel(playerUtility, selected).open();
                                            break;
                                        case FUEL_ENGINES:
                                            new fuelEngine(playerUtility, selected).open();
                                            break;
                                        case INSPECT_SAMPLE:
                                            new inspectSample(playerUtility, selected).open();
                                            break;
                                        case ADMIN_PANEL:
                                            ItemStack adminMap = new ItemStack(Material.FILLED_MAP, 1);
                                            MapMeta adminMapItemMeta = (MapMeta) adminMap.getItemMeta();
                                            MapView adminMapView = Bukkit.createMap(world);
                                            adminMapView.setScale(MapView.Scale.FARTHEST);
                                            adminMapView.setUnlimitedTracking(false);
                                            adminMapView.getRenderers().clear();
                                            adminMapView.addRenderer(new AdminMapRenderer());
                                            adminMapItemMeta.setCustomModelData(1);
                                            adminMapItemMeta.setMapView(adminMapView);
                                            adminMapItemMeta.setColor(Color.fromRGB(14, 209, 69));
                                            adminMapItemMeta.setDisplayName("Admin");
                                            adminMap.setItemMeta(adminMapItemMeta);
                                            player.getInventory().setItem(6, adminMap);
                                            break;
                                    }
                                }
                            }
                        }
                    } else if (killSelected.contains(Objects.requireNonNull(item.getItemMeta()).getCustomModelData())) {
                        if (itemType == InteractionItemType.R_U_KA || itemType == InteractionItemType.R_UA_KA || itemType == InteractionItemType.RA_U_KA || itemType == InteractionItemType.RA_UA_KA) {
                            if (selected.getTaskType().equals(Task.TaskType.PLAYER) && player.getCooldown(Material.DIAMOND_HOE) <= 0) {
                                Player killed;
                                if (selected.getArmorStand() != null)
                                    killed = Bukkit.getPlayer(Objects.requireNonNull(selected.getArmorStand().getCustomName()));
                                else
                                    killed = Bukkit.getPlayer(Objects.requireNonNull(selected.getSheep().getCustomName()));
                                player.playSound(player.getLocation(), "minecraft:kill", 1, 1);
                                AmongUs.getPlayerUtility(killed).kill(player);
                                AmongUs.updateInteraction(player, AmongUs.InteractionSetType.K);
                            }
                        }
                    } else if (item.getItemMeta().getCustomModelData() == 120) {
                        switch (selected.getTaskType()) {
                            case SECURITY_VENT:
                                useVent(player, playerUtility, Task.TaskType.MEDBAY_VENT);
                                break;
                            case MEDBAY_VENT:
                                useVent(player, playerUtility, Task.TaskType.ELECTRICAL_VENT);
                                break;
                            case ELECTRICAL_VENT:
                                useVent(player, playerUtility, Task.TaskType.SECURITY_VENT);
                                break;
                            case REACTOR_TOP_VENT:
                                useVent(player, playerUtility, Task.TaskType.UPPER_ENGINE_VENT);
                                break;
                            case REACTOR_BOTTOM_VENT:
                                useVent(player, playerUtility, Task.TaskType.LOWER_ENGINE_VENT);
                                break;
                            case NAVIGATION_TOP_VENT:
                                useVent(player, playerUtility, Task.TaskType.WEAPONS_VENT);
                                break;
                            case NAVIGATION_BOTTOM_VENT:
                                useVent(player, playerUtility, Task.TaskType.SHIELDS_VENT);
                                break;
                            case UPPER_ENGINE_VENT:
                                useVent(player, playerUtility, Task.TaskType.REACTOR_TOP_VENT);
                                break;
                            case LOWER_ENGINE_VENT:
                                useVent(player, playerUtility, Task.TaskType.REACTOR_BOTTOM_VENT);
                                break;
                            case ADMIN_VENT:
                                useVent(player, playerUtility, Task.TaskType.CAFETERIA_VENT);
                                break;
                            case CAFETERIA_VENT:
                                useVent(player, playerUtility, Task.TaskType.O2_VENT);
                                break;
                            case O2_VENT:
                                useVent(player, playerUtility, Task.TaskType.ADMIN_VENT);
                                break;
                            case WEAPONS_VENT:
                                useVent(player, playerUtility, Task.TaskType.NAVIGATION_TOP_VENT);
                                break;
                            case SHIELDS_VENT:
                                useVent(player, playerUtility, Task.TaskType.NAVIGATION_BOTTOM_VENT);
                                break;
                        }
                    }
                } else if (item.getType().equals(Material.FILLED_MAP)) {
                    if (item.getItemMeta().getDisplayName().toLowerCase().contains("sabotage")) {
                        new sabotageMenu(playerUtility, null).open();
                    }
                }
            }
        }
        else {
            if(event.getHand().equals(EquipmentSlot.HAND)) {
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(Material.NOTE_BLOCK)) {
                        new customizationMenu(playerUtility, null).open();
                    } else if (event.getClickedBlock().getType().equals(Material.RED_NETHER_BRICK_WALL) && !player.getInventory().contains(Material.RED_NETHER_BRICK_WALL)) {
                        world.getBlockAt(-45, 5, 23).setType(Material.REDSTONE_BLOCK);
                        player.getInventory().addItem(Menu.makeItem(Material.RED_NETHER_BRICK_WALL, "Fire Extinguisher"));
                    } else if(event.getClickedBlock().getType().equals(Material.SPRUCE_WALL_SIGN)) {
                        Sign block = (Sign) event.getClickedBlock().getState();
                        player.playSound(player.getLocation(), "minecraft:generic", 1, 1);
                        switch (block.getLine(0)) {
                            case "Impostors:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("4")) {
                                    block.setLine(2, "1");
                                }
                                impostorCount = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Kill Cooldown:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("65")) {
                                    block.setLine(2, "0");
                                }
                                killCooldown = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Map:":
//                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
//                                block.update();
                                break;
                            case "Confirm Ejects:":
                                if(block.getLine(2).equals("True")) {
                                    block.setLine(2, "False");
                                } else {
                                    block.setLine(2, "True");
                                }
                                confirmEjects = Boolean.parseBoolean(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Visual Tasks:":
                                if(block.getLine(2).equals("True")) {
                                    block.setLine(2, "False");
                                } else {
                                    block.setLine(2, "True");
                                }
                                visualTasks = Boolean.parseBoolean(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Discussion Time:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("65")) {
                                    block.setLine(2, "10");
                                }
                                discussionTime = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Voting Time:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("65")) {
                                    block.setLine(2, "10");
                                }
                                votingTime = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Long Tasks:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("8")) {
                                    block.setLine(2, "0");
                                }
                                longTaskAmount = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Short Tasks:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("8")) {
                                    block.setLine(2, "0");
                                }
                                shortTaskAmount = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Common Tasks:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("3")) {
                                    block.setLine(2, "1");
                                }
                                commonTaskAmount = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Player Speed:":
                                block.setLine(2, String.valueOf(Double.parseDouble(block.getLine(2)) + 0.5));
                                if(block.getLine(2).equals("3.0")) {
                                    block.setLine(2, "0.5");
                                }
                                playerSpeed = Double.parseDouble(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Crewmate View:":
                                block.setLine(2, String.valueOf(Double.parseDouble(block.getLine(2)) + 0.5));
                                if(block.getLine(2).equals("2.5")) {
                                    block.setLine(2, "0.5");
                                }
                                crewmateVisionDefault = Double.parseDouble(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Impostor View:":
                                block.setLine(2, String.valueOf(Double.parseDouble(block.getLine(2)) + 0.5));
                                if(block.getLine(2).equals("2.5")) {
                                    block.setLine(2, "0.5");
                                }
                                impostorVision = Double.parseDouble(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Emergen. Meeting:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("11")) {
                                    block.setLine(2, "0");
                                }
                                emergencyMeetings = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;
                            case "Emerg. Meet. Cool:":
                                block.setLine(2, String.valueOf(Integer.parseInt(block.getLine(2)) + 1));
                                if(block.getLine(2).equals("65")) {
                                    block.setLine(2, "0");
                                }
                                emergencyCooldown = Integer.parseInt(block.getLine(2));
                                block.update();
                                world.spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0), 25, 0, 0, 0, 0, Material.SPRUCE_PLANKS.createBlockData());
                                world.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                break;

                        }
                    }
                } else if (item != null && item.getItemMeta().getDisplayName().equals("Fire Extinguisher")) {
                    world.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1.5, 0), 500, player.getEyeLocation().getDirection().getX(), player.getEyeLocation().getDirection().getY(), player.getEyeLocation().getDirection().getZ(), 0);
                    player.setVelocity(player.getVelocity().add(player.getFacing().getDirection().multiply(-0.25)));
                }
            }
        }
    }

    private void useVent(Player player, PlayerUtility playerUtility, Task.TaskType vent) {
        player.playSound(player.getLocation(), "minecraft:vent", 1, 1);
        player.teleport(AmongUs.ventLocations.get(vent).clone().add(0, 15, 5));
        playerUtility.getSheep().teleport(AmongUs.ventLocations.get(vent).clone().add(0, -5, 0));
        PlayerMove.checkAroundPlayer(player);

        playerUtility.removeSelected();
        playerUtility.setSelected(AmongUs.ventTasks.get(vent));
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
//        if(item.getType().equals(Material.DIAMOND_HOE) && AmongUs.isPlaying()) {
//            player.sendMessage("SNEAKY");
//        }

        if (AmongUs.isPlaying()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
//        assert item != null;
//        if(item.getType().equals(Material.DIAMOND_HOE) && AmongUs.isPlaying() && inventory.getType().equals(InventoryType.PLAYER)) {
//            player.sendMessage("CLICKYED INVENTORY");
//            event.setCancelled(true);
//        }

        if (AmongUs.isPlaying()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onOffHandSwitcheroo(PlayerSwapHandItemsEvent event) {
        if (AmongUs.isPlaying()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (AmongUs.isPlaying()) {
            event.setCancelled(true);
        }

    }

}
