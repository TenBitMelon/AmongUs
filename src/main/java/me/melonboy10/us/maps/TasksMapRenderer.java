package me.melonboy10.us.maps;

import me.melonboy10.us.menuSystem.menus.sabotageMenu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.Task;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static me.melonboy10.us.utilities.AmongUs.crewMap;
import static me.melonboy10.us.utilities.AmongUs.getPlayerUtility;
import static me.melonboy10.us.utilities.Task.TaskType.*;

public class TasksMapRenderer extends MapRenderer {

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {

        ArrayList<String> text = new ArrayList<>();
        if(getPlayerUtility(player).isImpostor())
            text.add("Sabotage and kill all crewmates.");

        for(Task task : getPlayerUtility(player).getTasks()) {
            if(!task.getTaskType().equals(BUTTON) && !task.getTaskType().equals(ADMIN_PANEL) && !task.getTaskType().equals(BODY) && !task.getTaskType().equals(PLAYER) && !task.getTaskType().equals(SECURITY_CAMERA_VIEW) && !task.getTaskType().toString().contains("VENT")) {
                text.add(task.getTaskType().toString().replace("_", " "));
            }
        }

        for (int i = -256; i < 256; i++) {
            for (int j = -256; j < 256; j++) {
                canvas.setPixel(i, j, (byte) 0x00);
            }
        }
        text.forEach(text1 -> canvas.drawText(0, text.indexOf(text1) * 10, MinecraftFont.Font, text1));
    }
}
