package me.melonboy10.us.maps;

import me.melonboy10.us.menuSystem.menus.sabotageMenu;
import me.melonboy10.us.utilities.AmongUs;
import me.melonboy10.us.utilities.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import static me.melonboy10.us.utilities.AmongUs.*;
import static me.melonboy10.us.utilities.Task.TaskType.*;

public class AdminMapRenderer extends MapRenderer {

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        MapCursorCollection cursors = new MapCursorCollection();

        canvas.drawImage(0, 0, adminMap);

        for (Player canvasPlayer : AmongUs.getPlayingPlayers()) {
            Location location = canvasPlayer.getLocation();
            location.setY(0);
//            if(
//                    (location.getBlockX() > -36 && location.getBlockX() < -28 && location.getBlockZ() < -70 && location.getBlockZ() > -80) ||
//                    (location.getBlockX() > -46 && location.getBlockX() < -38 && location.getBlockZ() < -90 && location.getBlockZ() > -94) ||
//                    (location.getBlockX() > -36 && location.getBlockX() < -28 && location.getBlockZ() < -96 && location.getBlockZ() > -104) ||
//                    (location.getBlockX() > -26 && location.getBlockX() < -22 && location.getBlockZ() < -92 && location.getBlockZ() > -83) ||
//                    (location.getBlockX() > -17 && location.getBlockX() < -9 && location.getBlockZ() < -72 && location.getBlockZ() > -87) ||
//                    (location.getBlockX() > -20 && location.getBlockX() < -9 && location.getBlockZ() < -87 && location.getBlockZ() > -95) ||
//                    (location.getBlockX() > -11 && location.getBlockX() < 10 && location.getBlockZ() < -90 && location.getBlockZ() > -110) ||
//                    (location.getBlockX() > 15 && location.getBlockX() < 22 && location.getBlockZ() > -104 && location.getBlockZ() < -96) ||
//                    (location.getBlockX() < 15 && location.getBlockX() > 8 && location.getBlockZ() > -93 && location.getBlockZ() < -87) ||
//                    (location.getBlockX() > 31 && location.getBlockX() < 37 && location.getBlockZ() > -91 && location.getBlockZ() < -83) ||
//                    (location.getBlockX() < 25 && location.getBlockX() > 17 && location.getBlockZ() < -78 && location.getBlockZ() > -69) ||
//                    (location.getBlockX() < 15 && location.getBlockX() > 6 && location.getBlockZ() > -70 && location.getBlockZ() < -63) ||
//                    (location.getBlockX() < 3 && location.getBlockX() > -7 && location.getBlockZ() < -64 && location.getBlockZ() > -80) ||
//                    (location.getBlockX() < 7 && location.getBlockX() > 15 && location.getBlockZ() < -78 && location.getBlockZ() > -85)
//            ) {
//                createMarker(location.getBlockX() + 1, location.getBlockZ() - 2, MapCursor.Type.GREEN_POINTER, cursors);
//            }
            if(world.getBlockAt(location.add(0, 0, -5)).getType().equals(Material.BLACK_WOOL)) {
                createMarker(location.getBlockX(), location.getBlockZ(), MapCursor.Type.GREEN_POINTER, cursors, canvas);
            }
        }

        canvas.setCursors(cursors);
    }

    private void createMarker(int x, int y, MapCursor.Type type, MapCursorCollection cursors, MapCanvas canvas) {
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
