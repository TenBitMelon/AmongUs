package me.melonboy10.us;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.melonboy10.us.commands.TabComplete;
import me.melonboy10.us.commands.UtilsCommand;
import me.melonboy10.us.events.ChatMessageEvent;
import me.melonboy10.us.events.ClickEvents;
import me.melonboy10.us.events.PlayerJoin;
import me.melonboy10.us.events.PlayerMove;
import me.melonboy10.us.menuSystem.MenuListener;
import me.melonboy10.us.utilities.AmongUs;
import org.bukkit.plugin.java.JavaPlugin;

public class AmongUsPlugin extends JavaPlugin {

    private static AmongUsPlugin plugin;
    private ProtocolManager protocolManager;

    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        registerCommands();
        registerEvents();

        AmongUs.setUpForServer();


//        new BukkitRunnable(){
//            public void run() {
//                System.out.println("----------------");
//                System.out.println(doorCafeteria);
//                System.out.println(doorStorage);
//                System.out.println(doorElectrical);
//                System.out.println(doorUpperEngine);
//                System.out.println(doorLowerEngine);
//                System.out.println(doorSecurity);
//                System.out.println(doorMedbay);
//                System.out.println(sabotageReactor);
//                System.out.println(sabotageLights);
//                System.out.println(sabotageComs);
//                System.out.println(sabotageO2);
//                System.out.println(sabotageMenu.checkDoors());
//                System.out.println(sabotageMenu.checkSabotages());
//            }
//        }.runTaskTimer(AmongUs.plugin, 0, 20);
    }

    public void onDisable() {
        AmongUs.stopForServer();
    }

    private void registerCommands() {
        getCommand("utils").setExecutor(new UtilsCommand(this));

        getCommand("utils").setTabCompleter(new TabComplete(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new ChatMessageEvent(this), this);
        getServer().getPluginManager().registerEvents(new ClickEvents(this), this);
//        getServer().getPluginManager().registerEvents(new mapRenderer(), this);

    }

    public static AmongUsPlugin getPlugin() {
        return plugin;
    }

    public static ProtocolManager getManager() {
        return plugin.protocolManager;
    }

}
