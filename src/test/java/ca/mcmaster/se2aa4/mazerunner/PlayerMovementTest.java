// This test checks that the Player class correctly handles movement, 
//turning, and wall collisions in the maze.
package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerMovementTest {
    private Maze testMaze;
    private Player testPlayer;
    private static final String MAZE_FILE = "./examples/small.maz.txt";

    @BeforeEach
    void setupMazeAndPlayer() {
        //Load the maze and initialize the player at position (7, 1) facing EAST
        testMaze = new Maze(MAZE_FILE);
        testPlayer = new Player(new int[]{7, 1}, Direction.EAST);
    }

    @Test
    void verifyStartingPosition() {
        //Check if the player starts at the correct position
        int[] startingPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, startingPosition, "Player should start at position (7, 1).");
    }

    @Test
    void movePlayerNorth() {
        //Testing if player will still move NORTH even with wall in place
        testPlayer.executeMove('L', testMaze); // Turn left to face NORTH
        testPlayer.executeMove('F', testMaze); // Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, newPosition, "Player should stay at position (7, 1) when trying to move NORTH.");
    }

    @Test
    void movePlayerEast() {
        //Move the player EAST
        testPlayer.executeMove('F', testMaze); // Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 2}, newPosition, "Player should move to position (7, 2) when moving EAST.");
    }

    @Test
    void movePlayerSouth() {
        //Move the player SOUTH
        testPlayer.executeMove('R', testMaze); // Turn right to face SOUTH
        testPlayer.executeMove('F', testMaze); // Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{8, 1}, newPosition, "Player should move to position (8, 1) when moving SOUTH.");
    }

    @Test
    void movePlayerWest() {
        //Testing if player will still move WEST even with wall in place
        testPlayer.executeMove('L', testMaze); // Turn left to face WEST
        testPlayer.executeMove('L', testMaze); // Turn left again to face WEST
        testPlayer.executeMove('F', testMaze); // Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, newPosition, "Player should stay at position (7, 0) when trying to move WEST.");
    }
}