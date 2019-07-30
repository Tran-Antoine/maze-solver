package net.akami.mazesolver.util;

public class Vector2f {

    public final int x;
    public final int y;

    public Vector2f(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2f)) return false;
        Vector2f other = (Vector2f) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
