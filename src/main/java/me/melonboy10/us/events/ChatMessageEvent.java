package me.melonboy10.us.events;

import me.melonboy10.us.AmongUsPlugin;
import me.melonboy10.us.utilities.AmongUs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMessageEvent implements Listener {

    private AmongUsPlugin main = null;

    public ChatMessageEvent(AmongUsPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void ChatMessage(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

        if (AmongUs.getPlayerUtility(p).isDead()) {
            event.setCancelled(true);
            for (Player deadPlayer : AmongUs.getDeadPlayers()) {
                deadPlayer.sendMessage(ChatColor.GRAY + "<" + p.getDisplayName() + "> " + event.getMessage());
            }
        }
    }
}