package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    private static final Logger logger = LogManager.getLogger();
    private String[] directions;
    private int directionIndex;

    public Player() {
        directions = new String[] {"UP", "RIGHT", "DOWN", "LEFT"};
        directionIndex = 0; //Start facing up
    }

    public void turnLeft() {
        directionIndex = (directionIndex + 3) % 4;
        logger.trace("Facing: " + directions[directionIndex]);
    }

    public void turnRight() {
        directionIndex = (directionIndex + 1) % 4; //Moves clockwise
        logger.trace("Facing: " + directions[directionIndex]);
    }

    public String getCurrentDirection() {
        return directions[directionIndex];
    }
}
