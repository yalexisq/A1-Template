//Alexis Quilatan, 400507554, 2AA4
//This test checks that the Player class correctly handles movement, 
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
        //Reset the Singleton instance before each test
        Maze.resetInstance();

        //Load the maze using the Singleton pattern
        testMaze = Maze.getInstance(MAZE_FILE);
        testPlayer = new Player(new int[]{7, 1}, Direction.EAST);
    }

    @Test
    void verifyStartingPosition() {
        int[] startingPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, startingPosition, "Player should start at position (7, 1).");
    }

    @Test
    void movePlayerNorth() {
        testPlayer.executeMove('L', testMaze); //Turn left to face NORTH
        testPlayer.executeMove('F', testMaze); //Attempt to move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, newPosition, "Player should remain at position (7, 1) because (6, 1) is blocked.");
    }

    @Test
    void movePlayerEast() {
        testPlayer.executeMove('F', testMaze); //Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 2}, newPosition, "Player should move to position (7, 2) when moving EAST.");
    }

    @Test
    void movePlayerSouth() {
        testPlayer.executeMove('R', testMaze); //Turn right to face SOUTH
        testPlayer.executeMove('F', testMaze); //Move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{8, 1}, newPosition, "Player should move to position (8, 1) when moving SOUTH.");
    }

    @Test
    void movePlayerWest() {
        testPlayer.executeMove('L', testMaze); //Turn left to face WEST
        testPlayer.executeMove('L', testMaze); //Turn left again to face WEST
        testPlayer.executeMove('F', testMaze); //Attempt to move forward
        int[] newPosition = testPlayer.getCurrentPosition();
        assertArrayEquals(new int[]{7, 1}, newPosition, "Player should remain at position (7, 1) because (7, 0) is blocked.");
    }
}