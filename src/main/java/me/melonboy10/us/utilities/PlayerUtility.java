package me.melonboy10.us.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.melonboy10.us.AmongUsPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static me.melonboy10.us.utilities.AmongUs.InteractionSetType.K;
import static me.melonboy10.us.utilities.AmongUs.checkWinConditions;
import static me.melonboy10.us.utilities.AmongUs.world;

public class PlayerUtility {

    private final Player owner;
    public enum Camera {SECURITY, MEDBAY, ADMIN, NAVIGATION, NONE}

    private AmongUs.Colors color;
    private boolean dead;
    private boolean impostor;
    private boolean inVent;
    private boolean inCamera;
    private Camera camera = Camera.NONE;
    private Sheep sheep;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private Task selected;
    private int emergencyMeetings;

    public boolean isInCamera() {
        return inCamera;
    }

    public void setInCamera(boolean inCamera) {
        this.inCamera = inCamera;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private AmongUs.InteractionItemType currentInteraction = AmongUs.InteractionItemType.R_U;
//    private static ArrayList<ArmorStand> visiblePlayers = new ArrayList<>();

    public PlayerUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public AmongUs.Colors getColor() {
        return color;
    }

    public void setColor(AmongUs.Colors newColor) {
        color = newColor;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean state) {
        dead = state;
    }

    public boolean isImpostor() {
        return impostor;
    }

    public void setImpostor(boolean state) {
        impostor = state;
    }

    public boolean isInVent() {
        return inVent;
    }

    public void setInVent(boolean state) {
        inVent = state;
    }

    public Sheep getSheep() {
        return sheep;
    }

    public void setSheep(Sheep newSheep) {
        sheep = newSheep;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void clearTasks() {
        tasks.clear();
    }

    public AmongUs.InteractionItemType getInteractionType() {
        return currentInteraction;
    }

    public void setInteractionType(AmongUs.InteractionItemType interaction) {
        currentInteraction = interaction;
    }

    public int getEmergencyMeetings() {
        return emergencyMeetings;
    }

    public void setEmergencyMeetings(int emergencyMeetings) {
        this.emergencyMeetings = emergencyMeetings;
    }

    public Task getSelected() {
        return selected;
    }

    public void setSelected(Task selected) {
        if (selected.getArmorStand() != null) {
            ArmorStand selectedArmorStand = selected.getArmorStand();

            PacketContainer packet = AmongUsPlugin.getManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
            packet.getIntegers().write(0, selectedArmorStand.getEntityId()); //Set packet's entity id
            WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
            WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
            watcher.setEntity(selectedArmorStand); //Set the new data watcher's target
            watcher.setObject(0, serializer, (byte) (0x60)); //Set status to glowing, found on protocol page
            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

            try {
                AmongUsPlugin.getManager().sendServerPacket(this.owner, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Sheep selectedArmorStand = selected.getSheep();

            PacketContainer packet = AmongUsPlugin.getManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
            packet.getIntegers().write(0, selectedArmorStand.getEntityId()); //Set packet's entity id
            WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
            WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
            watcher.setEntity(selectedArmorStand); //Set the new data watcher's target
            watcher.setObject(0, serializer, (byte) (0x40)); //Set status to glowing, found on protocol page
            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

            try {
                AmongUsPlugin.getManager().sendServerPacket(this.owner, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        this.selected = selected;
    }

    public void removeSelected() {
        if (selected != null) {
            if (selected.getArmorStand() != null) {
                ArmorStand selectedArmorStand = selected.getArmorStand();

                PacketContainer packet = AmongUsPlugin.getManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
                packet.getIntegers().write(0, selectedArmorStand.getEntityId()); //Set packet's entity id
                WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
                WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
                watcher.setEntity(selectedArmorStand); //Set the new data watcher's target
                watcher.setObject(0, serializer, (byte) (0x20)); //Set status to glowing, found on protocol page
                packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

                try {
                    AmongUsPlugin.getManager().sendServerPacket(this.owner, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                Sheep selectedArmorStand = selected.getSheep();

                PacketContainer packet = AmongUsPlugin.getManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
                packet.getIntegers().write(0, selectedArmorStand.getEntityId()); //Set packet's entity id
                WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
                WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
                watcher.setEntity(selectedArmorStand); //Set the new data watcher's target
                watcher.setObject(0, serializer, (byte) (0x00)); //Set status to glowing, found on protocol page
                packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

                try {
                    AmongUsPlugin.getManager().sendServerPacket(this.owner, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            selected = null;
        }
    }

    public void kill(Player killer) {
        setDead(true);
        AmongUs.getDeadPlayers().add(owner);
        owner.closeInventory();
        owner.playSound(owner.getLocation(), "minecraft:death", 1, 1);
        for (int k = -57; k < 50; k++) {
            for (int j = -122; j < -50; j++) {
                owner.sendBlockChange(new Location(world, k, 7, j), Material.AIR.createBlockData());
            }
        }

        for (Player player : AmongUs.getPlayingPlayers()) {
            if (!AmongUs.getPlayerUtility(player).isDead()) {
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                packet.getIntegerArrays().write(0, new int[]{sheep.getEntityId()});
                try {
                    AmongUsPlugin.getManager().sendServerPacket(player, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (killer != null) {
                    ArmorStand bodyStand = (ArmorStand) sheep.getWorld().spawnEntity(sheep.getLocation().add(0, -0.5, 0), EntityType.ARMOR_STAND);
                    bodyStand.setGravity(false);
                    bodyStand.setVisible(false);
                    bodyStand.setArms(true);

                    bodyStand.setRightLegPose(new EulerAngle(1.588, 0, 0));
                    bodyStand.setLeftLegPose(new EulerAngle(1.588, 0, 0));
                    bodyStand.setHeadPose(new EulerAngle(3.141, 0, 0));

                    ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
                    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta leatherMeta = (LeatherArmorMeta) legs.getItemMeta();
                    leatherMeta.setColor(AmongUs.colorToColorCode.get(AmongUs.getPlayerUtility(owner).getColor()));
                    legs.setItemMeta(leatherMeta);
                    boots.setItemMeta(leatherMeta);

                    bodyStand.getEquipment().setLeggings(legs); //Edit the pose
                    bodyStand.getEquipment().setBoots(boots);
                    bodyStand.getEquipment().setHelmet(new ItemStack(Material.BONE));
                    bodyStand.setCustomName(owner.getDisplayName());
                    bodyStand.setCustomNameVisible(false);

                    Task body = new Task(Task.TaskType.BODY, sheep.getLocation(), player, bodyStand);

                }
                if (AmongUs.getPlayerUtility(player).isImpostor()) {
                    AmongUs.updateInteraction(player, K);
                    player.setCooldown(Material.DIAMOND_HOE, 20 * AmongUs.killCooldown);
                    player.setCooldown(Material.FILLED_MAP, 20 * AmongUs.killCooldown);
                    ArrayList<Task> playerTasks = AmongUs.getPlayerUtility(player).getTasks();
                    for (int i = playerTasks.size(); i > 0; i--) {
                        if (playerTasks.get(i - 1).getSheep() != null) {
                            if (playerTasks.get(i - 1).getSheep().equals(sheep)) {
                                AmongUs.getPlayerUtility(player).removeTask(playerTasks.get(i - 1));
                                AmongUs.getPlayerUtility(player).removeSelected();
                            }
                        }
                    }
                }
            }
        }

        if (killer != null) {
            owner.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
            owner.sendTitle(AmongUs.colorToChat.get(AmongUs.getPlayerUtility(killer).getColor()) + "\uE010 " + AmongUs.colorToChat.get(AmongUs.getPlayerUtility(owner).getColor()) + "\uE011", "", 0, 40, 0);

            new BukkitRunnable() {
                @Override
                public void run() {
                    owner.sendTitle(AmongUs.colorToChat.get(AmongUs.getPlayerUtility(killer).getColor()) + "\uE010 " + AmongUs.colorToChat.get(AmongUs.getPlayerUtility(owner).getColor()) + "\uE012", "", 0, 20, 0);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            owner.getInventory().setHelmet(new ItemStack(Material.AIR));
                        }
                    }.runTaskLater(AmongUs.plugin, 20);
                }
            }.runTaskLater(AmongUs.plugin, 30);
            checkWinConditions();
        }

        // owner.sendMessage("┤╡╢╖╕╣║╗╝\n╜╛┐└┴┬├─┼\n╞╟╚╔╩╦╠═╬"); //quick note: theses are the alert back groudn charachters┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬

    }
}
