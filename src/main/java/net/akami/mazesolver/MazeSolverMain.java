package net.akami.mazesolver;

import net.akami.mazesolver.core.Maze;
import net.akami.mazesolver.core.MazePath;
import net.akami.mazesolver.util.MazeFactory;

import java.io.IOException;

public class MazeSolverMain {

    public static void main(String[] args) throws IOException {

        if(args.length == 0) throw new IllegalStateException("No path given");

        Maze maze = MazeFactory.createMaze(args[0]);
        long startTime = System.nanoTime();
        MazePath solution = maze.solve();
        long finalTime = System.nanoTime();
        System.out.println("Maze successfully solved after "+(finalTime-startTime)/10E9+" seconds");
        System.out.println(solution);
    }
}
