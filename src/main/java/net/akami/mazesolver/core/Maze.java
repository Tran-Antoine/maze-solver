package net.akami.mazesolver.core;

import net.akami.mazesolver.util.Vector2f;

import java.util.*;

public class Maze {

    private final TileType[][] field;
    private final Vector2f start;
    private final List<MazePath> paths;

    public Maze(TileType[][] field, Vector2f start) {
        this.field = field;
        this.start = Objects.requireNonNull(start, "Could not find a start for the maze");
        this.paths = new ArrayList<>();
    }

    public MazePath solve() {
        MazePath initialPath = new MazePath(this, start);
        initialPath.spread();
        return shortestPath();
    }

    private MazePath shortestPath() {
        Collections.sort(paths);
        return paths.get(0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(TileType[] line : field) {
            for(TileType tile : line) {
                builder.append(tile);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    void addPath(MazePath path) {
        paths.add(path);
    }

    TileType getAt(Vector2f position) {
        return field[position.y][position.x];
    }

    TileType[][] getField() {
        return field;
    }
}
