package net.akami.mazesolver.util;

import net.akami.mazesolver.core.MazePath;

import java.util.ArrayList;
import java.util.List;

public class MazePrinter {

    public static String printMazePath(MazePath self) {
        StringBuilder builder = new StringBuilder();
        String[] lines = self.getMazeToString().split("\n");

        List<Vector2f> finalLocations = new ArrayList<>(self.getVisitedLocations());
        finalLocations.remove(0);
        finalLocations.remove(finalLocations.size()-1);

        for(Vector2f position : finalLocations) {
            char[] line = lines[position.y].toCharArray();
            line[position.x] = '.';
            lines[position.y] = new String(line);
        }
        for(String line : lines) {
            builder.append(line).append("\n");
        }
        builder
                .append("\n")
                .append("Distance between the start and the arrival (starting tile excluded) : ")
                .append(self.length());

        return builder.toString();
    }
}
