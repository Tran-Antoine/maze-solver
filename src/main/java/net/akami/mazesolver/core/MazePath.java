package net.akami.mazesolver.core;

import net.akami.mazesolver.util.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MazePath implements Comparable<MazePath> {

    private Maze parent;
    private Vector2f location;
    private List<Vector2f> visitedLocations;
    private int length;
    private boolean reachedExit = false;
    private boolean lastTileToWalkOn = false;

    MazePath(Maze parent, Vector2f location) {
        this.parent = parent;
        this.visitedLocations = new ArrayList<>();
        setLocation(location);
        parent.addPath(this);
    }

    void spread() {
        List<Vector2f> locations = nextDirections();

        int i = 0;
        for(Vector2f next : locations) {
            if(i == locations.size()-1) lastTileToWalkOn = true;
            parent.getAt(next).walkOn(this, next);
            i++;
        }
        lastTileToWalkOn = false;
    }

    void addStep() {
        length++;
    }

    boolean singleTileLeft() {
        return lastTileToWalkOn;
    }

    void resetLastTile() {
        this.lastTileToWalkOn = false;
    }

    void reachExit() {
        reachedExit = true;
    }

    @Override
    public int compareTo(MazePath o) {
        if(!this.reachedExit) this.length = Integer.MAX_VALUE;
        if(!o.reachedExit) o.length = Integer.MAX_VALUE;

        return length - o.length;
    }

    void setLocation(Vector2f tileLocation) {
        this.location = tileLocation;
        visitedLocations.add(tileLocation);
    }

    MazePath copy(Vector2f newLocation) {
        MazePath copy = new MazePath(parent, newLocation);
        copy.length = this.length;
        copy.visitedLocations = new ArrayList<>(this.visitedLocations);
        return copy;
    }

    private List<Vector2f> nextDirections() {

        TileType[][] field = parent.getField();
        int x = location.x;
        int y = location.y;

        List<Vector2f> nextLocations = new ArrayList<>();

        if(x != 0) nextLocations.add(new Vector2f(x-1, y));
        if(y != 0) nextLocations.add(new Vector2f(x, y-1));
        if(x < field[y].length -1) nextLocations.add(new Vector2f(x+1, y));
        if(y < field.length -1) nextLocations.add(new Vector2f(x, y+1));

        nextLocations.removeAll(visitedLocations);

        return nextLocations
                .stream()
                .filter(loc -> parent.getAt(loc).isTreadable())
                .collect(Collectors.toList());
    }

    List<Vector2f> getVisitedLocations() {
        return visitedLocations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String[] lines = parent.toString().split("\n");

        List<Vector2f> finalPaths = new ArrayList<>(visitedLocations);
        finalPaths.remove(0);
        finalPaths.remove(finalPaths.size()-1);

        for(Vector2f position : finalPaths) {
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
                .append(length);

        return builder.toString();
    }
}
