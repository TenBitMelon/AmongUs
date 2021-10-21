package me.melonboy10.us.maps;

import me.melonboy10.us.menuSystem.menus.sabotageMenu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.Task;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import static me.melonboy10.us.utilities.AmongUs.crewMap;
import static me.melonboy10.us.utilities.Task.TaskType.*;

public class CrewMapRenderer extends MapRenderer {

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        MapCursorCollection cursors = new MapCursorCollection();

        canvas.drawImage(0, 0, crewMap);

        createMarker(player.getLocation().getBlockX(), player.getLocation().getBlockZ() - 4, MapCursor.Type.MANSION, cursors);
        if (!sabotageMenu.comsSabotaged) {
            for (Task task : AmongUs.getPlayerUtility(player).getTasks()) {
                if (!task.getTaskType().equals(ADMIN_PANEL) && !task.getTaskType().equals(Task.TaskType.BUTTON) && task.isPreviousComplete() && !AmongUs.getPlayerUtility(player).isImpostor()) {
                    if (task.getTaskType().equals(O2_SABOTAGE_1) || task.getTaskType().equals(O2_SABOTAGE_2) || task.getTaskType().equals(REACTOR_SABOTAGE) || task.getTaskType().equals(LIGHTS_SABOTAGE) || task.getTaskType().equals(COMMUNICATIONS_SABOTAGE)) {
                        createMarker(task.getLocation().getBlockX(), task.getLocation().getBlockZ(), MapCursor.Type.BANNER_RED, cursors);
                    } else {
                        createMarker(task.getLocation().getBlockX(), task.getLocation().getBlockZ(), MapCursor.Type.BANNER_YELLOW, cursors);
                    }
                }
            }
        }

        canvas.setCursors(cursors);
    }

    private void createMarker(int x, int y, MapCursor.Type type, MapCursorCollection cursors) {
        int output_startx = -128;
        int output_endx = 150;
        int input_startx = -47;
        int input_endx = 39;

        int output_starty = -75;
        int output_endy = 85;
        int input_starty = -111;
        int input_endy = -62;

        int xOut = output_startx + ((output_endx - output_startx) / (input_endx - input_startx)) * (x - input_startx);
        int yOut = output_starty + ((output_endy - output_starty) / (input_endy - input_starty)) * (y - input_starty);

        cursors.addCursor(xOut, yOut, (byte) 8, type.getValue());
    }
}
