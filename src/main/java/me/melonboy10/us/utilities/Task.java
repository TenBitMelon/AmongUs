package me.melonboy10.us.utilities;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import static me.melonboy10.us.utilities.AmongUs.checkWinConditions;

public class Task {

    public enum TaskType {
        BUTTON,
        PLAYER,
        BODY,
        O2_SABOTAGE_1,
        O2_SABOTAGE_2,
        REACTOR_SABOTAGE,
        LIGHTS_SABOTAGE,
        COMMUNICATIONS_SABOTAGE,
        FIX_WIRES,
        EMPTY_GARBAGE,
        EMPTY_GARBAGE_MAIN, //the big one in storage
        DOWNLOAD_DATA,
        DIVERT_POWER,
        DIVERT_POWER_MAIN,
        CALIBRATE_DISTRIBUTOR,
        ALIGN_ENGINE_OUTPUT,
        INSPECT_SAMPLE,
        SUBMIT_SCAN,
        CHART_COURSE,
        STABILIZE_STEERING,
        CLEAN_O2_FILTER,
        START_REACTOR,
        UNLOCK_MANIFOLDS,
        PRIME_SHIELDS,
        FUEL_ENGINES,
        GATHER_FUEL,
        CLEAR_ASTEROIDS,
        UPLOAD_DATA,
        CARD_SWIPE,
        SECURITY_VENT,
        MEDBAY_VENT,
        ELECTRICAL_VENT,
        REACTOR_TOP_VENT,
        REACTOR_BOTTOM_VENT,
        NAVIGATION_TOP_VENT,
        NAVIGATION_BOTTOM_VENT,
        UPPER_ENGINE_VENT,
        LOWER_ENGINE_VENT,
        ADMIN_VENT,
        CAFETERIA_VENT,
        O2_VENT, // in hallway
        WEAPONS_VENT,
        SHIELDS_VENT,
        SECURITY_CAMERA_VIEW,
        ADMIN_PANEL
    }

    private final Location location;
    private final ArmorStand armorStand;
    private final Sheep sheep;
    private final Player owner;
    private final TaskType taskType;
    boolean complete;
    Task previousTask;


    public Task(TaskType task, Location loc, Player player, ArmorStand stand, Task... previousTask) {
        owner = player;
        location = loc;
        sheep = null;

        if (stand != null) {
            armorStand = stand;
            armorStand.teleport(location);
        } else {
            armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
        }

        taskType = task;
        complete = false;

        if (previousTask.length == 0) {
            this.previousTask = null;
        } else {
            this.previousTask = previousTask[0];
        }

        AmongUs.getPlayerUtility(player).addTask(this);
    }

    public Task(TaskType task, Location loc, Player player, Task... previousTask) {
        owner = player;
        location = loc;

        armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        sheep = null;

        taskType = task;
        complete = false;

        if (previousTask.length == 0) {
            this.previousTask = null;
        } else {
            this.previousTask = previousTask[0];
        }

        AmongUs.getPlayerUtility(player).addTask(this);
    }

    public Task(TaskType task, Location loc, Player player, Sheep newSheep, Task... previousTask) {
        owner = player;
        location = loc;
        sheep = newSheep;
        armorStand = null;

        taskType = task;
        complete = false;

        if (previousTask.length == 0) {
            this.previousTask = null;
        } else {
            this.previousTask = previousTask[0];
        }

        AmongUs.getPlayerUtility(player).addTask(this);
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setComplete() {
        owner.playSound(owner.getLocation(), "minecraft:task_completed", 1, 1);
        AmongUs.getPlayerUtility(getOwner()).removeSelected();

        AmongUs.getPlayerUtility(owner).removeTask(this);
        AmongUs.removeTask(this);
        complete = true;
        remove();

        AmongUs.updateInteraction(getOwner(), AmongUs.InteractionSetType.U);
        AmongUs.updateBossBar();
//        checkWinConditions();
    }

    public boolean isPreviousComplete() {
        if (previousTask == null)
            return true;
        else
            return previousTask.complete;
    }

    public void remove() {
        if (armorStand != null)
            armorStand.remove();
        else
            sheep.remove();
    }

    public Location getLocation() {
        if (taskType.equals(TaskType.PLAYER)) {
            if (armorStand != null)
                return armorStand.getLocation();
            else
                return sheep.getLocation();
        } else {
            return location;
        }
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Sheep getSheep() {
        return sheep;
    }

    public Player getOwner() {
        return owner;
    }

}
