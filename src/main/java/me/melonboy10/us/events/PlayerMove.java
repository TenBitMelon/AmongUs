package me.melonboy10.us.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.PlayerUtility;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;

public class PlayerMove implements Listener {

    private AmongUsPlugin main = null;

    public PlayerMove(AmongUsPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (AmongUs.canPlayerMove()) {
            Player player = event.getPlayer();
            Vector playerMovement = event.getTo().clone().subtract(event.getFrom()).toVector();
            Location armorStandLocation = player.getLocation().add(0, -16, -5);
            Sheep sheep = AmongUs.getPlayerUtility(player).getSheep();
            int viewDistance = AmongUs.crewmateVision;
            if (AmongUs.getPlayerUtility(player).isImpostor()) {
                viewDistance = (int) (AmongUs.viewDistance * AmongUs.impostorVision);
            }
            if(AmongUs.getPlayerUtility(player).isDead()) {
                viewDistance = (AmongUs.viewDistance * 2);
            }

            if(!AmongUs.getPlayerUtility(player).isInVent() && !AmongUs.getPlayerUtility(player).isInCamera()) {
                sheep.teleport(armorStandLocation);
            }

            if (playerMovement.getX() > 0) {
                sheep.setRotation(-90, 0);
            } else if (playerMovement.getX() < 0) {
                sheep.setRotation(90, 0);
            } else if (playerMovement.getZ() > 0) {
                sheep.setRotation(0, 0);
            } else if (playerMovement.getZ() < 0) {
                sheep.setRotation(180, 0);
            }

            if (player.isSprinting()) player.setSprinting(false);

            if ((event.getFrom().getPitch() != ((float) 75)) && (event.getFrom().getPitch() != ((float) -180))) {
                Location playerLocation = event.getTo();
                playerLocation.setPitch((float) 75);
                playerLocation.setYaw((float) -180);
                player.teleport(playerLocation, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            }

            if (!event.getFrom().getBlock().equals(event.getTo().getBlock()) && !AmongUs.getPlayerUtility(player).isDead()) {
                checkAroundPlayer(player);
                checkPlayerInteractions(player);
                for (Entity ent : player.getNearbyEntities(viewDistance, 2, viewDistance)) {
                    if (ent instanceof Player) {
                        Player closePlayer = (Player) ent;
                        if (!AmongUs.getPlayerUtility(closePlayer).isDead()) {
                            checkAroundPlayer(closePlayer);
                        }
                    }
                }
            }

            for (Entity ent : player.getNearbyEntities(viewDistance, 2, viewDistance)) {
                if (ent instanceof Player) {
                    Player tempPlayer = (Player) ent;
                    checkPlayerInteractions(tempPlayer);
                }
            }
        }
    }

    private void checkPlayerInteractions(Player tempPlayer) {
        final PlayerUtility playerUtility = AmongUs.getPlayerUtility(tempPlayer);
        final Location tempArmorStandLocation = playerUtility.getSheep().getLocation();
        for (Task task : playerUtility.getTasks()) {
            if (task.getLocation().distanceSquared(tempArmorStandLocation) <= 5 && playerUtility.getSelected() == null) {
                if (task.getTaskType().equals(Task.TaskType.PLAYER) && !playerUtility.isDead()/* && player.getCooldown(Material.DIAMOND_HOE) > 0*/) {
                    playerUtility.setSelected(task);
                    AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.KA);
                } else if (task.getTaskType().equals(Task.TaskType.BODY) && !playerUtility.isDead()) {
                    playerUtility.setSelected(task);
                    AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.RA);
                } else if (task.isPreviousComplete() && !(playerUtility.isDead() && task.getTaskType().equals(Task.TaskType.BUTTON))) {
                    playerUtility.setSelected(task);
                    AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.UA);
                }
            } else if (task.getLocation().distanceSquared(tempArmorStandLocation) > 5) {
                if (playerUtility.getSelected() == task) {
                    if (task.getTaskType().equals(Task.TaskType.PLAYER)) {
                        AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.K);
                        playerUtility.removeSelected();
                    } else if (task.getTaskType().equals(Task.TaskType.BODY)) {
                        AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.R);
                        playerUtility.removeSelected();
                    } else {
                        playerUtility.removeSelected();
                        AmongUs.updateInteraction(tempPlayer, AmongUs.InteractionSetType.U);
                    }
                }
            }
        }
    }

    public static void checkAroundPlayer(Player player) {
        Location location = new Location(player.getWorld(), player.getLocation().getBlockX() + 0.5, player.getLocation().getBlockY(), player.getLocation().getBlockZ() + 0.5);
        ArrayList<Double> blackList = new ArrayList<>();
        Sheep sheep = AmongUs.getPlayerUtility(player).getSheep();
        int viewDistance = AmongUs.crewmateVision;
        if (AmongUs.getPlayerUtility(player).isImpostor())
            viewDistance = (int) (AmongUs.viewDistance * AmongUs.impostorVision);

        for (int t = 0; t < viewDistance; t++) {
            for (double i = 0; i < 2 * Math.PI; i = i + Math.PI / 32) {
                double x = t * Math.cos(i);
                double y = 0.5;
                double z = t * Math.sin(i);
                location.add(x, y, z);
                Location glassLocation = location.clone().add(0, -13, -5);

                if (location.getBlock().getType().equals(Material.BARRIER) || t >= viewDistance - 2 || blackList.contains(i)) {
                    player.sendBlockChange(glassLocation, Material.BLACK_STAINED_GLASS.createBlockData());
                    blackList.add(i);
//                    world.spawnParticle(Particle.SOUL, glassLocation, 1, 0, 0, 0, 0);
                    if (t > viewDistance / 2) {
                        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                        for (Entity entity : AmongUs.world.getNearbyEntities(glassLocation.add(0, -3, 0), 1, 2, 1)) {
                            if (entity instanceof Sheep && entity != sheep) {
                                packet.getIntegerArrays().write(0, new int[]{entity.getEntityId()});
                                try {
                                    AmongUsPlugin.getManager().sendServerPacket(player, packet);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } else if (location.getBlock().getType().equals(Material.AIR)) {
                    player.sendBlockChange(glassLocation, Material.AIR.createBlockData());

                    if (t > viewDistance / 2) {
                        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
                        PacketContainer Metapacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                        for (Entity entity : AmongUs.world.getNearbyEntities(glassLocation.add(0, -3, 0), 1, 2, 1)) {
                            if (entity instanceof Sheep && entity != sheep) {
                                if (!AmongUs.getPlayerUtility(Bukkit.getPlayer(entity.getCustomName())).isDead()) {
                                    packet.getIntegers().write(0, entity.getEntityId());
                                    packet.getUUIDs().write(0, entity.getUniqueId());
                                    packet.getIntegers().write(1, 69);
                                    packet.getDoubles().write(0, entity.getLocation().getX());
                                    packet.getDoubles().write(1, entity.getLocation().getY());
                                    packet.getDoubles().write(2, entity.getLocation().getZ());
                                    packet.getBytes().write(0, (byte) (entity.getLocation().getYaw() * 256.0F / 360.0F));
                                    packet.getBytes().write(1, (byte) (entity.getLocation().getPitch() * 256.0F / 360.0F));

                                    Metapacket.getIntegers().write(0, entity.getEntityId()); //Set packet's entity id
                                    WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
                                    WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
//                                            WrappedDataWatcher.Serializer stringSerializer = WrappedDataWatcher.Registry.get(WrappedChatComponent.class); //Found this through google, needed for some stupid reason
                                    WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class); //Found this through google, needed for some stupid reason
                                    watcher.setEntity(entity); //Set the new data watcher's target
                                    if (AmongUs.getPlayerUtility(Bukkit.getPlayer(entity.getCustomName())).isInVent())
                                        watcher.setObject(0, byteSerializer, (byte) 0x20); //Set status to invis, found on protocol page
                                    Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(entity.getCustomName())[0].getHandle());
                                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
//                                            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, stringSerializer), "jeb");
                                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, booleanSerializer), true); //needed to combine because of boolean constructors
                                    watcher.setObject(16, byteSerializer, AmongUs.colorToSheepPacketColor.get(((Sheep) entity).getColor())); //Set status to glowing, found on protocol page

                                    Metapacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

                                    try {
                                        AmongUsPlugin.getManager().sendServerPacket(player, packet);
                                        AmongUsPlugin.getManager().sendServerPacket(player, Metapacket);
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }

                location.subtract(x, y, z);
            }
        }
    }

    public static void checkAroundPlayer(Player player, Location location) {
        ArrayList<Double> blackList = new ArrayList<>();
        Sheep sheep = AmongUs.getPlayerUtility(player).getSheep();
        int viewDistance = 20;

        for (int t = 0; t < viewDistance; t++) {
            for (double i = 0; i < 2 * Math.PI; i = i + Math.PI / 32) {
                double x = t * Math.cos(i);
                double y = 0.5;
                double z = t * Math.sin(i);
                location.add(x, y, z);
                Location glassLocation = location.clone().add(0, -13, -5);

                if (location.getBlock().getType().equals(Material.BARRIER) || t >= viewDistance - 2 || blackList.contains(i)) {
                    player.sendBlockChange(glassLocation, Material.BLACK_STAINED_GLASS.createBlockData());
                    if(!AmongUs.getPlayerUtility(player).isDead()) {
                        blackList.add(i);
                    }
//                    world.spawnParticle(Particle.SOUL, glassLocation, 1, 0, 0, 0, 0);
                    if (t > viewDistance / 2 && !AmongUs.getPlayerUtility(player).isDead()) {
                        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                        for (Entity entity : AmongUs.world.getNearbyEntities(glassLocation.add(0, -3, 0), 1, 2, 1)) {
                            if ((entity instanceof Sheep && entity != sheep) || (entity instanceof ArmorStand && ((ArmorStand) entity).getEquipment().getHelmet().getType().equals(Material.BONE))) {
                                packet.getIntegerArrays().write(0, new int[]{entity.getEntityId()});
                                try {
                                    AmongUsPlugin.getManager().sendServerPacket(player, packet);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } else if (location.getBlock().getType().equals(Material.AIR)) {
                    player.sendBlockChange(glassLocation, Material.AIR.createBlockData());

                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
                    PacketContainer Metapacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                    for (Entity entity : AmongUs.world.getNearbyEntities(glassLocation.add(0, -3, 0), 1, 2, 1)) {
                        if (entity instanceof Sheep && entity != sheep) {
                            if (!AmongUs.getPlayerUtility(Bukkit.getPlayer(entity.getCustomName())).isDead()) {
                                packet.getIntegers().write(0, entity.getEntityId());
                                packet.getUUIDs().write(0, entity.getUniqueId());
                                packet.getIntegers().write(1, 69);
                                packet.getDoubles().write(0, entity.getLocation().getX());
                                packet.getDoubles().write(1, entity.getLocation().getY());
                                packet.getDoubles().write(2, entity.getLocation().getZ());
                                packet.getBytes().write(0, (byte) (entity.getLocation().getYaw() * 256.0F / 360.0F));
                                packet.getBytes().write(1, (byte) (entity.getLocation().getPitch() * 256.0F / 360.0F));

                                Metapacket.getIntegers().write(0, entity.getEntityId()); //Set packet's entity id
                                WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
                                WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
//                                            WrappedDataWatcher.Serializer stringSerializer = WrappedDataWatcher.Registry.get(WrappedChatComponent.class); //Found this through google, needed for some stupid reason
                                WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class); //Found this through google, needed for some stupid reason
                                watcher.setEntity(entity); //Set the new data watcher's target
                                if (AmongUs.getPlayerUtility(Bukkit.getPlayer(entity.getCustomName())).isInVent())
                                    watcher.setObject(0, byteSerializer, (byte) 0x20); //Set status to invis, found on protocol page
                                Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(entity.getCustomName())[0].getHandle());
                                watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
//                                            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, stringSerializer), "jeb");
                                watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, booleanSerializer), true); //needed to combine because of boolean constructors
                                watcher.setObject(16, byteSerializer, AmongUs.colorToSheepPacketColor.get(((Sheep) entity).getColor())); //Set status to glowing, found on protocol page

                                Metapacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created

                                try {
                                    AmongUsPlugin.getManager().sendServerPacket(player, packet);
                                    AmongUsPlugin.getManager().sendServerPacket(player, Metapacket);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                location.subtract(x, y, z);
            }
        }
    }
}




