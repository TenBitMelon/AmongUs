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
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class sabotageMenu extends Menu {

    static ItemStack map = AmongUs.createCustomItem(Material.MAP, " ", 1);
    static ItemStack invisible = AmongUs.createCustomItem(Material.BARRIER, "Close Door", 1);

    static ItemStack door = makeItem(Material.OAK_DOOR, "Close Door");
    static ItemStack doorOffset = AmongUs.createCustomItem(Material.OAK_DOOR, "Close Door", 1);
    static ItemStack doorCooldown = makeItem(Material.DARK_OAK_DOOR, "Close Door");
    static ItemStack doorCooldownOffset = AmongUs.createCustomItem(Material.DARK_OAK_DOOR, "Close Door", 1);

    static ItemStack O2 = makeItem(Material.POTION, "Sabotage Oxygen");
    static ItemStack O2Cooldown = makeItem(Material.GLASS_BOTTLE, "Sabotage Oxygen");

    static ItemStack coms = makeItem(Material.MUSIC_DISC_13, "Sabotage Communications");
    static ItemStack comsCooldown = makeItem(Material.MUSIC_DISC_11, "Sabotage Communications");

    static ItemStack lights = makeItem(Material.REDSTONE, "Sabotage Lights");
    static ItemStack lightsCooldown = makeItem(Material.GUNPOWDER, "Sabotage Lights");

    static ItemStack reactor = makeItem(Material.ENDER_EYE, "Sabotage Reactor");
    static ItemStack reactorCooldown = makeItem(Material.ENDER_PEARL, "Sabotage Reactor");

    public enum sabotageType {
        doorCafeteria,
        doorStorage,
        doorElectrical,
        doorUpperEngine,
        doorLowerEngine,
        doorSecurity,
        doorMedbay,
        sabotageReactor,
        sabotageLights,
        sabotageComs,
        sabotageO2,
    }

    public static int doorCafeteria = 0;
    public static int doorStorage = 0;
    public static int doorElectrical = 0;
    public static int doorUpperEngine = 0;
    public static int doorLowerEngine = 0;
    public static int doorSecurity = 0;
    public static int doorMedbay = 0;

    public static int sabotageReactor = 0;
    public static int sabotageLights = 0;
    public static int sabotageComs = 0;
    public static int sabotageO2 = 0;

    public static boolean reactorSabotaged = false;
    public static boolean lightsSabotaged = false;
    public static boolean comsSabotaged = false;
    public static boolean o2Sabotaged = false;

    public static BukkitTask doorCafeteriaRunnable;
    public static BukkitTask doorStorageRunnable;
    public static BukkitTask doorElectricalRunnable;
    public static BukkitTask doorUpperEngineRunnable;
    public static BukkitTask doorLowerEngineRunnable;
    public static BukkitTask doorSecurityRunnable;
    public static BukkitTask doorMedbayRunnable;

    public static BukkitTask sabotageReactorRunnable;
    public static BukkitTask sabotageLightsRunnable;
    public static BukkitTask sabotageComsRunnable;
    public static BukkitTask sabotageO2Runnable;

    static ArrayList<sabotageMenu> menus = new ArrayList<>();

    public static void reset() {
        menus.clear();

        if(doorCafeteriaRunnable != null)
            doorCafeteriaRunnable.cancel();
        if(doorStorageRunnable != null)
            doorStorageRunnable.cancel();
        if(doorElectricalRunnable != null)
            doorElectricalRunnable.cancel();
        if(doorUpperEngineRunnable != null)
            doorUpperEngineRunnable.cancel();
        if(doorLowerEngineRunnable != null)
            doorLowerEngineRunnable.cancel();
        if(doorSecurityRunnable != null)
            doorSecurityRunnable.cancel();
        if(doorMedbayRunnable != null)
            doorMedbayRunnable.cancel();

        if(sabotageReactorRunnable != null)
            sabotageReactorRunnable.cancel();
        if(sabotageLightsRunnable != null)
            sabotageLightsRunnable.cancel();
        if(sabotageComsRunnable != null)
            sabotageComsRunnable.cancel();
        if(sabotageO2Runnable != null)
            sabotageO2Runnable.cancel();

        doorCafeteriaRunnable = null;
        doorStorageRunnable = null;
        doorElectricalRunnable = null;
        doorUpperEngineRunnable = null;
        doorLowerEngineRunnable = null;
        doorSecurityRunnable = null;
        doorMedbayRunnable = null;

        sabotageReactorRunnable = null;
        sabotageLightsRunnable = null;
        sabotageComsRunnable = null;
        sabotageO2Runnable = null;

        doorCafeteria = 0;
        doorStorage = 0;
        doorElectrical = 0;
        doorUpperEngine = 0;
        doorLowerEngine = 0;
        doorSecurity = 0;
        doorMedbay = 0;

        sabotageReactor = 0;
        sabotageLights = 0;
        sabotageComs = 0;
        sabotageO2 = 0;

        reactorSabotaged = false;
        lightsSabotaged = false;
        comsSabotaged = false;
        o2Sabotaged = false;
    }

    public sabotageMenu(PlayerUtility playerUtility, Task task) {
        super(playerUtility, task);
    }

    @Override
    public String getMenuName() {
        return "Sabotage Menu";
    }

    @Override
    public int getSlots() {
        return 54 - 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) { //move the iteration above the cancel so it checks for 0 after it subtracts
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        int slot = event.getSlot();

        switch (slot) {
            case 4:
            case 5:
            case 13:
            case 14:
                if (inventory.getItem(4).getType().equals(Material.OAK_DOOR)) {
                    if (doorCafeteriaRunnable == null) {
                        doorCafeteria = 30;
                        AmongUs.sabotage(sabotageType.doorCafeteria);
                        doorCafeteriaRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldownOffset;
                                    item.setAmount(doorCafeteria);
                                    menu.inventory.setItem(4, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                    if (doorCafeteria <= 0) {
                                        menu.inventory.setItem(4, doorOffset);
                                        doorCafeteriaRunnable = null;
                                        cancel();
                                    }
                                }

                                doorCafeteria--;

                                if (doorCafeteria <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(4, doorOffset);
                                    doorCafeteriaRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 10:
                if (inventory.getItem(10).getType().equals(Material.OAK_DOOR)) {
                    if (doorUpperEngineRunnable == null) {
                        doorUpperEngine = 30;
                        AmongUs.sabotage(sabotageType.doorUpperEngine);
                        doorUpperEngineRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldown;
                                    item.setAmount(doorUpperEngine);
                                    menu.inventory.setItem(10, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorUpperEngine--;

                                if (doorUpperEngine <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(10, door);
                                    doorUpperEngineRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 18:
                if (inventory.getItem(18).getType().equals(Material.ENDER_EYE)) {
                    reactorSabotaged = true;
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(4, doorCooldownOffset);
                        menu.inventory.setItem(10, doorCooldown);
                        menu.inventory.setItem(20, doorCooldown);
                        menu.inventory.setItem(21, doorCooldown);
                        menu.inventory.setItem(28, doorCooldown);
                        menu.inventory.setItem(29, doorCooldown);
                        menu.inventory.setItem(31, doorCooldownOffset);

                        menu.inventory.setItem(18, reactorCooldown);
                        menu.inventory.setItem(24, O2Cooldown);
                        menu.inventory.setItem(30, lightsCooldown);
                        menu.inventory.setItem(42, comsCooldown);
                    }
                    AmongUs.sabotage(sabotageType.sabotageReactor);
                }
                break;
            case 20:
                if (inventory.getItem(20).getType().equals(Material.OAK_DOOR)) {
                    if (doorSecurityRunnable == null) {
                        doorSecurity = 30;
                        AmongUs.sabotage(sabotageType.doorSecurity);
                        doorSecurityRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldown;
                                    item.setAmount(doorSecurity);
                                    menu.inventory.setItem(20, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorSecurity--;

                                if (doorSecurity <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(20, door);
                                    doorSecurityRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 21:
                if (inventory.getItem(21).getType().equals(Material.OAK_DOOR)) {
                    if (doorMedbayRunnable == null) {
                        doorMedbay = 30;
                        AmongUs.sabotage(sabotageType.doorMedbay);
                        doorMedbayRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldown;
                                    item.setAmount(doorMedbay);
                                    menu.inventory.setItem(21, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorMedbay--;

                                if (doorMedbay <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(21, door);
                                    doorMedbayRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 24:
                if (inventory.getItem(24).getType().equals(Material.POTION)) {
                    o2Sabotaged = true;
                    me.melonboy10.us.menuSystem.menus.sabotageO2.generateCombo();
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(4, doorCooldownOffset);
                        menu.inventory.setItem(10, doorCooldown);
                        menu.inventory.setItem(20, doorCooldown);
                        menu.inventory.setItem(21, doorCooldown);
                        menu.inventory.setItem(28, doorCooldown);
                        menu.inventory.setItem(29, doorCooldown);
                        menu.inventory.setItem(31, doorCooldownOffset);

                        menu.inventory.setItem(18, reactorCooldown);
                        menu.inventory.setItem(24, O2Cooldown);
                        menu.inventory.setItem(30, lightsCooldown);
                        menu.inventory.setItem(42, comsCooldown);
                    }
                    AmongUs.sabotage(sabotageType.sabotageO2);
                }
                break;
            case 28:
                if (inventory.getItem(28).getType().equals(Material.OAK_DOOR)) {
                    if (doorLowerEngineRunnable == null) {
                        doorLowerEngine = 30;
                        AmongUs.sabotage(sabotageType.doorLowerEngine);
                        doorLowerEngineRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldown;
                                    item.setAmount(doorLowerEngine);
                                    menu.inventory.setItem(28, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorLowerEngine--;

                                if (doorLowerEngine <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(28, door);
                                    doorLowerEngineRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 29:
                if (inventory.getItem(29).getType().equals(Material.OAK_DOOR)) {
                    if (doorElectricalRunnable == null) {
                        doorElectrical = 30;
                        AmongUs.sabotage(sabotageType.doorElectrical);
                        doorElectricalRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldown;
                                    item.setAmount(doorElectrical);
                                    menu.inventory.setItem(29, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorElectrical--;

                                if (doorElectrical <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(29, door);
                                    doorElectricalRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 30:
                if (inventory.getItem(30).getType().equals(Material.REDSTONE)) {
                    lightsSabotaged = true;
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(4, doorCooldownOffset);
                        menu.inventory.setItem(10, doorCooldown);
                        menu.inventory.setItem(20, doorCooldown);
                        menu.inventory.setItem(21, doorCooldown);
                        menu.inventory.setItem(28, doorCooldown);
                        menu.inventory.setItem(29, doorCooldown);
                        menu.inventory.setItem(31, doorCooldownOffset);

                        menu.inventory.setItem(18, reactorCooldown);
                        menu.inventory.setItem(24, O2Cooldown);
                        menu.inventory.setItem(30, lightsCooldown);
                        menu.inventory.setItem(42, comsCooldown);
                    }
                    AmongUs.sabotage(sabotageType.sabotageLights);
                }
                break;
            case 31:
            case 32:
            case 40:
            case 41:
                if (inventory.getItem(31).getType().equals(Material.OAK_DOOR)) {
                    if (doorStorageRunnable == null) {
                        doorStorage = 30;
                        AmongUs.sabotage(sabotageType.doorStorage);
                        doorStorageRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (sabotageMenu menu : menus) {
                                    ItemStack item = doorCooldownOffset;
                                    item.setAmount(doorStorage);
                                    menu.inventory.setItem(31, item);

                                    if (checkDoors()) {
                                        menu.inventory.setItem(18, reactorCooldown);
                                        menu.inventory.setItem(24, O2Cooldown);
                                        menu.inventory.setItem(30, lightsCooldown);
                                        menu.inventory.setItem(42, comsCooldown);
                                    }

                                }

                                doorStorage--;

                                if (doorStorage <= 0) {
                                    cancel();
                                }
                            }

                            @Override
                            public void cancel() {
                                for (sabotageMenu menu : menus) {
                                    menu.inventory.setItem(31, doorOffset);
                                    doorStorageRunnable = null;
                                }
                                super.cancel();
                            }
                        }.runTaskTimer(AmongUs.plugin, 0, 20);
                    }
                }
                break;
            case 42:
                if (inventory.getItem(42).getType().equals(Material.MUSIC_DISC_13)) {
                    comsSabotaged = true;
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(4, doorCooldownOffset);
                        menu.inventory.setItem(10, doorCooldown);
                        menu.inventory.setItem(20, doorCooldown);
                        menu.inventory.setItem(21, doorCooldown);
                        menu.inventory.setItem(28, doorCooldown);
                        menu.inventory.setItem(29, doorCooldown);
                        menu.inventory.setItem(31, doorCooldownOffset);

                        menu.inventory.setItem(18, reactorCooldown);
                        menu.inventory.setItem(24, O2Cooldown);
                        menu.inventory.setItem(30, lightsCooldown);
                        menu.inventory.setItem(42, comsCooldown);
                    }
                    AmongUs.sabotage(sabotageType.sabotageComs);
                }
                break;

        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        menus.remove(this);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, map);

        if (doorCafeteria == 0) {
            inventory.setItem(4, doorOffset);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(4, doorCooldownOffset);
            }
        }
        inventory.setItem(5, invisible);
        inventory.setItem(13, invisible);
        inventory.setItem(14, invisible);

        if (doorUpperEngine == 0) {
            inventory.setItem(10, door);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(10, doorCooldown);
            }
        }

        if (doorSecurity == 0) {
            inventory.setItem(20, door);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(20, doorCooldown);
            }
        }

        if (doorMedbay == 0) {
            inventory.setItem(21, door);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(21, doorCooldown);
            }
        }

        if (doorLowerEngine == 0) {
            inventory.setItem(28, door);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(28, doorCooldown);
            }
        }

        if (doorElectrical == 0) {
            inventory.setItem(29, door);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(29, doorCooldown);
            }
        }

        if (doorStorage == 0) {
            inventory.setItem(31, doorOffset);
            if (checkSabotageTimes() || checkSabotages()) {
                inventory.setItem(31, doorCooldownOffset);
            }
        }
        inventory.setItem(32, invisible);
        inventory.setItem(40, invisible);
        inventory.setItem(41, invisible);

        if (sabotageReactor == 0) {
            inventory.setItem(18, reactor);
            if (reactorSabotaged || checkSabotages() || checkDoors()) {
                inventory.setItem(18, reactorCooldown);
            }
        }

        if (sabotageO2 == 0) {
            inventory.setItem(24, O2);
            if (o2Sabotaged || checkSabotages() || checkDoors()) {
                inventory.setItem(24, O2Cooldown);
            }
        }

        if (sabotageLights == 0) {
            inventory.setItem(30, lights);
            if (lightsSabotaged || checkSabotages() || checkDoors()) {
                inventory.setItem(30, lightsCooldown);
            }
        }

        if (sabotageComs == 0) {
            inventory.setItem(42, coms);
            if (comsSabotaged || checkSabotages() || checkDoors()) {
                inventory.setItem(42, comsCooldown);
            }
        }

        menus.add(this);
    }

    public static boolean checkDoors() {
        return doorStorage > 20 || doorCafeteria > 20 || doorElectrical > 20 || doorLowerEngine > 20 || doorMedbay > 20 || doorSecurity > 20 || doorUpperEngine > 20;
    }

    public static boolean checkSabotages() {
        return lightsSabotaged || comsSabotaged || o2Sabotaged || reactorSabotaged;
    }

    public static boolean checkSabotageTimes() {
        return sabotageLights > 0 || sabotageComs > 0 || sabotageO2 > 0 || sabotageReactor > 0;
    }

    public static void reactorComplete() {
        if (sabotageReactorRunnable == null) {
            AmongUs.sabotageType = null;
            sabotageReactor = 30;
            reactorSabotaged = false;
            AmongUs.sabotageRunnable.cancel();
            AmongUs.resetSabotage();
            sabotageReactorRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (sabotageMenu menu : menus) {
                        ItemStack item = reactorCooldown;
                        item.setAmount(sabotageReactor);
                        menu.inventory.setItem(18, item);

                        if (checkSabotages()) {
                            menu.inventory.setItem(4, doorCooldownOffset);
                            menu.inventory.setItem(10, doorCooldown);
                            menu.inventory.setItem(20, doorCooldown);
                            menu.inventory.setItem(21, doorCooldown);
                            menu.inventory.setItem(28, doorCooldown);
                            menu.inventory.setItem(29, doorCooldown);
                            menu.inventory.setItem(31, doorCooldownOffset);

                            menu.inventory.setItem(24, O2Cooldown);
                            menu.inventory.setItem(30, lightsCooldown);
                            menu.inventory.setItem(42, comsCooldown);
                        }
                    }
                    sabotageReactor--;

                    if (sabotageReactor <= 0) {
                        cancel();
                    }
                }

                @Override
                public void cancel() {
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(18, reactor);
                        sabotageReactorRunnable = null;
                        reactorSabotaged = false;

                        menu.inventory.setItem(4, doorOffset);
                        menu.inventory.setItem(10, door);
                        menu.inventory.setItem(20, door);
                        menu.inventory.setItem(21, door);
                        menu.inventory.setItem(28, door);
                        menu.inventory.setItem(29, door);
                        menu.inventory.setItem(31, doorOffset);

                        menu.inventory.setItem(24, O2);
                        menu.inventory.setItem(30, lights);
                        menu.inventory.setItem(42, coms);
                    }
                    super.cancel();
                }
            }.runTaskTimer(AmongUs.plugin, 0, 20);
        }
    }

    public static void o2Complete() {
        if (sabotageO2Runnable == null) {
            AmongUs.sabotageType = null;
            sabotageO2 = 30;
            o2Sabotaged = false;
            AmongUs.sabotageRunnable.cancel();
            AmongUs.resetSabotage();
            sabotageO2Runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (sabotageMenu menu : menus) {
                        ItemStack item = O2Cooldown;
                        item.setAmount(sabotageO2);
                        menu.inventory.setItem(24, item);

                        if (checkSabotages()) {
                            menu.inventory.setItem(4, doorCooldownOffset);
                            menu.inventory.setItem(10, doorCooldown);
                            menu.inventory.setItem(20, doorCooldown);
                            menu.inventory.setItem(21, doorCooldown);
                            menu.inventory.setItem(28, doorCooldown);
                            menu.inventory.setItem(29, doorCooldown);
                            menu.inventory.setItem(31, doorCooldownOffset);

                            menu.inventory.setItem(18, reactorCooldown);
                            menu.inventory.setItem(30, lightsCooldown);
                            menu.inventory.setItem(42, comsCooldown);
                        }
                    }
                    sabotageO2--;

                    if (sabotageO2 <= 0) {
                        cancel();
                    }
                }

                @Override
                public void cancel() {
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(24, O2);
                        sabotageO2Runnable = null;
                        o2Sabotaged = false;

                        menu.inventory.setItem(4, doorOffset);
                        menu.inventory.setItem(10, door);
                        menu.inventory.setItem(20, door);
                        menu.inventory.setItem(21, door);
                        menu.inventory.setItem(28, door);
                        menu.inventory.setItem(29, door);
                        menu.inventory.setItem(31, doorOffset);

                        menu.inventory.setItem(18, reactor);
                        menu.inventory.setItem(30, lights);
                        menu.inventory.setItem(42, coms);
                    }
                    super.cancel();
                }
            }.runTaskTimer(AmongUs.plugin, 0, 20);
        }
    }

    static void lightsComplete() {
        if (sabotageLightsRunnable == null) {
            AmongUs.sabotageType = null;
            sabotageLights = 30;
            lightsSabotaged = false;
            AmongUs.resetSabotage();
            sabotageLightsRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (sabotageMenu menu : menus) {
                        ItemStack item = lightsCooldown;
                        item.setAmount(sabotageLights);
                        menu.inventory.setItem(30, item);

                        if (checkSabotages()) {
                            menu.inventory.setItem(4, doorCooldownOffset);
                            menu.inventory.setItem(10, doorCooldown);
                            menu.inventory.setItem(20, doorCooldown);
                            menu.inventory.setItem(21, doorCooldown);
                            menu.inventory.setItem(28, doorCooldown);
                            menu.inventory.setItem(29, doorCooldown);
                            menu.inventory.setItem(31, doorCooldownOffset);

                            menu.inventory.setItem(18, reactorCooldown);
                            menu.inventory.setItem(24, O2Cooldown);
                            menu.inventory.setItem(42, comsCooldown);
                        }
                    }
                    sabotageLights--;

                    if (sabotageLights <= 0) {
                        cancel();
                    }
                }

                @Override
                public void cancel() {
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(30, lights);
                        sabotageLightsRunnable = null;
                        lightsSabotaged = false;

                        menu.inventory.setItem(4, doorOffset);
                        menu.inventory.setItem(10, door);
                        menu.inventory.setItem(20, door);
                        menu.inventory.setItem(21, door);
                        menu.inventory.setItem(28, door);
                        menu.inventory.setItem(29, door);
                        menu.inventory.setItem(31, doorOffset);

                        menu.inventory.setItem(18, reactor);
                        menu.inventory.setItem(24, O2);
                        menu.inventory.setItem(42, coms);
                    }
                    super.cancel();
                }
            }.runTaskTimer(AmongUs.plugin, 0, 20);
        }
    }

    static void comsComplete() {
        if (sabotageComsRunnable == null) {
            AmongUs.sabotageType = null;
            sabotageComs = 30;
            comsSabotaged = false;
            AmongUs.resetSabotage();
            sabotageComsRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (sabotageMenu menu : menus) {
                        ItemStack item = comsCooldown;
                        item.setAmount(sabotageComs);
                        menu.inventory.setItem(42, item);

                        if (checkSabotages()) {
                            menu.inventory.setItem(4, doorCooldownOffset);
                            menu.inventory.setItem(10, doorCooldown);
                            menu.inventory.setItem(20, doorCooldown);
                            menu.inventory.setItem(21, doorCooldown);
                            menu.inventory.setItem(28, doorCooldown);
                            menu.inventory.setItem(29, doorCooldown);
                            menu.inventory.setItem(31, doorCooldownOffset);

                            menu.inventory.setItem(18, reactorCooldown);
                            menu.inventory.setItem(24, O2Cooldown);
                            menu.inventory.setItem(30, lightsCooldown);
                        }
                    }
                    sabotageComs--;

                    if (sabotageComs <= 0) {
                        cancel();
                    }
                }

                @Override
                public void cancel() {
                    for (sabotageMenu menu : menus) {
                        menu.inventory.setItem(42, coms);
                        comsSabotaged = false;
                        sabotageComsRunnable = null;

                        menu.inventory.setItem(4, doorOffset);
                        menu.inventory.setItem(10, door);
                        menu.inventory.setItem(20, door);
                        menu.inventory.setItem(21, door);
                        menu.inventory.setItem(28, door);
                        menu.inventory.setItem(29, door);
                        menu.inventory.setItem(31, doorOffset);

                        menu.inventory.setItem(18, reactor);
                        menu.inventory.setItem(24, O2);
                        menu.inventory.setItem(30, lights);
                    }
                    super.cancel();
                }
            }.runTaskTimer(AmongUs.plugin, 0, 20);
        }
    }


}
