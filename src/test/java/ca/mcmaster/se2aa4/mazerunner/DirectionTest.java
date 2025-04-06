//Alexis Quilatan, 400507554, 2AA4
//This test ensures that the Direction class correctly handles turning left and right,
//converting characters to directions, and retrieving delta values for each direction.
package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    public void testTurnLeft() {
        //Test that turning left from each direction results in the correct new direction
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft()); 
    }

    @Test
    public void testTurnRight() {
        //Tests that turning right from each direction results in the correct new direction
        assertEquals(Direction.EAST, Direction.NORTH.turnRight()); 
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight()); 
        assertEquals(Direction.NORTH, Direction.WEST.turnRight());
    }

    @Test
    public void testFromChar() {
        //Tests that the fromChar method correctly maps characters to directions
        assertEquals(Direction.NORTH, Direction.fromChar('N')); 
        assertEquals(Direction.EAST, Direction.fromChar('E'));  
        assertEquals(Direction.SOUTH, Direction.fromChar('S')); 
        assertEquals(Direction.WEST, Direction.fromChar('W')); 
    }

    @Test
    public void testFromCharCaseInsensitive() {
        //Tests that the fromChar method works correctly with lowercase characters
        assertEquals(Direction.NORTH, Direction.fromChar('n')); 
        assertEquals(Direction.EAST, Direction.fromChar('e')); 
        assertEquals(Direction.SOUTH, Direction.fromChar('s')); 
        assertEquals(Direction.WEST, Direction.fromChar('w'));
    }

    @Test
    public void testFromCharInvalidInput() {
        //Test that an exception is thrown for invalid input characters
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Direction.fromChar('X'); // Invalid character 'X'
        });
        //Verifies that the exception message matches the expected output
        assertEquals("Invalid direction X", exception.getMessage());
    }

    @Test
    public void testDeltaValues() {
        //Tests that the deltaRow and deltaCol values for each direction are correct
        assertEquals(-1, Direction.NORTH.getDeltaRow()); //NORTH moves up (-1 row)
        assertEquals(0, Direction.NORTH.getDeltaCol());  //NORTH does not move horizontally
        assertEquals(0, Direction.EAST.getDeltaRow());   //EAST does not move vertically
        assertEquals(1, Direction.EAST.getDeltaCol());   //EAST moves right (+1 column)
        assertEquals(1, Direction.SOUTH.getDeltaRow());  // OUTH moves down (+1 row)
        assertEquals(0, Direction.SOUTH.getDeltaCol());  //SOUTH does not move horizontally
        assertEquals(0, Direction.WEST.getDeltaRow());   //WEST does not move vertically
        assertEquals(-1, Direction.WEST.getDeltaCol());  //WEST moves left (-1 column)
    }
}