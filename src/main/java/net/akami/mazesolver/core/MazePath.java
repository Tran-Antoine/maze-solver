package net.akami.mazesolver.core;

import net.akami.mazesolver.util.MazePrinter;
import net.akami.mazesolver.util.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MazePath implements Comparable<MazePath> {

    private final Maze parent;
    private Vector2f location;
    private final List<Vector2f> visitedLocations;
    private boolean reachedExit = false;
    private boolean singleTileLeft = false;

    MazePath(Maze parent, Vector2f location) {
        this(parent, location, new ArrayList<>());
    }

    MazePath(Maze parent, Vector2f location, List<Vector2f> previousLocations) {
        this.parent = parent;
        this.visitedLocations = previousLocations;
        setLocation(location);
        parent.addPath(this);
    }

    void spread() {
        List<Vector2f> locations = nextDirections();

        int i = 0;
        for(Vector2f next : locations) {
            if(i == locations.size()-1) singleTileLeft = true;
            parent.getAt(next).walkOn(this, next);
            i++;
        }
        singleTileLeft = false;
    }

    boolean singleTileLeft() {
        return singleTileLeft;
    }

    void resetSingleTileLeft() {
        this.singleTileLeft = false;
    }

    void reachExit() {
        reachedExit = true;
    }

    @Override
    public int compareTo(MazePath o) {
        return lengthToExit() - o.lengthToExit();
    }

    void setLocation(Vector2f tileLocation) {
        this.location = tileLocation;
        visitedLocations.add(tileLocation);
    }

    MazePath copy(Vector2f newLocation) {
        MazePath copy = new MazePath(parent, newLocation, new ArrayList<>(visitedLocations));
        return copy;
    }

    private List<Vector2f> nextDirections() {

        int x = location.x;
        int y = location.y;
        Vector2f edges = parent.getEdges(y);

        List<Vector2f> nextLocations = new ArrayList<>();

        if(x != 0) nextLocations.add(new Vector2f(x-1, y));
        if(y != 0) nextLocations.add(new Vector2f(x, y-1));
        if(x < edges.x -1) nextLocations.add(new Vector2f(x+1, y));
        if(y < edges.y -1) nextLocations.add(new Vector2f(x, y+1));

        return nextLocations
                .stream()
                .filter(loc -> !visitedLocations.contains(loc))
                .filter(loc -> parent.getAt(loc).isTreadable())
                .collect(Collectors.toList());
    }

    public List<Vector2f> getVisitedLocations() {
        return new ArrayList<>(visitedLocations);
    }

    public String getMazeToString() {
        return parent.toString();
    }

    public int length() {
        return visitedLocations.size()-1;
    }

    private int lengthToExit() {
        return reachedExit ? length() : Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return MazePrinter.printMazePath(this);
    }


}
