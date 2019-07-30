package net.akami.mazesolver.util;

import net.akami.mazesolver.core.Maze;
import net.akami.mazesolver.core.TileType;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public final class MazeFactory {

    private MazeFactory() {}

    public static Maze createMaze(String path) throws IOException {

        File file = new File(path);
        if(!file.exists()) throw new IOException("Unable to locate file "+path);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());

        Vector2f start = null;
        int y = 0;
        TileType[][] fieldArray = new TileType[lines.size()][];

        for(String line : lines) {
            fieldArray[y] = new TileType[line.length()];
            int x = 0;
            for(char c : line.toCharArray()) {
                TileType match = TileType.of(c);
                fieldArray[y][x] = match;
                if(start == null && match == TileType.EXIT)
                    start = new Vector2f(x, y);
                x++;
            }
            y++;
        }

        return new Maze(fieldArray, start);
    }
}
