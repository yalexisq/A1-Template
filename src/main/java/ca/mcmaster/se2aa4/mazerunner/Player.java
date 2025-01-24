//Alexis Quilatan, 400507554, 2AA4

package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    private static final Logger logger = LogManager.getLogger();

    private int directionIndex; //0: UP, 1: RIGHT, 2: DOWN, 3: LEFT
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private final String[] directionNames = {"UP", "RIGHT", "DOWN", "LEFT"};

    public Player() {
        directionIndex = 1; //Starts facing RIGHT
    }

    public String computePath(Maze maze) {
        StringBuilder path = new StringBuilder();
        int[] position = maze.findEntry();

        if (position == null) {
            logger.error("No entry point found");
            return "";
        }

        //Logging statements for testing / debugging
        logger.info("Starting traversal at position: {},{}", position[0], position[1]);

        int rows = maze.getGrid().length;
        int cols = maze.getGrid()[0].length;

        //Moves one step forward from the entry point to start traversal
        position = move(position);
        path.append("F");
        logger.debug("FORWARD from entry to position: {},{}", position[0], position[1]);

        //Main traversal loop
        while (true) {
            logger.debug("Current position: {},{} - Facing: {}", position[0], position[1], directionNames[directionIndex]);

            //Checks if current position is the exit (and not the entry)
            if (isExit(maze, position, cols) && !maze.isEntry(position)) {
                logger.info("Exit found at position: {},{}", position[0], position[1]);
                break;
            }

            //Right-hand rule
            turnRight();
            int[] nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("RF");
                logger.debug("Moved to: {},{}", nextPos[0], nextPos[1]);
                position = nextPos;
                continue;
            }

            //Undo right turn and try moving forward
            turnLeft();
            nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("F");
                logger.debug("Moved to: {},{}", nextPos[0], nextPos[1]);
                position = nextPos;
                continue;
            }

            //Try to turn left and move
            turnLeft();
            nextPos = move(position);
            if (isValidMove(maze, nextPos, rows, cols)) {
                path.append("LF");
                logger.debug("Moved to: {},{}", nextPos[0], nextPos[1]);
                position = nextPos;
                continue;
            }

            //If no moves are valid, turn back and try again
            turnLeft();
        }

        return path.toString().trim();
    }

    private boolean isValidMove(Maze maze, int[] position, int rows, int cols) {
        int row = position[0], col = position[1];
        boolean valid = row >= 0 && row < rows && col >= 0 && col < cols && maze.isPassage(row, col);
        if (!valid) {
            logger.debug("Invalid. Move to: {},{}", row, col);
        }
        return valid;
    }

    private boolean isExit(Maze maze, int[] position, int cols) {
        int row = position[0], col = position[1];
        return (col == 0 || col == cols - 1) && maze.isPassage(row, col);
    }

    private void turnRight() {
        directionIndex = (directionIndex + 1) % 4;
    }

    private void turnLeft() {
        directionIndex = (directionIndex + 3) % 4; // Equivalent to -1 mod 4
    }

    private int[] move(int[] position) {
        return new int[]{position[0] + directions[directionIndex][0], position[1] + directions[directionIndex][1]};
    }
}
