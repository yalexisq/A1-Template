//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    private static final Logger logger = LogManager.getLogger();
    private int directionIndex; // 0: UP, 1: RIGHT, 2: DOWN, 3: LEFT
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public Player() {
        directionIndex = 1; //Start facing right
    }

    public String computePath(Maze maze) {
        StringBuilder path = new StringBuilder();
        int[] position = maze.findEntry();

        if (position == null) {
            logger.error("No entry point found");
            return "";
        }

        position = move(position);
        path.append("F");

        int rows = maze.getGrid().length;
        int cols = maze.getGrid()[0].length;

        while (true) {
            if (isExit(maze, position, cols) && !maze.isEntry(position)) break;

            turnRight();
            int[] nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("RF");
                position = nextPos;
                continue;
            }

            turnLeft();
            nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("F");
                position = nextPos;
                continue;
            }

            turnLeft();
            nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("LF");
                position = nextPos;
                continue;
            }

            turnLeft();
        }

        return path.toString().trim();
    }

    private boolean isValidMove(Maze maze, int[] position, int rows, int cols) {
        int row = position[0], col = position[1];
        return row >= 0 && row < rows && col >= 0 && col < cols && maze.isPassage(row, col);
    }

    private boolean isExit(Maze maze, int[] position, int cols) {
        int row = position[0], col = position[1];
        return (col == 0 || col == cols - 1) && maze.isPassage(row, col);
    }

    private void turnRight() {
        directionIndex = (directionIndex + 1) % 4;
    }

    private void turnLeft() {
        directionIndex = (directionIndex + 3) % 4; //Equal to -1 mod 4
    }

    private int[] move(int[] position) {
        return new int[]{position[0] + directions[directionIndex][0], position[1] + directions[directionIndex][1]};
    }
}
