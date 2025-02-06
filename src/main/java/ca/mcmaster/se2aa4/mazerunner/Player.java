//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class);
    private int row;
    private int col;
    private Direction facing;
    
    public Player(int[] startPos, Direction initial) {
        this.row = startPos[0];
        this.col = startPos[1];
        this.facing = initial;
    }
    
    //Makes moves to traverse maze
    public void executeMove(char move, Maze maze) {
        move = Character.toUpperCase(move);
        switch (move) {
            case 'F':
                attemptForward(maze);
                break;
            case 'L':
                facing = facing.turnLeft();
                break;
            case 'R':
                facing = facing.turnRight();
                break;
            default:
                throw new IllegalArgumentException("Unknown move command: " + move);
        }
    }
    
    private void attemptForward(Maze maze) {
        int newRow = row + facing.getDeltaRow();
        int newCol = col + facing.getDeltaCol();
        if (maze.isOpen(newRow, newCol)) {
            row = newRow;
            col = newCol;
            logger.debug("Moved forward");
        } else {
            logger.debug("Current coordinate causing block. Cannot move forward");
        }
    }
    
    public int[] getCurrentPosition() {
        return new int[] { row, col };
    }
    
    public Direction getFacing() {
        return facing;
    }
}
