package me.melonboy10.us.maps;

import me.melonboy10.us.menuSystem.menus.sabotageMenu;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import static me.melonboy10.us.utilities.AmongUs.sabotageMap;

public class SabotageMapRenderer extends MapRenderer {

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        MapCursorCollection cursors = new MapCursorCollection();
        /*
        orange: O2
        gray: O2Cooldown
        magenta: coms
        light_gray: comsCooldown
        light_blue: lights
        cyan: lightsCooldown
        lime: reactor
        purple: reactorCooldown
        pink: door
        blue: doorCooldown
         */

        canvas.drawImage(0, 0, sabotageMap);

        if (sabotageMenu.doorCafeteria > 0 || sabotageMenu.checkSabotages()) {
            createMarker(0, -100, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(0, -100, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorStorage > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-2, -71, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-2, -71, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorUpperEngine > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-32, -100, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-32, -100, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorLowerEngine > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-32, -75, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-32, -75, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorElectrical > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-17, -78, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-17, -78, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorSecurity > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-24, -87, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-24, -87, MapCursor.Type.BANNER_PINK, cursors); //door
        }
        if (sabotageMenu.doorMedbay > 0 || sabotageMenu.checkSabotages()) {
            createMarker(-15, -91, MapCursor.Type.BANNER_BLUE, cursors); //door
        } else {
            createMarker(-15, -91, MapCursor.Type.BANNER_PINK, cursors); //door
        }

        if (sabotageMenu.reactorSabotaged || sabotageMenu.checkSabotages() || sabotageMenu.checkDoors()) {
            createMarker(-42, -87, MapCursor.Type.BANNER_PURPLE, cursors); //reactor
        } else {
            createMarker(-42, -87, MapCursor.Type.BANNER_LIME, cursors); //reactor
        }
        if (sabotageMenu.o2Sabotaged || sabotageMenu.checkSabotages() || sabotageMenu.checkDoors()) {
            createMarker(12, -89, MapCursor.Type.BANNER_GRAY, cursors); //o2
        } else {
            createMarker(12, -89, MapCursor.Type.BANNER_ORANGE, cursors); //o2
        }
        if (sabotageMenu.comsSabotaged || sabotageMenu.checkSabotages() || sabotageMenu.checkDoors()) {
            createMarker(11, -66, MapCursor.Type.BANNER_LIGHT_GRAY, cursors); //coms
        } else {
            createMarker(11, -66, MapCursor.Type.BANNER_MAGENTA, cursors); //coms
        }
        if (sabotageMenu.lightsSabotaged || sabotageMenu.checkSabotages() || sabotageMenu.checkDoors()) {
            createMarker(-11, -78, MapCursor.Type.BANNER_CYAN, cursors); //lights
        } else {
            createMarker(-11, -78, MapCursor.Type.BANNER_LIGHT_BLUE, cursors); //lights
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
