//This test ensures the Maze class correctly loads a maze from a file, 
//checks if cells are open, and handles entry and exit points. 
//It also tests edge cases like empty files and invalid entry sides. 
//The test uses temporary files to avoid cluttering the filesystem and 
//ensures proper cleanup after each test.
package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MazeTest {

    @Test
    public void testIsOpen() throws IOException {
        // Create a temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "#####\n#   #\n#####");

        Maze maze = new Maze(tempFile.toString());
        assertTrue(maze.isOpen(1, 1)); //Valid open cell
        assertFalse(maze.isOpen(0, 0)); //Wall
        assertFalse(maze.isOpen(-1, 0)); //Out of bounds (negative row)
        assertFalse(maze.isOpen(5, 5)); //Out of bounds (exceeds grid size)

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testSetEntrySide() throws IOException {
        //Create a temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "     \n#####\n     ");

        Maze maze = new Maze(tempFile.toString());
        maze.setEntrySide('E'); //Entry on the left, exit on the right
        assertArrayEquals(new int[]{0, 0}, maze.getEntry());
        assertArrayEquals(new int[]{2, 4}, maze.getExit());

        maze.setEntrySide('W'); //Entry on the right, exit on the left
        assertArrayEquals(new int[]{2, 4}, maze.getEntry());
        assertArrayEquals(new int[]{0, 0}, maze.getExit());

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testSetEntrySideInvalid() throws IOException {
        //Create a temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "     \n#####\n     ");

        Maze maze = new Maze(tempFile.toString());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maze.setEntrySide('X'); //Invalid side
        });
        assertEquals("Unknown entry side on X", exception.getMessage());

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testGetRowsAndCols() throws IOException {
        //Create a temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "#####\n#   #\n#####");

        Maze maze = new Maze(tempFile.toString());
        assertEquals(3, maze.getRows()); //Check number of rows
        assertEquals(5, maze.getCols()); //Check number of columns

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testIsOpenEdgeCases() throws IOException {
        //Create a temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "#####\n#   #\n#####");

        Maze maze = new Maze(tempFile.toString());
        assertFalse(maze.isOpen(-1, -1)); //Negative indices
        assertFalse(maze.isOpen(3, 0)); //Row out of bounds
        assertFalse(maze.isOpen(0, 5)); //Column out of bounds
        assertTrue(maze.isOpen(1, 1)); //Valid open cell

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testLoadMazeFromFileEmptyFile() throws IOException {
        //Create an empty temporary maze file
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Maze(tempFile.toString());
        });
        assertEquals("Failed to load maze file from " + tempFile.toString(), exception.getMessage());

        Files.delete(tempFile); //Clean up temporary file
    }

    @Test
    public void testPadLine() throws IOException {
        //Create a temporary maze file with uneven rows
        Path tempFile = Files.createTempFile("maze", ".txt");
        Files.writeString(tempFile, "###\n#\n###");

        Maze maze = new Maze(tempFile.toString());
        assertEquals(3, maze.getRows()); //Check number of rows
        assertEquals(3, maze.getCols()); //Check number of columns (padded to match longest row)

        Files.delete(tempFile); //Clean up temporary file
    }
}