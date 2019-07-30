package net.akami.mazesolver.core;

import net.akami.mazesolver.util.Vector2f;

import java.util.List;
import java.util.function.BiConsumer;

public enum TileType {

    EXIT('X', true, TileType::reachExit),
    AIR('0', true, TileType::reachAir),
    WALL('1', false, null);

    private char id;
    private boolean treadable;
    private BiConsumer<MazePath, Vector2f> walkEvent;

    TileType(char id, boolean treadable, BiConsumer<MazePath, Vector2f> walkEvent) {
        this.id = id;
        this.treadable = treadable;
        this.walkEvent = walkEvent;
    }

    public static TileType of(char id) {
        for(TileType tile : values()) {
            if(tile.id == id) return tile;
        }
        throw new IllegalArgumentException("Could not match the given char with any kind of tile");
    }

    public boolean isTreadable() {
        return treadable;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public void walkOn(MazePath path, Vector2f tileLocation) {
        walkEvent.accept(path, tileLocation);
    }

    private static void reachExit(MazePath path, Vector2f tileLocation) {
        MazePath current = setNewLocation(path, tileLocation);
        current.reachExit();
    }

    private static void reachAir(MazePath path, Vector2f tileLocation) {
        MazePath current = setNewLocation(path, tileLocation);
        current.spread();
    }

    private static MazePath setNewLocation(MazePath path, Vector2f tileLocation) {
        MazePath current;

        if(path.singleTileLeft()) {
            current = path;
            path.resetLastTile();
        }
        else {
            List<Vector2f> visitedLocations = path.getVisitedLocations();
            current = path.copy(visitedLocations.get(visitedLocations.size()-1));
        }

        current.addStep();
        current.setLocation(tileLocation);
        return current;
    }
}
